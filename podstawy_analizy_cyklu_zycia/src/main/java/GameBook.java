import model.Postac;
import model.Wzmocnienie;
import utils.ParserZdarzen;
import utils.Renderer;
import model.widoki.Zdarzenie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameBook {

    public static void main(String args[]) throws IOException {
        gra(30, 90, 80);
        //wyszukiwarkaZdarzen();
    }



    private static void gra(int wysokoscOkna, int szerokoscOkna, int szerokoscTekstu) throws IOException{

        Postac bohater = new Postac();

        bohater.setNazwaPostaci("zbyszek");
        bohater.setIloscPunktowZycia(50);
        bohater.setRegeneracjaPunktowZycia(8);
        bohater.setWartoscAtaku(10);
        bohater.setSzybkoscAtaku(5);
        bohater.setWartoscPancerza(3);
        bohater.setProcentowaSzansaNaTrafienie(100);
        bohater.setProcentowaSzansaNaUnik(10);

        Wzmocnienie mieczyk = new Wzmocnienie();
        mieczyk.setNazwa("zbyt krótki miecz");
        mieczyk.setWartoscAtaku(10);
        bohater.zdobadzPrzedmiot(mieczyk);

        Wzmocnienie pancerzyk = new Wzmocnienie();
        pancerzyk.setNazwa("chujowy pancerz");
        pancerzyk.setWartoscPancerza(3);
        bohater.zdobadzPrzedmiot(pancerzyk);

        Wzmocnienie płaszczyk = new Wzmocnienie();
        płaszczyk.setNazwa("płaszcz zwinnego skurwysyna");
        płaszczyk.setProcentowaSzansaNaUnik(50);
        bohater.zdobadzPrzedmiot(płaszczyk);

        Zdarzenie aktualneZdarzenie = ParserZdarzen.stworzZdarzenie("0");
        Renderer renderer = new Renderer(wysokoscOkna, szerokoscOkna, szerokoscTekstu);

        while (true) {
            aktualneZdarzenie = aktualneZdarzenie.wykonajZdarzenie(renderer, bohater);
        }
    }
}

