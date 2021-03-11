package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;
import sample.model.BitArray;
import sample.model.ErrorCorrectionAlgorithm;

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


    ErrorCorrectionAlgorithm errorCorrection = new ErrorCorrectionAlgorithm();

    @FXML
    public void initialize() {
        inputTextBit.textProperty().bindBidirectional(inputText.textProperty(), prepareConverter());
        outputTextBit.textProperty().bindBidirectional(outputText.textProperty(), prepareConverter());

    }

    public void sendToEdit() {
        editTextBit.setText(errorCorrection.encode(inputText.getText()));
        inputText.setText("");
    }

    public void sendToOutput() {
        outputTextBit.setText(editTextBit.getText());
        editTextBit.setText("");
    }

    public void correct() {

    }

    public void checkCorrection() {
        if(errorCorrection.checkCorrection(outputTextBit.getText())) {
            background.setStyle("-fx-background-color:#86AF49;");
        } else {
            background.setStyle("-fx-background-color:#F08080;");
        }
    }

    public void removeParityBits() {
        outputText.setText(errorCorrection.removeParityBits(outputTextBit.getText()));

    }



    private StringConverter prepareConverter() {
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
}
