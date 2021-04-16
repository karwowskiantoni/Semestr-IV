package xmodem;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrimaryController {
    @FXML
    Button connectButton = new Button();
    @FXML
    Button loadButton = new Button();
    @FXML
    TextField pathField = new TextField();

    byte[] bytes = new byte[0];
    final int SOH = 1;
    final int NAK = 21;
    final int ACK = 6;
    final int EOT = 4;
    final int CAN = 24;
    final int C = 67;

    public void sendFile() throws IOException {
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        OutputStream outputStream = port.getOutputStream();
        InputStream inputStream = port.getInputStreamWithSuppressedTimeoutExceptions();

        System.out.println("Rozpoczęto połączenie, port " + port.getSystemPortName() + " otwarty: " + port.isOpen());
        System.out.println("Odbiorca wysłał sygnał: " + inputStream.read());
        System.out.println("Nadawca rozpoczyna wysyłanie wiadomości..." + System.lineSeparator());

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
            outputStream.write(4); // EOT
            lastResponse = inputStream.read();

        } while (lastResponse != ACK);
        System.out.println("Odbiorca wysłał sygnał: " + lastResponse);
        System.out.println("Zamykanie połączenia...");
        System.out.println("świzdu gwizdu com2 poszedl w pizdu");
        outputStream.close();
        inputStream.close();
        port.closePort();
    }

    public void receiveFile() throws IOException {
        SerialPort port = SerialPort.getCommPorts()[1];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        OutputStream outputStream = port.getOutputStream();
        InputStream inputStream = port.getInputStreamWithSuppressedTimeoutExceptions();

        List<Integer> list = new ArrayList<>();
        outputStream.write(NAK);

        while (true) {
            byte[] response = inputStream.readAllBytes();
            if (response[0] != SOH) {
                outputStream.write(NAK);
                continue;
            }
            byte[] block = new byte[128];
            byte checksum = response[131];
            System.arraycopy(response, 4, block, 0, 128);
            if (calculateChecksum(block) != checksum) {
                outputStream.write(NAK);
            } else {
                outputStream.write(ACK);
//                list.addAll(.stream().mapToInt().collect(Collectors.toList()));
                break;
            }
        }

        outputStream.close();
        inputStream.close();
        port.closePort();
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


    private int calculateChecksum(byte[] block) {
        int sum = 0;
        for (byte element : block) {
            sum += element;
        }
        return sum % 256;
    }
}
