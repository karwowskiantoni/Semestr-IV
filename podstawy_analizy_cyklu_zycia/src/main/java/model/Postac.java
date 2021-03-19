package model;

public class Postac {
    public int iloscPunktowZycia;
    public int regeneracjaPunktowZycia;
    public int wartoscAtaku;
    public int szybkoscAtaku;
    public int wartoscPancerza;
    public int procentowaSzansaNaTrafienie;
    public int procentowaSzansaNaUnik;
    public String nazwaPostaci;
    public Wzmocnienie[] przedmioty;
    public Wzmocnienie[] cechy;

    public Postac(int iloscPunktowZycia, int regeneracjaPunktowZycia, int wartoscAtaku,
                  int szybkoscAtaku, int wartoscPancerza, int procentowaSzansaNaTrafienie,
                  int procentowaSzansaNaUnik, String nazwaPostaci, Wzmocnienie[] cechy) {
        this.iloscPunktowZycia = iloscPunktowZycia;
        this.regeneracjaPunktowZycia = regeneracjaPunktowZycia;
        this.wartoscAtaku = wartoscAtaku;
        this.szybkoscAtaku = szybkoscAtaku;
        this.wartoscPancerza = wartoscPancerza;
        this.procentowaSzansaNaTrafienie = procentowaSzansaNaTrafienie;
        this.procentowaSzansaNaUnik = procentowaSzansaNaUnik;
        this.nazwaPostaci = nazwaPostaci;
        this.cechy = cechy;
    }

    public boolean walka(Postac wrog) {
        int inicjatywaBohatera = szybkoscAtaku;
        int inicjatywaWroga = wrog.szybkoscAtaku;
        int licznikTur = 1;
        while (true) {
            System.out.println();
            System.out.println("Zaczela sie tura nr " + licznikTur);
            System.out.println();
            int[] daneTury = liczenieInicjatywy(inicjatywaBohatera, inicjatywaWroga);
            if (daneTury[0] == 1){

                if (daneTury[1] != 0){
                    inicjatywaBohatera -= daneTury[1] * inicjatywaWroga;
                } else {
                    inicjatywaBohatera -= inicjatywaWroga;
                }
                inicjatywaWroga = 0;

                int wynik = wymianaCiosow(wrog, daneTury);

                if (wynik == 1){
                    return true;
                } else if (wynik == 2){
                    return false;
                }

            } else {
                if (daneTury[1] != 0){
                    inicjatywaWroga -= daneTury[1] * inicjatywaBohatera;
                } else {
                    inicjatywaWroga -= inicjatywaBohatera;
                }
                inicjatywaBohatera = 0;

                int wynik = wrog.wymianaCiosow(this, daneTury);

                if (wynik == 1){
                    return false;
                } else if (wynik == 2){
                    return true;
                }
            }

            inicjatywaBohatera += szybkoscAtaku;
            inicjatywaWroga += wrog.szybkoscAtaku;
            licznikTur++;
        }
    }

    private String wypiszInformacjeOPostaci() {
        return "Grzegorz Brzęczyszczykiewicz";
    }

    private boolean czyTrafil(Postac wrog) {
        int ostatecznaSzansaNaTrafienie = procentowaSzansaNaTrafienie - wrog.procentowaSzansaNaUnik;
        return Math.random() < ostatecznaSzansaNaTrafienie/100.0;
    }

    private int wyprowadzCios(Postac wrog) {
        if (czyTrafil(wrog)) {
            int obrazenia = this.wartoscAtaku - wrog.wartoscPancerza;
            if (obrazenia > 0) {
                return obrazenia;
            }
            System.out.println(nazwaPostaci + " atakuje łyżką zamiast mieczem");
            return 0;
        }
        System.out.println(nazwaPostaci + " poślizgnął się na skórce od banana");
        return 0;
    }

    private void zadajObrazenia(Postac wrog){
        int obrazeniaZadanePrzezBohatera = wyprowadzCios(wrog);
        if (obrazeniaZadanePrzezBohatera == 0) {
        } else {
            wrog.iloscPunktowZycia -= obrazeniaZadanePrzezBohatera;
            System.out.println(nazwaPostaci + " zadanł " + wrog.nazwaPostaci + " " + obrazeniaZadanePrzezBohatera + " punktów obrażeń!");
        }
    }

    private int wymianaCiosow(Postac wrog, int[] daneTury){
        for (int i = 0; i < daneTury[1]; i++) {
            zadajObrazenia(wrog);
            if (wrog.iloscPunktowZycia <= 0) {
                System.out.println(wrog.nazwaPostaci + "odszedł do krainy wiecznych łowów");
                return 1;
            }
            System.out.println("Życie " + wrog.nazwaPostaci + " wynosi " + wrog.iloscPunktowZycia);
        }
        wrog.zadajObrazenia(this);
        System.out.println("Życie " + nazwaPostaci + " wynosi " + iloscPunktowZycia);
        if (iloscPunktowZycia <= 0) {
            return 2;
        }
        return 0;
    }

    private int[] liczenieInicjatywy(int inicjatywaBohatera, int inicjatywaWroga){
        int roznicaSzybkosci = inicjatywaBohatera - inicjatywaWroga;
        int[] info = new int[] {0,0};
        if (roznicaSzybkosci > 0) {
            int iloscAtakow = inicjatywaBohatera/inicjatywaWroga;
            info[0] = 1;
            info[1] = iloscAtakow;
        } else {
            int iloscAtakow = inicjatywaWroga/inicjatywaBohatera;
            info[0] = 2;
            info[1] = iloscAtakow;
        }
        return info;
    }
}

