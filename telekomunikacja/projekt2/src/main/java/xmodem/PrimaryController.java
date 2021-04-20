package xmodem;

import com.fazecast.jSerialComm.SerialPort;
import com.google.common.primitives.Bytes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//Twórcy: Antoni Karwowski 229908, Michał Gebel 229879
//Główna klasa odpowiadająca za widok aplikacji, w której wyjątkowo z braku potrzeby rozdzielania logiki od widoku
//(ten przedmiot nie sprawdza dobrych technik programistycznych)
//umieszczono również wszystkie funkcje służące za przesył i odbiór informacji w xmodem
//Aplikacja powstawała współbieżnie za pomocą pluginu "Code with me" do IDE Intelij firmy JetBrains
//W związku z tym cały kod powstał przy udziale dwóch osób jednocześnie
//Przełożyo się to na lepsze zrozumienie zagadnienia i wyeliminowało przypadek, gdzie druga osoba nie wie co dzieje
//się w danej części programu, nad którym nie pracowała.
//Korzystamy z dwóch głównych funkcji - receiveFile - wystawienie odbiornika, sendFile - wystawienie nadawcy
//


public class PrimaryController {
    @FXML
    Button sendButton = new Button();
    @FXML
    Button loadButton = new Button();
    @FXML
    TextField pathField = new TextField();
    @FXML
    Button receiveButton = new Button();
    @FXML
    RadioButton checksumON = new RadioButton();
    @FXML
    RadioButton crcON = new RadioButton();

    //Ustawienie stałych wartości komunikatów, które zostaną wysłane lub odebrane
    byte[] bytes = new byte[0]; //przedstawienie bajtowe pliku, który odbieramy
    final int SOH = 1;
    final int NAK = 21;
    final int ACK = 6;
    final int EOT = 4;
    final int CAN = 24;
    final int C = 67;


    //wysyłanie wiadomości (ustawienie programu na funkcję nadajnika)
    public void sendFile() throws IOException {
        //ustawienie portu komunikacyjnego (COM2) oraz strumieni transmisji danych
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        OutputStream outputStream = port.getOutputStream();
        InputStream inputStream = port.getInputStreamWithSuppressedTimeoutExceptions();

        if(!crcON.isSelected() & !checksumON.isSelected()){
            System.out.println("Nie wybrano sposobu kodowania pliku, żegnam");
            System.exit(0);
        }

        boolean isCRC = crcON.isSelected();

        System.out.println("Rozpoczęto połączenie, port " + port.getSystemPortName() + " otwarty: " + port.isOpen());
        System.out.println("Nadawca rozpoczyna wysyłanie wiadomości...");

        //wydzielenie bloków danych z pliku, który chcemy przesłać
        for (int i = 0; i < bytes.length / 128 + 1; i++) {
            byte[] block = new byte[128];
            int beginBlockIndex = i * 128;
            int endBlockIndex = i * 128 + 128;

            if (endBlockIndex < bytes.length) {
                System.arraycopy(bytes, beginBlockIndex, block, 0, 128);
            } else {
                System.arraycopy(bytes, beginBlockIndex, block, 0, 128 - (endBlockIndex - bytes.length));
            }
            do {
                //przesłanie nagłówka
                outputStream.write(new byte[]{SOH, (byte) (i + 1), (byte) (~(i + 1))}); // wysylanie nagłówka
                outputStream.write(block);  // wysylanie bloku
                //suma kontrolna crc
                if(isCRC) {
                    byte[] crc = ByteBuffer.allocate(4).putInt(calculateCrc(block)).array(); //obliczanie crc
                    outputStream.write(new byte[]{crc[2], crc[3]});
                }
                //suma kontrolna suma bitowa
                else {
                    outputStream.write(calculateChecksum(block)); //obliczanie checksuma
                }
                System.out.println("Wysłano blok numer " + (i + 1));
            } while (inputStream.read() != ACK);
        }
        System.out.println("Zakończono wysyłanie bloków");
        //przeslanie eot, który sygnalizuje koniec przesyłania danych
        int lastResponse;
        do {
            outputStream.write(EOT);
            lastResponse = inputStream.read();

        } while (lastResponse != ACK);
        System.out.println("Zamykanie połączenia...");
        outputStream.close();
        inputStream.close();
        port.closePort();
    }

