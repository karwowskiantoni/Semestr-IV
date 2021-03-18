public class Postac {
    public int iloscPunktowZycia;
    public int regeneracjaPunktowZycia;
    public int wartoscAtaku;
    public int szybkoscAtaku;
    public int wartoscPancerza;
    public int procentowaSzansaNaTrafienie;
    public int procentowaSzansaNaUnik;
    public String opisPostaci;
    public Wzmocnienie[] przedmioty;
    public Wzmocnienie[] cechy;

    public Postac(int iloscPunktowZycia, int regeneracjaPunktowZycia, int wartoscAtaku,
                  int szybkoscAtaku, int wartoscPancerza, int procentowaSzansaNaTrafienie,
                  int procentowaSzansaNaUnik, String opisPostaci, Wzmocnienie[] przedmioty, Wzmocnienie[] cechy) {
        this.iloscPunktowZycia = iloscPunktowZycia;
        this.regeneracjaPunktowZycia = regeneracjaPunktowZycia;
        this.wartoscAtaku = wartoscAtaku;
        this.szybkoscAtaku = szybkoscAtaku;
        this.wartoscPancerza = wartoscPancerza;
        this.procentowaSzansaNaTrafienie = procentowaSzansaNaTrafienie;
        this.procentowaSzansaNaUnik = procentowaSzansaNaUnik;
        this.opisPostaci = opisPostaci;
        this.przedmioty = przedmioty;
        this.cechy = cechy;
    }

    public boolean walka(Postac wrog, Wzmocnienie[] przedmioty) {
        int inicjatywaBohatera = szybkoscAtaku;
        int inicjatywaWroga = wrog.szybkoscAtaku;
        while (true) {
            if (zadajCios(wrog) == 0) {
                System.out.println("Poślizgnął się na skórce od banana");
            }
            if (iloscPunktowZycia <= 0) {
                return false;
            }
            if (wrog.iloscPunktowZycia <= 0){
                return true;
            }
            break;
        }
        return true;
    }

    public String wypiszInformacjeOPostaci() {
        return "Grzegorz Brzęczyszczykiewicz";
    }

    public int zadajCios(Postac wrog) {
        if (czyTrafil(wrog)) {
            int obrazenia = this.wartoscAtaku - wrog.wartoscPancerza;
            if (obrazenia > 0) {
                return obrazenia;
            }
            return 0;
        }
        return 0;
    }

    public boolean czyTrafil(Postac wrog) {
        int ostatecznaSzansaNaTrafienie = procentowaSzansaNaTrafienie - wrog.procentowaSzansaNaUnik;
        return Math.random() < (double) ostatecznaSzansaNaTrafienie / 100;
    }

    public int liczenieInicjatywy(Postac wrog, int inicjatywaBohatera, int inicjatywaWroga){
        int roznicaSzybkosci = inicjatywaBohatera - inicjatywaWroga;
        if (roznicaSzybkosci > 0) {
            inicjatywaBohatera -= inicjatywaWroga;
        }
        return 0;
    }
}
