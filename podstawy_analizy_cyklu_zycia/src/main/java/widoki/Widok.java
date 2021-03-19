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
        renderer.RenderujTekst(opis + System.lineSeparator()+ "." + System.lineSeparator() + "NACIŚNIJ ENTER ABY KONTYNUOWAĆ");
        Interpreter.czekajNaUzytkownika();
    }

}
