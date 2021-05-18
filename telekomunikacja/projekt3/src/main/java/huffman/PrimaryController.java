package huffman;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class PrimaryController {

    private final BitArray[] huffmanLibrary = new BitArray[]{
            BitArray.bitStringToBitArray("00"),
            BitArray.bitStringToBitArray("110"),
            BitArray.bitStringToBitArray("100"),
            BitArray.bitStringToBitArray("011"),
            BitArray.bitStringToBitArray("1110"),
            BitArray.bitStringToBitArray("1111"),
            BitArray.bitStringToBitArray("1010"),
            BitArray.bitStringToBitArray("0101"),
            BitArray.bitStringToBitArray("0100"),
            BitArray.bitStringToBitArray("10110"),
            BitArray.bitStringToBitArray("10111")
    };

    private final char[] huffmanLibraryHeaders = new char[]{
            'a', 't', 'i', ' ', 'v', 'n', 's', 'm', 'u', 'e', 'o'
    };

    @FXML
    TextArea portText = new TextArea();
    @FXML
    TextArea fileText = new TextArea();
    @FXML
    Button receiveButton = new Button();
    ServerSocket serverSocket;
    Socket clientSocket;
    private byte[] bytes;
    private InputStream inputStream;
    private OutputStream outputStream;

    public static void main(String[] args) {
        App.main(args);
    }

    public void openPort() {
        portText.setText("PORT 5000 OTWARTY");
        portText.setText(portText.getText() + System.lineSeparator() + "oczekiwanie na połączenie ...");
        try {
            serverSocket = new ServerSocket(5000);
            clientSocket = serverSocket.accept();
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            portText.setText(portText.getText() + System.lineSeparator() + "połączono :)");
        } catch (IOException e) {
            portText.setText(portText.getText() + System.lineSeparator() + "błędny numer lub port zajęty");
        }
    }

    public void connectToPort() {
        try {
            clientSocket = new Socket("127.0.0.1", 5000);
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            portText.setText("POŁĄCZONO Z PORTEM 5000");
        } catch (IOException e) {
            portText.setText("błędny numer lub port zajęty");
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
        clientSocket.close();
        inputStream.close();
        outputStream.close();
    }

    public void sendFile() throws IOException {
        byte numberOfChars = (byte) new String(bytes).toCharArray().length;
        BitArray array = convertToHuffmanCode(bytes);
        byte[] data = array.getBytes();
        System.out.println(new BitArray(bytes).bitArrayToBitString());
        System.out.println(array.bitArrayToBitString());
        System.out.println(new BitArray(data).bitArrayToBitString());
        outputStream.write(numberOfChars);
        outputStream.write(data);
        fileText.setText("wysłano plik o treści: " + System.lineSeparator() + new String(data));
    }

    public void receiveFile() throws IOException {
        int length = inputStream.read();
        BitArray cryptedData = new BitArray(inputStream.readNBytes(inputStream.available()));
        String decryptedPhrase = new String(convertFromHuffmanCode(cryptedData));
        String cut = decryptedPhrase.substring(0, length);
        bytes = cut.getBytes();
        fileText.setText("odebrano plik o treści: ");
        fileText.setText(fileText.getText() + System.lineSeparator() + new String(bytes));
        saveFile();
    }

    public void saveFile() {
        try (FileOutputStream stream = new FileOutputStream("output")) {
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        File selectedDirectory = fileChooser.showOpenDialog(fileText.getScene().getWindow());
        fileText.setText(selectedDirectory.getAbsolutePath());

        try {
            bytes = Files.readAllBytes(Paths.get(fileText.getText()));
            saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BitArray convertToHuffmanCode(byte[] bytes) {
        String message = new String(bytes);
        BitArray result = new BitArray(0);
        for (int i = 0; i < message.length(); i++) {
            int index = positionInHuffmanLibrary(message.toCharArray()[i]);
            if(index == -1) {
                System.out.println("brak liter w słowniku");
                return new BitArray(0);
            }
            result = result.connect(huffmanLibrary[index]);
        }
        return result;
    }

        private byte[] convertFromHuffmanCode(BitArray message) {
            StringBuilder result = new StringBuilder();
            while (message.length > 0) {
                int i = 0;

                while (true){
                    int index = positionInHuffmanLibrary(message.getBitsFromLeftSide(i));
                    if (index != -1) {


                        result.append(huffmanLibraryHeaders[index]);
                        message = message.getBitsFromRightSide(i);


                        break;
                    }
                    i++;

                }
            }
            return result.toString().getBytes();
        }

    private int positionInHuffmanLibrary(char c) {
        int index = -1;
        for (int i = 0; i < huffmanLibraryHeaders.length; i++) {
            if (huffmanLibraryHeaders[i] == c)
                index = i;
        }
        return index;
    }

    private int positionInHuffmanLibrary(BitArray b) {
        int index = -1;
        for (int i = 0; i < huffmanLibrary.length; i++) {
            if (b.isEqual(huffmanLibrary[i])) {
                index = i;
            }
        }
        return index;
    }
}
