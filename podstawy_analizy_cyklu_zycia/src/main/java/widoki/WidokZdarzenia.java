package widoki;

import model.Postac;
import model.Wzmocnienie;
import utils.Renderer;

public class WidokZdarzenia {
    public String Opis;
    public int[] nastepneID;
    Renderer renderer;


    public WidokZdarzenia(String opis, int[] nastepneID) {
        Opis = opis;
        this.nastepneID = nastepneID;
    }

    public boolean zmienPunktyZycia(Postac bohater) {
        bohater.iloscPunktowZycia -= bohater.regeneracjaPunktowZycia;
        return bohater.iloscPunktowZycia >= 0;
    }

    public void dodajPrzedmioty(Postac bohater, Wzmocnienie[] przedmioty) {
        //bohater.przedmioty;
    }
}
