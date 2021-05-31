package ac;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

//Twórcy: Antoni Karwowski 229908, Michał Gebel 229879
//Główna klasa odpowiadająca za widok i logikę aplikacji - wyjątkowo z braku potrzeby rozdzielania logiki od widoku
//(ten przedmiot nie sprawdza dobrych technik programistycznych)
//Aplikacja powstawała współbieżnie za pomocą pluginu "Code with me" do IDE Intelij firmy JetBrains
//W związku z tym cały kod powstał przy udziale dwóch osób jednocześnie
//Przełożyo się to na lepsze zrozumienie zagadnienia i wyeliminowało przypadek, gdzie druga osoba nie wie co dzieje
//się w danej części programu, nad którym nie pracowała.
//Program posiada dwie główne funkcjonalności
//1. Połączenie dwóch instancji aplikacji poprzez gniazdo sieciowe (socket)
//Dzieje się to poprzez połączenie przez wolny port o numerze 5000
//Jedna z instancji jest serwerem, druga klientem
//2. Zakodowanie wybranej sentencji zgodnie ze stworzonym do niego kodem Huffmana i wysłanie jej przez jedną z instancji aplikacji
//Odebranie, odkodowanie i zapisanie jej przez drugą instancję aplikacji

public class PrimaryController {
    //deklaracja słownika kodowego Huffmana
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
    //socket servera
    ServerSocket serverSocket;
    //socket klienta
    Socket clientSocket;
    private byte[] bytes;
    private InputStream inputStream;
    private OutputStream outputStream;

    public static void main(String[] args) {
        App.main(args);
    }
    //otwieranie portu, czyli instancja aplikacji staje się serwerem
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
    //połączenie z portem, czyli instancja aplikacji staje sie klientem
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
    //zamknięcie połączenia
    public void stop() throws IOException {
        serverSocket.close();
        clientSocket.close();
        inputStream.close();
        outputStream.close();
    }
    //wysłąnie pliku
    public void sendFile() throws IOException {
        //określenie jak długa jest żądana wiadomość
        byte numberOfChars = (byte) new String(bytes).toCharArray().length;
        //kodowanie wiadomości zgodnie ze słownikiem kodowym
        BitArray array = convertToHuffmanCode(bytes);
        //pobranie bajtów danych
        byte[] data = array.getBytes();
        System.out.println(new BitArray(bytes).bitArrayToBitString());
        System.out.println(array.bitArrayToBitString());
        System.out.println(new BitArray(data).bitArrayToBitString());
        //przesłanie ilości znaków oraz zakodowaną wiadomość
        outputStream.write(numberOfChars);
        outputStream.write(data);
        fileText.setText("wysłano plik o treści: " + System.lineSeparator() + new String(data));
    }

    public void receiveFile() throws IOException {
        //odebranie długości wiadomości
        int length = inputStream.read();
        //odebranie zakodowanej wiadomości
        BitArray cryptedData = new BitArray(inputStream.readNBytes(inputStream.available()));
        //odkodowanie wiadomości
        String decryptedPhrase = new String(convertFromHuffmanCode(cryptedData));
        //ucięcie niepotrzebnych znaków (uzupełnien bitów 0 do pełnego bajta)
        String cut = decryptedPhrase.substring(0, length);
        bytes = cut.getBytes();
        fileText.setText("odebrano plik o treści: ");
        fileText.setText(fileText.getText() + System.lineSeparator() + new String(bytes));
        saveFile();
    }
    //zapis do pliku
    public void saveFile() {
        try (FileOutputStream stream = new FileOutputStream("output")) {
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //wybranie pliku
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
    //kodowanie huffmana
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
    //odkodowywanie huffmana
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
    //szukanie odpowiadających słów w słowniku kodowym
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
