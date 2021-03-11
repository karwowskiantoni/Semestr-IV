package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;
import sample.model.BitArray;
import sample.model.ErrorCorrectionAlgorithm;


public class Controller {

    @FXML
    TextArea outputText = new TextArea();
    @FXML
    TextArea inputText = new TextArea();
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
        inputText.setText("");
        editTextBit.setText(errorCorrection.addParityBits(BitArray.stringToBitArray("a")).bitArrayToBitString());
    }

    @FXML
    public void sendToOutput() {
        outputTextBit.setText(editTextBit.getText());
        editTextBit.setText("");
    }

    @FXML
    public void correct() {
        if(errorCorrection.checkCorrection(BitArray.bitStringToBitArray(outputTextBit.getText()))) {
            outputText.setText("no i zajebiście");
        } else {
            outputText.setText("noooo, coś nie dobrze");
        }
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
