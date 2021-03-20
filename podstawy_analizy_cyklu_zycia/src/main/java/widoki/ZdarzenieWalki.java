package widoki;

import model.Postac;

public class ZdarzenieWalki extends Zdarzenie{
    Postac przeciwnik;

    public ZdarzenieWalki(String opis, String[] nastepne) {
        super(opis, nastepne);
    }
}
