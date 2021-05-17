package huffman;

import com.google.common.primitives.Bytes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
//Twórcy: Antoni Karwowski 229908, Michał Gebel 229879

public class PrimaryController {

    @FXML
    TextArea portText = new TextArea();
    @FXML
    TextField fileText = new TextField();
    @FXML
    Button receiveButton = new Button();

    private byte[] bytes;
    ServerSocket serverSocket;
    Socket clientSocket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;


    public void openPort() {
        try {
            serverSocket = new ServerSocket(5000);
            portText.setText("PORT 5000 OTWARTY");
            portText.setText(portText.getText() + System.lineSeparator() + "oczekiwanie na połączenie ...");
            clientSocket = serverSocket.accept();
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            portText.setText(portText.getText() + System.lineSeparator() + "połączono :)");
        } catch (IOException e) {
            portText.setText(portText.getText() + System.lineSeparator() + "błędny numer lub port zajęty");
        }
    }

    public void connectToPort() {
        try {
            clientSocket = new Socket("127.0.0.1", 5000);
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            portText.setText("POŁĄCZONO Z PORTEM 5000");
        } catch (IOException e) {
            portText.setText("błędny numer lub port zajęty");
        }
    }

    public void stop() throws IOException{
        serverSocket.close();
        clientSocket.close();
        inputStream.close();
        outputStream.close();
    }
    public void sendFile(){
        outputStream.println(new String(bytes));
        fileText.setText("wysłano plik o treści: ");
        fileText.setText(fileText.getText() + System.lineSeparator() + new String(bytes));
    }

    public void receiveFile() throws IOException{
        List<Byte> receivedData = new ArrayList<>();
        int counter = 1;
        while (inputStream.ready()) {
            System.out.println("czytam linię numer " + counter);
            receivedData.addAll(Bytes.asList(inputStream.readLine().getBytes()));
            counter++;
        }
        bytes = new byte[receivedData.size()];
        for (int i = 0; i < receivedData.size(); i++){
            bytes[i] = receivedData.get(i);
        }
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

    public static void main(String[] args) {
        App.main(args);
    }
}
