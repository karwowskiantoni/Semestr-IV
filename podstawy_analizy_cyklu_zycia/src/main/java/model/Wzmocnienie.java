package model;

import model.Postac;

public class Wzmocnienie {
    private String nazwa = "nic";
    private int iloscPunktowZycia = 0;
    private int wartoscAtaku = 0;
    private int szybkoscAtaku = 0;
    private int wartoscPancerza = 0;
    private int procentowaSzansaNaTrafienie = 0;
    private int procentowaSzansaNaUnik = 0;

    public Wzmocnienie() {
    }

    public void wzmocnij(Postac bohater){
        bohater.setIloscPunktowZycia(iloscPunktowZycia + bohater.getIloscPunktowZycia());
        bohater.setWartoscAtaku(wartoscAtaku + bohater.getWartoscAtaku());
        bohater.setSzybkoscAtaku(szybkoscAtaku + bohater.getSzybkoscAtaku());
        bohater.setWartoscPancerza(wartoscPancerza + bohater.getWartoscPancerza());
        bohater.setProcentowaSzansaNaTrafienie(procentowaSzansaNaTrafienie + bohater.getProcentowaSzansaNaTrafienie());
        bohater.setProcentowaSzansaNaUnik(procentowaSzansaNaUnik + bohater.getProcentowaSzansaNaUnik());
    }

    public void anulujWzmocnienie(Postac bohater){
        bohater.setIloscPunktowZycia(bohater.getIloscPunktowZycia() - iloscPunktowZycia);
        bohater.setWartoscAtaku(bohater.getWartoscAtaku() - wartoscAtaku);
        bohater.setSzybkoscAtaku(bohater.getSzybkoscAtaku() - szybkoscAtaku);
        bohater.setWartoscPancerza(bohater.getWartoscPancerza() - wartoscPancerza);
        bohater.setProcentowaSzansaNaTrafienie(bohater.getProcentowaSzansaNaTrafienie() - procentowaSzansaNaTrafienie);
        bohater.setProcentowaSzansaNaUnik(bohater.getProcentowaSzansaNaUnik() - procentowaSzansaNaUnik);
    }

    public String opis(){
        StringBuilder lista = new StringBuilder();
        lista.append(nazwa).append(": ");
        if(iloscPunktowZycia != 0){
            lista.append("życie +").append(iloscPunktowZycia).append(", ");
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
