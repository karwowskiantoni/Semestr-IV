package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import widoki.Zdarzenie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class ParserZdarzen {

    public static Zdarzenie stworzZdarzenie(String nazwaPliku){
        String dane = odczytajzPliku(nazwaPliku);
        ObjectMapper mapper = new ObjectMapper();
        Zdarzenie zdarzenie = null;
        try {
            zdarzenie = mapper.readValue(dane.replace(System.lineSeparator(), ""), Zdarzenie.class);
            zdarzenie.setOpis(zdarzenie.getOpis().replace("~", System.lineSeparator() + "." + System.lineSeparator()));
        } catch (JsonProcessingException e) {
            System.out.println("BŁĘDNY FORMAT PLIKU: "+ nazwaPliku);
            System.out.println(dane);
            System.exit(1);
        }
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
