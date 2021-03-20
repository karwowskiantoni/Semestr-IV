package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.widoki.Zdarzenie;
import model.widoki.ZdarzenieWalki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class ParserZdarzen {

    public static Zdarzenie stworzZdarzenie(String nazwaPliku){

        String plik = odczytajzPliku(nazwaPliku);
        String typZdarzenia = plik.split("&")[0];
        String dane = plik.split("&")[1].replace(System.lineSeparator(), "");

        ObjectMapper mapper = new ObjectMapper();
        Zdarzenie zdarzenie = null;
        try {

            if(typZdarzenia.equals("zdarzenie")) {
                zdarzenie = mapper.readValue(dane, Zdarzenie.class);
            } else if(typZdarzenia.equals("zdarzenie walki")) {
                zdarzenie = mapper.readValue(dane, ZdarzenieWalki.class);
            }


        } catch (JsonProcessingException e) {
            System.out.println("BŁĘDNY FORMAT PLIKU: "+ nazwaPliku);
            System.out.println(e);
            System.out.println(dane);
            System.exit(1);
        }

        zdarzenie.setOpis(zdarzenie.getOpis().replace("~", System.lineSeparator() + "." + System.lineSeparator()));
        return zdarzenie;
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
