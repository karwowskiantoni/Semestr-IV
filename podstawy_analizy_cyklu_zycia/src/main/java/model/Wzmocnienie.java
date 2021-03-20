package model;

import model.Postac;

public class Wzmocnienie {
    public String nazwa;
    public int iloscPunktowZycia;
    public int regeneracjaPunktowZycia;
    public int wartoscAtaku;
    public int szybkoscAtaku;
    public int wartoscPancerza;
    public int procentowaSzansaNaTrafienie;
    public int procentowaSzansaNaUnik;

    public Wzmocnienie(String nazwa, int iloscPunktowZycia, int regeneracjaPunktowZycia,
                       int wartoscAtaku, int szybkoscAtaku, int wartoscPancerza,
                       int procentowaSzansaNaTrafienie, int procentowaSzansaNaUnik) {
        this.nazwa = nazwa;
        this.iloscPunktowZycia = iloscPunktowZycia;
        this.regeneracjaPunktowZycia = regeneracjaPunktowZycia;
        this.wartoscAtaku = wartoscAtaku;
        this.szybkoscAtaku = szybkoscAtaku;
        this.wartoscPancerza = wartoscPancerza;
        this.procentowaSzansaNaTrafienie = procentowaSzansaNaTrafienie;
        this.procentowaSzansaNaUnik = procentowaSzansaNaUnik;
    }

    public Wzmocnienie() {
    }

    public void wzmocnij(Postac bohater){
        bohater.iloscPunktowZycia += iloscPunktowZycia;
        bohater.regeneracjaPunktowZycia += regeneracjaPunktowZycia;
        bohater.wartoscAtaku += wartoscAtaku;
        bohater.szybkoscAtaku += szybkoscAtaku;
        bohater.wartoscPancerza += wartoscPancerza;
        bohater.procentowaSzansaNaTrafienie += procentowaSzansaNaTrafienie;
        bohater.procentowaSzansaNaUnik += procentowaSzansaNaUnik;
    }

    public void zdejmijPrzedmiot(Postac bohater){
        bohater.iloscPunktowZycia -= iloscPunktowZycia;
        bohater.regeneracjaPunktowZycia -= regeneracjaPunktowZycia;
        bohater.wartoscAtaku -= wartoscAtaku;
        bohater.szybkoscAtaku -= szybkoscAtaku;
        bohater.wartoscPancerza -= wartoscPancerza;
        bohater.procentowaSzansaNaTrafienie -= procentowaSzansaNaTrafienie;
        bohater.procentowaSzansaNaUnik -= procentowaSzansaNaUnik;
    }

    public String opis(){
        StringBuilder lista = new StringBuilder();
        lista.append(nazwa).append(": ");
        if(iloscPunktowZycia != 0){
            lista.append("życie +").append(iloscPunktowZycia).append(", ");
        }
        if(regeneracjaPunktowZycia != 0){
            lista.append("regeneracja +").append(regeneracjaPunktowZycia).append(", ");
        }
        if(wartoscAtaku != 0){
            lista.append("obrażenia +").append(wartoscAtaku).append(", ");
        }
        if(szybkoscAtaku != 0){
            lista.append("szybkość +").append(szybkoscAtaku).append(", ");
        }
        if(wartoscPancerza != 0){
            lista.append("pancerz +").append(wartoscPancerza).append(", ");
        }
        if(procentowaSzansaNaTrafienie != 0){
            lista.append("szansa na trafienie +").append(procentowaSzansaNaTrafienie).append("%, ");
        }
        if(procentowaSzansaNaUnik != 0){
            lista.append("szansa na unik +").append(procentowaSzansaNaUnik).append("%, ");
        }
        return lista.toString().substring(0, lista.length() - 2);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
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
}