    //odbieranie wiadomości (ustawienie programu na funkcję odbiornika)
    public void receiveFile() throws IOException {
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 10000, 10000);
        OutputStream outputStream = port.getOutputStream();
        InputStream inputStream = port.getInputStream();
        List<Byte> receivedData = new ArrayList<>();

        if(!crcON.isSelected() & !checksumON.isSelected()){
            System.out.println("Nie wybrano sposobu kodowania pliku, żegnam");
            System.exit(0);
        }

        boolean isCRC = crcON.isSelected();
        if(isCRC) {
            outputStream.write(C);
        }else {
            outputStream.write(NAK);
        }
        while (true) {
            try {
                int header = inputStream.read();
                if (header == CAN) {
                    System.out.println("sygnał CAN, zamknięto połączenie");
                    port.closePort();
                    inputStream.close();
                    outputStream.close();
                    System.exit(0);
                } else if (header == EOT) {
                    outputStream.write(ACK);
                    break;
                }
            } catch (Exception e) {
                System.out.println("minęło 10 sekund od wysłania wiadomości, wysyłanie ponowne");
                if(isCRC) {
                    outputStream.write(C);
                }else {
                    outputStream.write(NAK);
                }
                continue;
            }

            byte[] response;
            if(isCRC)
                response = inputStream.readNBytes(132);
            else
                response = inputStream.readNBytes(131);
            System.out.println("zakończono wysyłanie bloku");

            byte[] block = new byte[128];
            System.arraycopy(response, 2, block, 0, 128);

            if(isCRC) {
                int crc = ((response[130] & 0xff) << 8) | (response[131] & 0xff);
                System.out.println(crc);
                System.out.println(calculateCrc(block));
                if (calculateCrc(block) != crc) {
                    System.out.println("Nie zgadza się suma kontrolna");
                    outputStream.write(NAK);
                } else {
                    receivedData.addAll(Bytes.asList(block));
                    System.out.println("odczytano blok numer " + response[0]);
                    outputStream.write(ACK);
                }
            }
            else {
                byte checksum = response[130];
                if (calculateChecksum(block) != checksum) {
                    System.out.println("Nie zgadza się suma kontrolna");
                    outputStream.write(NAK);
                } else {
                    receivedData.addAll(Bytes.asList(block));
                    System.out.println("odczytano blok numer " + response[0]);
                    outputStream.write(ACK);
                }
            }
        }
        System.out.println("Zakończono wysyłanie bloków");

        bytes = new byte[receivedData.size()];
        for(int i = 0; i < receivedData.size(); i++){
            bytes[i] = receivedData.get(i);
        }
        saveFile();
        System.out.println("Zamykanie połączenia...");
        port.closePort();
        inputStream.close();
        outputStream.close();
    }


    //zapisanie pliku do lokalizacji kodu programu
    public void saveFile() throws IOException {
        try (FileOutputStream stream = new FileOutputStream("output")) {
            stream.write(bytes);
        }
    }

    //wczytanie pliku z sytemu gospodarza
    public void loadFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        File selectedDirectory = fileChooser.showOpenDialog(pathField.getScene().getWindow());
        pathField.setText(selectedDirectory.getAbsolutePath());

        try {
            bytes = Files.readAllBytes(Paths.get(pathField.getText()));
            saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        App.main(args);
    }

    //przełącznik pomiędzy checksumem, a CRC
    public void setChecksumON(){
        checksumON.setSelected(true);
        crcON.setSelected(false);
    }

    public void setCrcOn(){
        checksumON.setSelected(false);
        crcON.setSelected(true);
    }

    //implementacja algorytmu wyliczania podstawowej sumy kontrolnej (suma bitowa)
    private int calculateChecksum(byte[] block) {
        byte sum = 0;
        for (byte element : block) {
            sum += element;
        }
        return sum;
    }

    //implementacja algorytmu wyliczania zaawansowanej sumy kontrolnej (cykliczny kod nadmiarowy(cyclic redundancy code))
    private int calculateCrc(byte[] b) {
        int crc, i;
        crc = 0;
        for (byte value : b) {
            crc = crc ^ (int) value << 8;
            for (i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0)
                    crc = crc << 1 ^ 0x1021;
                else
                    crc = crc << 1;
            }
        }
        return crc & 0xFFFF;
    }
}
