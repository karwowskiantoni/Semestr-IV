package utils;

import com.google.gson.Gson;
import model.Postac;
import model.widoki.Zdarzenie;
import model.widoki.ZdarzenieWalki;

import java.io.*;
import java.util.Scanner;
public class Parser {

    public static Zdarzenie stworzZdarzenie(String nazwaPliku){

        String plik = odczytajzPliku(nazwaPliku);
        String typZdarzenia = plik.split("&")[0];
        String dane = plik.split("&")[1].replace(System.lineSeparator(), "");

        Gson gson = new Gson();

        Zdarzenie zdarzenie = null;

        if(typZdarzenia.equals("zdarzenie")) {
            zdarzenie = gson.fromJson(dane, Zdarzenie.class);
        } else if(typZdarzenia.equals("zdarzenie walki")) {
            zdarzenie = gson.fromJson(dane, ZdarzenieWalki.class);
        }

        zdarzenie.setOpis(zdarzenie.getOpis().replace("~", System.lineSeparator() + "." + System.lineSeparator()));
        return zdarzenie;
    }

    public static Postac stworzPostac(String nazwaPliku) {
        String plik = odczytajzPliku(nazwaPliku);
        Gson gson = new Gson();
        return gson.fromJson(plik, Postac.class);
    }

    public static void zapiszPostac(Postac postac) throws IOException{
        Gson gson = new Gson();
        String json = gson.toJson(postac);
        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/karwoant/Uczelnia/Semestr_IV/podstawy_analizy_cyklu_zycia/src/main/resources/zdarzenia/zapis_postaci"));
        writer.write(json);
        writer.close();
    }

    public static void zapiszZdarzenie(Zdarzenie zdarzenie) throws IOException{
        Gson gson = new Gson();
        String json = gson.toJson(zdarzenie);
        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/karwoant/Uczelnia/Semestr_IV/podstawy_analizy_cyklu_zycia/src/main/resources/zdarzenia/zapis_zdarzenia"));
        if(zdarzenie.getClass() == Zdarzenie.class) {
            json = "zdarzenie&" + json;
        } else if(zdarzenie.getClass() == ZdarzenieWalki.class) {
            json = "zdarzenie walki&" + json;
        }
        writer.write(json);
        writer.close();
    }


    public static String odczytajzPliku(String nazwa) {
        StringBuilder dane = new StringBuilder();

        File plik = new File("/home/karwoant/Uczelnia/Semestr_IV/podstawy_analizy_cyklu_zycia/src/main/resources/zdarzenia/" + nazwa);
        Scanner skaner = null;
        try {
            skaner = new Scanner(plik);
        } catch (FileNotFoundException e) {
            System.out.println("NIE ZNALEZIONO PLIKU: " + nazwa);
            System.exit(1);
        }
        while (skaner.hasNextLine()) {
            String aktualnyWiersz = skaner.nextLine();
            if (!aktualnyWiersz.equals("")) {
                dane.append(aktualnyWiersz).append(System.lineSeparator());
            }

        }
        skaner.close();
        return dane.toString();
    }
}
