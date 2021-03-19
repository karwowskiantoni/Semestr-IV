package utils;

import widoki.WidokZdarzenia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class ParserWidokow {

    public static WidokZdarzenia stworzZdarzenie(String nazwaPliku) throws FileNotFoundException {
        String dane = odczytajzPliku(nazwaPliku);
        String[] parametry = dane.split("~");


        // troche pojebane parsowanie, tak nie powinno byc ale no
        // coz niech bedzie takie spaghetti, ja mniej wiecej ogarniam
        String typ = parametry[1].replace(System.lineSeparator(), "").split(":")[1];
        String opis = parametry[2].split(":")[1];
        int nastepny1 = Integer.parseInt(parametry[3].replace(System.lineSeparator(), "").split(":")[1]);
        int nastepny2 = Integer.parseInt(parametry[4].replace(System.lineSeparator(), "").split(":")[1]);


        return new WidokZdarzenia(opis, new int[]{nastepny1, nastepny2});
    }

    private static String odczytajzPliku(String nazwa) throws FileNotFoundException {
        StringBuilder dane = new StringBuilder();

        File plik = new File("/home/karwoant/Uczelnia/Semestr_IV/podstawy_analizy_cyklu_zycia/src/main/resources/zdarzenia/" + nazwa);
        Scanner skaner = new Scanner(plik);
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
