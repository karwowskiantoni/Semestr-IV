package xmodem;

import com.fazecast.jSerialComm.SerialPort;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Bytes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import xmodem.model.BitArray;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.CRC32;

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

    byte[] bytes = new byte[0];
    final int SOH = 1;
    final int NAK = 21;
    final int ACK = 6;
    final int EOT = 4;
    final int CAN = 24;

    public void sendFile() throws IOException {
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        OutputStream outputStream = port.getOutputStream();
        InputStream inputStream = port.getInputStreamWithSuppressedTimeoutExceptions();

        if(!crcON.isSelected() & !checksumON.isSelected()){
            System.out.println("Nie wybrano sposobu kodowania pliku, żegnam");
            System.exit(0);
        }

        System.out.println("Rozpoczęto połączenie, port " + port.getSystemPortName() + " otwarty: " + port.isOpen());
        System.out.println("Odbiorca wysłał sygnał: " + inputStream.read());
        System.out.println("Nadawca rozpoczyna wysyłanie wiadomości..." + System.lineSeparator());

        if(checksumON.isSelected()) {
            for (int i = 0; i < bytes.length / 128 + 1; i++) {
                byte[] block = new byte[128];
                int beginBlockIndex = i * 128;
                int endBlockIndex = i * 128 + 128;

                if (endBlockIndex < bytes.length) {
                    System.arraycopy(bytes, beginBlockIndex, block, 0, 128);
                } else {
                    System.arraycopy(bytes, beginBlockIndex, block, 0, 128 - (endBlockIndex - bytes.length));
                }
                int firstResponse;
                do {
                    outputStream.write(new byte[]{SOH, (byte) (i + 1), (byte) (~(i + 1))}); //nagłówek
                    outputStream.write(block);  // wysylanie bloku
                    outputStream.write(calculateChecksum(block)); //obliczanie checksuma
                    firstResponse = inputStream.read();
                    System.out.println("Wysłano blok numer " + (i + 1));
                    System.out.println("Odpowiedź obiorcy to: " + firstResponse + System.lineSeparator());
                } while (firstResponse != ACK);
            }
            System.out.println("Zakończono wysyłanie bloków");
            int lastResponse;
            do {
                outputStream.write(EOT);
                lastResponse = inputStream.read();

            } while (lastResponse != ACK);
            System.out.println("Odbiorca wysłał sygnał: " + lastResponse);
        }
//        } else {
//
//            for (int i = 0; i < bytes.length / 128 + 1; i++) {
//                byte[] block = new byte[128];
//                int beginBlockIndex = i * 128;
//                int endBlockIndex = i * 128 + 128;
//
//                if (endBlockIndex < bytes.length) {
//                    System.arraycopy(bytes, beginBlockIndex, block, 0, 128);
//                } else {
//                    System.arraycopy(bytes, beginBlockIndex, block, 0, 128 - (endBlockIndex - bytes.length));
//                }
//                int firstResponse;
//                do {
//                    outputStream.write(new byte[]{SOH, (byte) (i + 1), (byte) (~(i + 1))}); //nagłówek
//                    outputStream.write(block);  // wysylanie bloku
//                    String hex = Integer.toHexString(CRC16(block));
//                    BitArray crcBitArray = BitArray.hexStringToBitArray(hex);
//                    outputStream.write(crcBitArray.getBytes()); //obliczanie checksuma
//                    firstResponse = inputStream.read();
//                    System.out.println("Wysłano blok numer " + (i + 1));
//                    System.out.println("Odpowiedź obiorcy to: " + firstResponse + System.lineSeparator());
//                } while (firstResponse != ACK);
//            }
//            System.out.println("Zakończono wysyłanie bloków");
//            int lastResponse;
//            do {
//                outputStream.write(EOT); // EOT
//                lastResponse = inputStream.read();
//
//            } while (lastResponse != ACK);
//            System.out.println("Odbiorca wysłał sygnał: " + lastResponse);
//        }


        System.out.println("Zamykanie połączenia...");
        System.out.println("świzdu gwizdu com2 poszedl w pizdu");
        outputStream.close();
        inputStream.close();
        port.closePort();
    }

    public void receiveFile() throws IOException {
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 10000, 10000);
        OutputStream outputStream = port.getOutputStream();
        InputStream inputStream = port.getInputStream();
        List<Byte> receivedData = new ArrayList<>();
        outputStream.write(NAK);
        while (true) {
            try {
                int header = inputStream.read();
                if (header == SOH) {
                    //przechodzimy do następnego bloku
                } else if (header == CAN) {
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
                outputStream.write(NAK);
                continue;
            }

            byte[] response = inputStream.readNBytes(131);
            byte[] block = new byte[128];
            byte checksum = response[130];
            System.arraycopy(response, 2, block, 0, 128);
            if (calculateChecksum(block) != checksum) {
                System.out.println("Nie zgadza się suma kontrolna");
                outputStream.write(NAK);
            } else {
                receivedData.addAll(Bytes.asList(block));
                System.out.println("odczytano blok numer " + response[0]);
                outputStream.write(ACK);
            }

        }
        bytes = new byte[receivedData.size()];
        for(int i = 0; i < receivedData.size(); i++){
            bytes[i] = receivedData.get(i);
        }
        saveFile();
        port.closePort();
        inputStream.close();
        outputStream.close();
    }

    public void saveFile() throws IOException {
        try (FileOutputStream stream = new FileOutputStream("output")) {
            stream.write(bytes);
        }
    }

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

    public void setChecksumON(){
        checksumON.setSelected(true);
        crcON.setSelected(false);
    }

    public void setCrcOn(){
        checksumON.setSelected(false);
        crcON.setSelected(true);
    }

    private int calculateChecksum(byte[] block) {
        byte sum = 0;
        for (byte element : block) {
            sum += element;
        }
        return sum;
    }

    int calculate_crc(byte[] bytes) {
        int i;
        int crc_value = 0;
        for (byte aByte : bytes) {
            for (i = 0x80; i != 0; i >>= 1) {
                if ((crc_value & 0x8000) != 0) {
                    crc_value = (crc_value << 1) ^ 0x8005;
                } else {
                    crc_value = crc_value << 1;
                }
                if ((aByte & i) != 0) {
                    crc_value ^= 0x8005;
                }
            }
        }
        return crc_value;
    }

    int CRC16(byte[] bytes) {
        int tmp = 0, val = 0x18005 << 15;
        for (int i = 0; i < 3; i++) {
            tmp = tmp * 256 + bytes[i];
        }
        tmp *= 256;

        for (int i = 3; i < 134; i++) {
            if (i < 128) {
                tmp += bytes[i];
            }
            for (int j = 0; j < 8; j++) {
                if (tmp == (1 << 31)) {
                    tmp ^= val;
                }
                tmp <<= 1;
            }
        }
        return tmp >> 16;
    }
}
