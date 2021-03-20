package model;

import widoki.Zdarzenie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Postac {
    public int iloscPunktowZycia;
    public int regeneracjaPunktowZycia;
    public int wartoscAtaku;
    public int szybkoscAtaku;
    public int wartoscPancerza;
    public int procentowaSzansaNaTrafienie;
    public int procentowaSzansaNaUnik;
    public String nazwaPostaci;
    public List<Wzmocnienie> przedmioty;
    public List<Wzmocnienie> cechy;
    public Zdarzenie zapis = null;

    public Postac(int iloscPunktowZycia, int regeneracjaPunktowZycia, int wartoscAtaku,
                  int szybkoscAtaku, int wartoscPancerza, int procentowaSzansaNaTrafienie,
                  int procentowaSzansaNaUnik, String nazwaPostaci) {
        this.iloscPunktowZycia = iloscPunktowZycia;
        this.regeneracjaPunktowZycia = regeneracjaPunktowZycia;
        this.wartoscAtaku = wartoscAtaku;
        this.szybkoscAtaku = szybkoscAtaku;
        this.wartoscPancerza = wartoscPancerza;
        this.procentowaSzansaNaTrafienie = procentowaSzansaNaTrafienie;
        this.procentowaSzansaNaUnik = procentowaSzansaNaUnik;
        this.nazwaPostaci = nazwaPostaci;
        this.cechy =  new ArrayList<>();
        this.przedmioty = new ArrayList<>();
    }

    public Postac(){

    }

    public String walka(Postac wrog) throws IOException {
        StringBuilder logWalki = new StringBuilder();
        int inicjatywaBohatera = szybkoscAtaku;
        int inicjatywaWroga = wrog.szybkoscAtaku;
        int licznikTur = 1;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Walczysz z " + wrog.nazwaPostaci + System.lineSeparator());
        System.out.println("Jego statystyki to " + wrog.wypiszInformacjeOPostaci(wrog.nazwaPostaci) + System.lineSeparator());

//        List<Wzmocnienie> smietnik = new ArrayList<>();

//        if(przedmioty.size() != 0){
//            while(true){
//                System.out.println(("Czy chcesz skorzystać z przedmiotów w sakwie? Wpisz 't' jeśli tak, 'n' jeśli nie."));
//                String odpowiedz = br.readLine();
//                if (odpowiedz.equals("t")){
//                    System.out.println("Podaj nazwe przedmiotu, w który chcesz się wyposażyć");
//                    String nazwa = br.readLine();
//                    smietnik.add(zalozPrzedmiot(nazwa));
//                } else {
//                    System.out.println("Stwierdasz, że masz pięści jak kamienie i nimi będziesz łotra okładał!");
//                    break;
//                }
//            }
//        } else {
//            System.out.println("Sakwa pusta jak niejedna makówka, bohater decyduje się zlać wroga gołymi pięściami");
//        }


        while (true) {
            logWalki.append(System.lineSeparator());
            logWalki.append("Zaczęła się tura nr ").append(licznikTur);
            logWalki.append(System.lineSeparator());
            int[] daneTury = liczenieInicjatywy(inicjatywaBohatera, inicjatywaWroga);
            if (daneTury[0] == 1){

                if (daneTury[1] != 0){
                    inicjatywaBohatera -= daneTury[1] * inicjatywaWroga;
                } else {
                    inicjatywaBohatera -= inicjatywaWroga;
                }
                inicjatywaWroga = 0;

                int wynik = wymianaCiosow(wrog, daneTury, logWalki);

                if (wynik == 1){
                    return logWalki.toString();
                } else if (wynik == 2){
                    return logWalki.toString();
                }

            } else {
                if (daneTury[1] != 0){
                    inicjatywaWroga -= daneTury[1] * inicjatywaBohatera;
                } else {
                    inicjatywaWroga -= inicjatywaBohatera;
                }
                inicjatywaBohatera = 0;

                int wynik = wrog.wymianaCiosow(this, daneTury, logWalki);

                if (wynik == 1){
                    return logWalki.toString();
                } else if (wynik == 2){
                    return logWalki.toString();
                }
            }

            inicjatywaBohatera += szybkoscAtaku;
            inicjatywaWroga += wrog.szybkoscAtaku;
            licznikTur++;
        }
    }

    public String wypiszInformacjeOPostaci(String naglowek) {
        StringBuilder info = new StringBuilder();
        info.append("==================================================================").append(System.lineSeparator());
        info.append("                       ***").append(naglowek).append("***                       ").append(System.lineSeparator());
        info.append("Imię:                ").append(nazwaPostaci).append(System.lineSeparator());
        info.append("Ilość punktów życia: ").append(iloscPunktowZycia).append(System.lineSeparator());
        info.append("Wartość pancerza:    ").append(wartoscPancerza).append(System.lineSeparator());
        info.append("Wartość ataku:       ").append(wartoscAtaku).append(System.lineSeparator());
        info.append("Szansa na trafienie: ").append(procentowaSzansaNaTrafienie).append("%").append(System.lineSeparator());
        info.append("Szybkość ataku:      ").append(szybkoscAtaku).append(System.lineSeparator());
        info.append("==================================================================").append(System.lineSeparator());
        info.append("                           ***CECHY***                            ").append(System.lineSeparator());
        info.append(zbiorCech()).append(System.lineSeparator());
        info.append("==================================================================").append(System.lineSeparator());
        info.append("                   ***PRZEDMIOTY W SAKWIE***                      ").append(System.lineSeparator());
        info.append(listaPrzedmiotow()).append(System.lineSeparator());
        info.append("==================================================================").append(System.lineSeparator());

        return info.toString();
    }

    public void zdobadzPrzedmiot(Wzmocnienie przedmiot){
        przedmioty.add(przedmiot);
    }

    public void zdobadzCeche(Wzmocnienie cecha){
        cechy.add(cecha);
        cecha.wzmocnij(this);
    }


    public Wzmocnienie zalozPrzedmiot(String nazwa){
        for (Wzmocnienie przedmiot: przedmioty){
            if(przedmiot.nazwa.equals(nazwa)){
                przedmiot.wzmocnij(this);
                return przedmiot;
            }
        }
        return new Wzmocnienie("brak",0,0,0,0,0,0,0);
    }

    private boolean czyTrafil(Postac wrog) {
        int ostatecznaSzansaNaTrafienie = procentowaSzansaNaTrafienie - wrog.procentowaSzansaNaUnik;
        return Math.random() < ostatecznaSzansaNaTrafienie/100.0;
    }

    private int wyprowadzCios(Postac wrog, StringBuilder log) {
        if (czyTrafil(wrog)) {
            int obrazenia = this.wartoscAtaku - wrog.wartoscPancerza;
            if (obrazenia > 0) {
                return obrazenia;
            }
            log.append(nazwaPostaci).append(" atakuje łyżką zamiast mieczem").append(System.lineSeparator());
            return 0;
        }
        log.append(nazwaPostaci).append(" poślizgnął się na skórce od banana").append(System.lineSeparator());
        return 0;
    }

    private void zadajObrazenia(Postac wrog, StringBuilder log){
        int obrazeniaZadanePrzezBohatera = wyprowadzCios(wrog, log);
        if (obrazeniaZadanePrzezBohatera != 0) {
            wrog.iloscPunktowZycia -= obrazeniaZadanePrzezBohatera;
            log.append(nazwaPostaci).append(" zadał ").append(wrog.nazwaPostaci).append(" ")
                    .append(obrazeniaZadanePrzezBohatera).append(" punktów obrażeń!").append(System.lineSeparator());
        }
    }

    private int wymianaCiosow(Postac wrog, int[] daneTury, StringBuilder log){
        for (int i = 0; i < daneTury[1]; i++) {
            zadajObrazenia(wrog, log);
            if (wrog.iloscPunktowZycia <= 0) {
                log.append(wrog.nazwaPostaci).append(" odszedł do krainy wiecznych łowów").append(System.lineSeparator());
                return 1;
            }
            log.append("Życie ").append(wrog.nazwaPostaci).append(" wynosi ")
                    .append(wrog.iloscPunktowZycia).append(System.lineSeparator());
        }
        wrog.zadajObrazenia(this, log);
        log.append("Życie ").append(nazwaPostaci).append(" wynosi ").append(iloscPunktowZycia)
                                                                .append(System.lineSeparator());
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

    private String zbiorCech(){
        StringBuilder zbior = new StringBuilder();
        int licznik = 0;
        for(Wzmocnienie cecha: cechy){
            zbior.append(cecha.nazwa);
            licznik++;
            if(cechy.size() == licznik){
                zbior.append(".");
            } else {
                zbior.append(", ").append(System.lineSeparator());
            }
        }
        return zbior.toString();
    }

    private String listaPrzedmiotow(){
        StringBuilder lista = new StringBuilder();
        int licznik = 0;
        if(przedmioty.size() == 0){
            lista.append("Brak przedmiotów w sakwie. Trzeba ograbić więcej przeciwników!");
            return lista.toString();
        }
        for(Wzmocnienie przedmiot: przedmioty){
            lista.append(przedmiot.nazwa);
            licznik++;
            if(przedmioty.size() == licznik){
                lista.append(".");
            } else {
                lista.append(", ").append(System.lineSeparator());
            }
        }
        return lista.toString();
    }

    private void zuzywaniePrzedmiotow(List<Wzmocnienie> smietnik){

    }

    public int getIloscPunktowZycia() {
        return iloscPunktowZycia;
    }

    public void setIloscPunktowZycia(int iloscPunktowZycia) {
        this.iloscPunktowZycia = iloscPunktowZycia;
    }

    public int getRegeneracjaPunktowZycia() {
        return regeneracjaPunktowZycia;
    }

    public void setRegeneracjaPunktowZycia(int regeneracjaPunktowZycia) {
        this.regeneracjaPunktowZycia = regeneracjaPunktowZycia;
    }

    public int getWartoscAtaku() {
        return wartoscAtaku;
    }

    public void setWartoscAtaku(int wartoscAtaku) {
        this.wartoscAtaku = wartoscAtaku;
    }

    public int getSzybkoscAtaku() {
        return szybkoscAtaku;
    }

    public void setSzybkoscAtaku(int szybkoscAtaku) {
        this.szybkoscAtaku = szybkoscAtaku;
    }

    public int getWartoscPancerza() {
        return wartoscPancerza;
    }

    public void setWartoscPancerza(int wartoscPancerza) {
        this.wartoscPancerza = wartoscPancerza;
    }

    public int getProcentowaSzansaNaTrafienie() {
        return procentowaSzansaNaTrafienie;
    }

    public void setProcentowaSzansaNaTrafienie(int procentowaSzansaNaTrafienie) {
        this.procentowaSzansaNaTrafienie = procentowaSzansaNaTrafienie;
    }

    public int getProcentowaSzansaNaUnik() {
        return procentowaSzansaNaUnik;
    }

    public void setProcentowaSzansaNaUnik(int procentowaSzansaNaUnik) {
        this.procentowaSzansaNaUnik = procentowaSzansaNaUnik;
    }

    public String getNazwaPostaci() {
        return nazwaPostaci;
    }

    public void setNazwaPostaci(String nazwaPostaci) {
        this.nazwaPostaci = nazwaPostaci;
    }

    public List<Wzmocnienie> getPrzedmioty() {
        return przedmioty;
    }

    public void setPrzedmioty(List<Wzmocnienie> przedmioty) {
        this.przedmioty = przedmioty;
    }

    public List<Wzmocnienie> getCechy() {
        return cechy;
    }

    public void setCechy(List<Wzmocnienie> cechy) {
        this.cechy = cechy;
    }

    public Zdarzenie getZapis() {
        return zapis;
    }

    public void setZapis(Zdarzenie zapis) {
        this.zapis = zapis;
    }
}

