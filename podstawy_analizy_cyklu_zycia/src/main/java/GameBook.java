import model.Postac;
import model.Wzmocnienie;
import utils.Interpreter;
import utils.ParserWidokow;
import utils.Renderer;
import widoki.WidokZdarzenia;

import java.io.IOException;

public class GameBook {

    public static void main(String args[]) throws IOException {
        gra(33, 90, 80);
        gra(20, 140, 120);
        //wyszukiwarkaZdarzen();
    }



    private static void gra(int wysokoscOkna, int szerokoscOkna, int szerokoscTekstu) throws IOException{

        WidokZdarzenia aktualnyWidok = ParserWidokow.stworzZdarzenie("0");
        Renderer skurwysynek = new Renderer(wysokoscOkna, szerokoscOkna, szerokoscTekstu);
        Postac bohater = new Postac(50,
                10,
                8,
                5,
                0,
                100,
                0,
                "Zbyszek",
                new Wzmocnienie[0]);



        while (true) {
            skurwysynek.RenderujTekst(aktualnyWidok.Opis);
            aktualnyWidok = Interpreter.wykonajPolecenie(aktualnyWidok, skurwysynek, bohater);

        }
    }
}

