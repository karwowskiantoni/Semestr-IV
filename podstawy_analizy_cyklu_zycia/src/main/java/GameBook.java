import model.Postac;
import model.Wzmocnienie;
import utils.Parser;
import utils.Renderer;
import model.widoki.Zdarzenie;
import utils.ZapisOdczytException;

import java.io.IOException;

public class GameBook {

    public static void main(String args[]) throws IOException {
        gra(30, 90, 80);
        //wyszukiwarkaZdarzen();
    }



    private static void gra(int wysokoscOkna, int szerokoscOkna, int szerokoscTekstu) throws IOException{

        Postac bohater = new Postac();
        Zdarzenie aktualneZdarzenie = Parser.stworzZdarzenie("prolog");

        bohater.setNazwaPostaci("zbyszek");
        bohater.setIloscPunktowZycia(50);
        bohater.setRegeneracjaPunktowZycia(0);
        bohater.setWartoscAtaku(10);
        bohater.setSzybkoscAtaku(5);
        bohater.setWartoscPancerza(3);
        bohater.setProcentowaSzansaNaTrafienie(70);
        bohater.setProcentowaSzansaNaUnik(10);

        Wzmocnienie mieczyk = new Wzmocnienie();
        mieczyk.setNazwa("zbyt krótki miecz");
        mieczyk.setWartoscAtaku(10);
        bohater.zdobadzPrzedmiot(mieczyk);

        Wzmocnienie pancerzyk = new Wzmocnienie();
        pancerzyk.setNazwa("wełniane kalesony");
        pancerzyk.setWartoscPancerza(3);
        bohater.zdobadzPrzedmiot(pancerzyk);

        Wzmocnienie plaszczyk = new Wzmocnienie();
        plaszczyk.setNazwa("płaszcz zwinnego skurwysyna");
        plaszczyk.setProcentowaSzansaNaUnik(50);
        bohater.zdobadzPrzedmiot(plaszczyk);

        Renderer renderer = new Renderer(wysokoscOkna, szerokoscOkna, szerokoscTekstu);

        while (true) {
            try {
                aktualneZdarzenie = aktualneZdarzenie.wykonajZdarzenie(renderer, bohater);
            } catch(ZapisOdczytException exception) {
                if(exception.getMessage().equals("zapisz")) {
                    Parser.zapiszZdarzenie(aktualneZdarzenie);
                    Parser.zapiszPostac(bohater);
                }
                else if(exception.getMessage().equals("wczytaj")) {
                    aktualneZdarzenie = Parser.stworzZdarzenie("zapis_zdarzenia");
                    bohater = Parser.stworzPostac("zapis_postaci");
                }
            }
        }
    }
}

