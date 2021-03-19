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


    public void wzmocnij(Postac bohater){
        bohater.iloscPunktowZycia += iloscPunktowZycia;
        bohater.regeneracjaPunktowZycia += regeneracjaPunktowZycia;
        bohater.wartoscAtaku += wartoscAtaku;
        bohater.szybkoscAtaku += szybkoscAtaku;
        bohater.wartoscAtaku += wartoscAtaku;
        bohater.wartoscPancerza += wartoscPancerza;
        bohater.procentowaSzansaNaTrafienie += procentowaSzansaNaTrafienie;
        bohater.procentowaSzansaNaUnik += procentowaSzansaNaUnik;
    }

    public void zdejmijPrzedmiot(Postac bohater){
        bohater.iloscPunktowZycia -= iloscPunktowZycia;
        bohater.regeneracjaPunktowZycia -= regeneracjaPunktowZycia;
        bohater.wartoscAtaku -= wartoscAtaku;
        bohater.szybkoscAtaku -= szybkoscAtaku;
        bohater.wartoscAtaku -= wartoscAtaku;
        bohater.wartoscPancerza -= wartoscPancerza;
        bohater.procentowaSzansaNaTrafienie -= procentowaSzansaNaTrafienie;
        bohater.procentowaSzansaNaUnik -= procentowaSzansaNaUnik;
    }
}
