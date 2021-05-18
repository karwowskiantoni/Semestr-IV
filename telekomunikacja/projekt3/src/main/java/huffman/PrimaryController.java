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
        BitArray array = convertToHuffmanCode(bytes);
        outputStream.write(array.length % 8);
        outputStream.write(bytes);
        fileText.setText("wysłano plik o treści: ");
        fileText.setText(fileText.getText() + System.lineSeparator() + new String(bytes));
    }

    public void receiveFile() throws IOException {
        int length = inputStream.read();
        bytes = inputStream.readNBytes(inputStream.available());
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
            String result = "";
            while (message.length > 0) {
                int i = 0;
                while (true){
                    int index = positionInHuffmanLibrary(message.getBitsFromLeftSide(i));
                    if (index != -1) {
                        result += huffmanLibraryHeaders[index];
                        message = message.getBitsFromRightSide(i);
                        break;
                    }
                    i++;
                }
            }
            return result.getBytes();
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
            if (b.bitArrayToBitString().equals(huffmanLibrary[i].bitArrayToBitString())) {
                index = i;
            }
        }
        return index;
    }
}
