import model.Postac;
import utils.ParserZdarzen;
import utils.Renderer;
import widoki.Zdarzenie;

import java.io.IOException;

public class GameBook {

    public static void main(String args[]) throws IOException {
        gra(30, 90, 80);
        //wyszukiwarkaZdarzen();
    }



    private static void gra(int wysokoscOkna, int szerokoscOkna, int szerokoscTekstu) throws IOException{

        Postac bohater = new Postac(50,
                10,
                8,
                5,
                0,
                100,
                0,
                "Zbyszek");

        Zdarzenie aktualneZdarzenie = ParserZdarzen.stworzZdarzenie("0");
        Renderer renderer = new Renderer(wysokoscOkna, szerokoscOkna, szerokoscTekstu);


        while (true) {
            aktualneZdarzenie = aktualneZdarzenie.wykonajZdarzenie(renderer, bohater);
        }
    }
}

