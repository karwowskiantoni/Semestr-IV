package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import sample.model.BitArray;
import sample.model.ErrorCorrectionAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//Klasa odpowiadająca za widok aplikacji, łącząca metody z logiki z odpowiednimi przyciskami

public class Controller {

    @FXML
    TabPane background = new TabPane();
    @FXML
    TextArea inputText = new TextArea();
    @FXML
    TextArea outputText = new TextArea();
    @FXML
    TextArea inputTextBit = new TextArea();
    @FXML
    TextArea editTextBit = new TextArea();
    @FXML
    TextArea outputTextBit = new TextArea();
    @FXML
    Button inputButton = new Button();
    @FXML
    Button editButton = new Button();
    @FXML
    Button outputButton = new Button();
    @FXML
    TextField pathField = new TextField();
    @FXML
    TextField pathField2 = new TextField();

    byte[] bytes;
    ErrorCorrectionAlgorithm errorCorrection = new ErrorCorrectionAlgorithm();

    @FXML
    public void initialize() {
        inputTextBit.textProperty().bindBidirectional(inputText.textProperty(), prepareInputConverter());
        outputTextBit.textProperty().bindBidirectional(outputText.textProperty(), prepareOutputConverter());

    }

    public void sendToEdit() throws FileNotFoundException, IOException {
        editTextBit.setText(errorCorrection.encode(inputText.getText()));
        inputText.setText("");

        try (FileOutputStream stream = new FileOutputStream("msgWithParityBits")) {
            stream.write(BitArray.bitStringToBitArray(editTextBit.getText()).getBytes());
        }
    }

    public void sendToOutput() {
        outputTextBit.setText(editTextBit.getText());
        editTextBit.setText("");
    }

    public void correct() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        outputTextBit.setText(errorCorrection.correct(outputTextBit.getText()));
    }

    public void checkCorrection() {
        if(errorCorrection.checkCorrection(outputTextBit.getText())) {
            background.setStyle("-fx-background-color:#86AF49;");
        } else {
            background.setStyle("-fx-background-color:#F08080;");
        }
    }

    public void chooseFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        File selectedDirectory = fileChooser.showOpenDialog(pathField.getScene().getWindow());
        pathField.setText(selectedDirectory.getAbsolutePath());

        try {
            bytes = Files.readAllBytes(Paths.get(pathField.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chooseFile2() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        File selectedDirectory = fileChooser.showOpenDialog(pathField2.getScene().getWindow());
        pathField2.setText(selectedDirectory.getAbsolutePath());

        try {
            bytes = Files.readAllBytes(Paths.get(pathField2.getText()));
            BitArray array = new BitArray(bytes);
            outputTextBit.setText(array.bitArrayToBitString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile(){
        BitArray bitArray = BitArray.bitStringToBitArray("010011011000110111");
        BitArray bitArray1 = new BitArray(new byte[]{bitArray.getByte(2)});
        System.out.println("haha taka zmyłka");
    }


    private StringConverter prepareInputConverter() {
        return new StringConverter() {
            @Override
            public String toString(Object object) {
                return BitArray.stringToBitArray(object.toString()).bitArrayToBitString();
            }

            @Override
            public Object fromString(String string) {
                return BitArray.bitStringToBitArray(string).bitArrayToString();
            }
        };
    }

    private StringConverter prepareOutputConverter() {
        return new StringConverter() {
            @Override
            public String toString(Object object) {
                return BitArray.stringToBitArray(object.toString()).bitArrayToBitString();
            }

            @Override
            public Object fromString(String string) {
                return errorCorrection.removeParityBits(string);
            }
        };
    }
}
