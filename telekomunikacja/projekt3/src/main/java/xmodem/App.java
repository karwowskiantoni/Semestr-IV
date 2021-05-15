package xmodem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//Klasa w której znajduje się metoda main, dzięki której uruchamiamy aplikację

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader primaryLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        stage.setTitle("XModem");
        stage.setScene(new Scene(primaryLoader.load(), 620, 510));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}