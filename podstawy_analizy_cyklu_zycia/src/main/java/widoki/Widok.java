package widoki;

import model.Postac;
import model.Wzmocnienie;
import utils.Interpreter;
import utils.Renderer;

public class Widok {
    public String opis;


    public Widok(String opis) {
        this.opis = opis;
    }

    public void render(Renderer renderer) {
        renderer.RenderujTekst(opis);
        Interpreter.czekajNaUzytkownika();
    }

}
