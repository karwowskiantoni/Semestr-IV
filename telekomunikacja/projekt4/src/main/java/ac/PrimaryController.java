package ac;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.*;
import javax.sound.sampled.*;

//Twórcy: Antoni Karwowski 229908, Michał Gebel 229879
//Główna klasa odpowiadająca za widok i logikę aplikacji - wyjątkowo z braku potrzeby rozdzielania logiki od widoku
//(ten przedmiot nie sprawdza dobrych technik programistycznych)
//Aplikacja powstawała współbieżnie za pomocą pluginu "Code with me" do IDE Intelij firmy JetBrains
//W związku z tym cały kod powstał przy udziale dwóch osób jednocześnie
//Przełożyo się to na lepsze zrozumienie zagadnienia i wyeliminowało przypadek, gdzie druga osoba nie wie co dzieje
//się w danej części programu, nad którym nie pracowała.
//Program posiada dwie główne funkcjonalności
//1.Nagrywanie dźwięku (input z karty dźwiękowej) i zapisanie go do pliku o rozszerzeniu .wav
//2.Odtwarzanie nagranego dźwięku w programie.
public class PrimaryController {

    @FXML
    TextArea terminal = new TextArea();
    @FXML
    Button recordButton = new Button();

    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;

    public static void main(String[] args) {
        App.main(args);
    }
    //funkcja odpowiadająca za odtwarzanie nagranego uprzednio dźwięku
    public void playSound() throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File("nagranie.wav")));
        clip.start();
        Thread.sleep(clip.getMicrosecondLength() / 1000);
    }

    //funkcja odpowiadająca za wyowołanie funkcji nagrywającej dźwięk. Tutaj glównie zarządzamy zachowaniem przycisków
    public void recordSound() throws LineUnavailableException{
        if(recordButton.getText().equals("Rozpocznij nagrywanie")) {
            captureAudio();
            terminal.setText("Rozpoczęto nagrywanie");
            recordButton.setText("Zakończ nagrywanie");
            recordButton.setStyle("-fx-border-color:red");
        } else if(recordButton.getText().equals("Zakończ nagrywanie")) {
            stopAudio();
            terminal.setText("Zakończono nagrywanie");
            recordButton.setText("Rozpocznij nagrywanie");
            recordButton.setStyle("-fx-border-color:white");
        }
    }

    //zakończenie nagrywania
    private void stopAudio() {
        targetDataLine.stop();
        targetDataLine.close();
    }
    //rozpoczęcie nagrywania dźwięku
    private void captureAudio() throws LineUnavailableException {
        audioFormat = new AudioFormat(8000.0F, 16, 1, true, false);
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        CaptureThread captureThread = new CaptureThread();
        captureThread.start();
    }
    //dodatkowy wątek odpowiedzialny za nagrywanie dźwięku w trakcie działania programu
    class CaptureThread extends Thread {
        @Override
        public void run() {
            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
            File audioFile = new File("nagranie.wav");
            try {
                targetDataLine.open(audioFormat);
                targetDataLine.start();
                AudioSystem.write(new AudioInputStream(targetDataLine), fileType, audioFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
