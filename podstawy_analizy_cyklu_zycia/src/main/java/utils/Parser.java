package utils;

import com.google.gson.Gson;
import model.Postac;
import model.widoki.Zdarzenie;
import model.widoki.ZdarzenieWalki;
import model.widoki.ZdarzenieWarunkowe;
import model.widoki.ZdarzenieWzmocnienia;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Parser {

    public Zdarzenie stworzZdarzenie(String nazwaPliku, boolean czyLokalnie){

        String plik = odczytajzPliku(nazwaPliku, czyLokalnie);
        String typZdarzenia = plik.split("&")[0];
        String dane = plik.split("&")[1];

        Gson gson = new Gson();

        Zdarzenie zdarzenie = null;

        switch (typZdarzenia) {
            case "zdarzenie":
                zdarzenie = gson.fromJson(dane, Zdarzenie.class);
                break;
            case "zdarzenie walki":
                zdarzenie = gson.fromJson(dane, ZdarzenieWalki.class);
                break;
            case "zdarzenie wzmocnienia":
                zdarzenie = gson.fromJson(dane, ZdarzenieWzmocnienia.class);
                break;
            case "zdarzenie warunkowe":
                zdarzenie = gson.fromJson(dane, ZdarzenieWarunkowe.class);
                break;
            default:
                System.out.println("BŁĘDNA NAZWA TYPU ZDARZENIA W PLIKU: " + nazwaPliku);
                System.exit(1);
        }
        return zdarzenie;
    }

    public Postac stworzPostac(String nazwaPliku, boolean czyLokalnie) {
        String plik = odczytajzPliku(nazwaPliku, czyLokalnie);
        Gson gson = new Gson();
        return gson.fromJson(plik, Postac.class);
    }

    public void zapiszPostac(Postac postac) throws IOException{
        Gson gson = new Gson();
        String json = gson.toJson(postac);
        BufferedWriter writer = new BufferedWriter(new FileWriter("zapis_postaci"));
        writer.write(json);
        writer.close();
    }

    public void zapiszZdarzenie(Zdarzenie zdarzenie) throws IOException{
        Gson gson = new Gson();
        String json = gson.toJson(zdarzenie);
        BufferedWriter writer = new BufferedWriter(new FileWriter("zapis_zdarzenia"));
        if(zdarzenie.getClass() == Zdarzenie.class) {
            json = "zdarzenie&" + json;
        } else if(zdarzenie.getClass() == ZdarzenieWalki.class) {
            json = "zdarzenie walki&" + json;
        }
        writer.write(json);
        writer.close();
    }


    public String odczytajzPliku(String nazwa, boolean czyLokalnie) {
        String text = "";
        if(czyLokalnie) {
            try {
                File plik = new File(nazwa);
                Scanner myReader = new Scanner(plik);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    text += data;
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("NIE UDAŁO SIĘ WCZYTAĆ PLIKU: " + nazwa);
                e.printStackTrace();
            }


        } else {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(nazwa);
            text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        }



        return text
                .replace("\n", "")
                .replace(System.lineSeparator(), "")
                .replace("~", System.lineSeparator() + "." + System.lineSeparator());

    }
}
