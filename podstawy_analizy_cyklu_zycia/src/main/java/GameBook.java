import model.Postac;
import model.Wzmocnienie;
import utils.Parser;
import utils.Renderer;
import model.widoki.Zdarzenie;
import utils.ZapisOdczytException;

import java.io.Console;
import java.io.IOException;

public class GameBook {
    static Parser parser = new Parser();
    static Renderer renderer = new Renderer(40, 90, 80);
    static Postac bohater = new Postac();
    static Zdarzenie aktualneZdarzenie = parser.stworzZdarzenie("menu", false);

    public static void main(String args[]) throws IOException {

        Wzmocnienie mieczyk = new Wzmocnienie();
        mieczyk.setNazwa("zbyt krotki miecz");
        mieczyk.setWartoscAtaku(10);
        bohater.zdobadzPrzedmiot(mieczyk);

        Wzmocnienie pancerzyk = new Wzmocnienie();
        pancerzyk.setNazwa("welniane kalesony");
        pancerzyk.setWartoscPancerza(3);
        bohater.zdobadzPrzedmiot(pancerzyk);

        Wzmocnienie plaszczyk = new Wzmocnienie();
        plaszczyk.setNazwa("plaszcz zwinnego antoniego");
        plaszczyk.setProcentowaSzansaNaUnik(50);
        bohater.zdobadzPrzedmiot(plaszczyk);

        bohater.setNazwaPostaci("Bezimienny Bohater");
        bohater.setIloscPunktowZycia(50);
        bohater.setWartoscAtaku(10);
        bohater.setWartoscPancerza(3);
        bohater.setProcentowaSzansaNaTrafienie(100);
        bohater.setProcentowaSzansaNaUnik(10);
        bohater.setSzybkoscAtaku(5);

        while (true) {
            try {
                aktualneZdarzenie = aktualneZdarzenie.wykonajZdarzenie(renderer, bohater);
            } catch(ZapisOdczytException exception) {
                if(exception.getMessage().equals("zapisz")) {
                    parser.zapiszZdarzenie(aktualneZdarzenie);
                    parser.zapiszPostac(bohater);
                }
                else if(exception.getMessage().equals("wczytaj")) {
                    aktualneZdarzenie = parser.stworzZdarzenie("zapis_zdarzenia", true);
                    bohater = parser.stworzPostac("zapis_postaci", true);
                }
            }
        }
    }


}

