public class Zdarzenie {
    public int id;
    public String Opis;
    public int[] nastepneID;

    public Zdarzenie(int id, String opis, int[] nastepneID, String[] wybory) {
        this.id = id;
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
