package sample;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;
import sample.model.BitArray;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    TextArea inputText = new TextArea();
    @FXML
    TextArea editText = new TextArea();
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

//    @FXML
//    public void initialize() {
//        inputTextBit.textProperty().bindBidirectional(inputText.textProperty(), prepareConverter());
//        editTextBit.textProperty().bindBidirectional(editText.textProperty(), prepareConverter());
//        outputTextBit.textProperty().bindBidirectional(outputText.textProperty(), prepareConverter());
//
//    }

    public void sendToEdit() {
        editText.setText(inputText.getText());
        inputText.setText("");
        ErrorCorrectionAlgorithm errorCorrection = new ErrorCorrectionAlgorithm();
        inputTextBit.setText(errorCorrection.addParityBits(BitArray.stringToBitArray("a")).bitArrayToBitString());
    }

    @FXML
    public void sendToOutput() {
        outputText.setText(editText.getText());
        editText.setText("");
    }



//    private StringConverter prepareConverter() {
//        return new StringConverter() {
//            @Override
//            public String toString(Object object) {
//                return BitArray.stringToBitArray(object.toString()).bitArrayToBitString();
//            }
//
//            @Override
//            public Object fromString(String string) {
//                return BitArray.bitStringToBitArray(string).bitArrayToString();
//            }
//        };
//    }
}
