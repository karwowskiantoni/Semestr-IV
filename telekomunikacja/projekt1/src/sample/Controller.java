package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    TextArea inputText = new TextArea();
    @FXML
    TextArea semiOutputText = new TextArea();
    @FXML
    TextArea outputText = new TextArea();

    @FXML
    Button inputButton = new Button();
    @FXML
    Button semiOutputButton = new Button();
    @FXML
    Button outputButton = new Button();


    public void sendToSemi() {
        semiOutputText.setText(inputText.getText());
        inputText.setText("");
    }

    @FXML
    public void sendToOutput() {
        outputText.setText(semiOutputText.getText());
        semiOutputText.setText("");
    }
}
