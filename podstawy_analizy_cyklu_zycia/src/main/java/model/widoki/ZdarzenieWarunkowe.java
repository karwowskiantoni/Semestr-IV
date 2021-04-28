package model.widoki;

import model.Postac;
import model.Wzmocnienie;
import utils.Parser;
import utils.Renderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ZdarzenieWarunkowe extends Zdarzenie{

    String alternatywnyOpis;
    Postac wzorzec;

    public ZdarzenieWarunkowe() {
        super();
    }

    public Postac getWzorzec() {
        return wzorzec;
    }

    public void setWzorzec(Postac wzorzec) {
        this.wzorzec = wzorzec;
    }

    public String getAlternatywnyOpis() {
        return alternatywnyOpis;
    }

    public void setAlternatywnyOpis(String alternatywnyOpis) {
        this.alternatywnyOpis = alternatywnyOpis;
    }

    @Override
    public Zdarzenie wykonajZdarzenie(Renderer renderer, Postac bohater) throws IOException {

        Parser parser = new Parser();
        int nastepny;



        if(porownajPostaci(bohater, wzorzec)) {
            nastepny = 0;
            renderer.renderujTekst(getOpis());
        } else {
            nastepny = 1;
            renderer.renderujTekst(getAlternatywnyOpis());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("WCIŚNIJ DOWOLNY KLAWISZ ABY KONTYNUOWAĆ");
        br.readLine();

        return parser.stworzZdarzenie(this.getNastepne()[nastepny], false);
    }

    private boolean porownajPostaci (Postac bohater, Postac wzorzec) {
        // porównywanie statystyk
        if(bohater.getIloscPunktowZycia() < wzorzec.getIloscPunktowZycia()) return false;
        if(bohater.getProcentowaSzansaNaTrafienie() < wzorzec.getProcentowaSzansaNaTrafienie()) return false;
        if(bohater.getProcentowaSzansaNaUnik() < wzorzec.getProcentowaSzansaNaUnik()) return false;
        if(bohater.getWartoscAtaku() < wzorzec.getWartoscAtaku()) return false;
        if(bohater.getWartoscPancerza() < wzorzec.getWartoscPancerza()) return false;
        if(bohater.getSzybkoscAtaku() < wzorzec.getSzybkoscAtaku()) return false;
        //porównywanie przedmiotów
        int licznikPrzedmiotow = 0;
        for(Wzmocnienie przedmiot: bohater.getPrzedmioty()) {
            for(Wzmocnienie przedmiotWzorcowy: wzorzec.getPrzedmioty()) {
                if (przedmiot.getNazwa().equals(przedmiotWzorcowy.getNazwa())) licznikPrzedmiotow++;
            }
        }
        if (licznikPrzedmiotow < wzorzec.getPrzedmioty().size()) return false;
        //porównywanie cech
        int licznikCech = 0;
        for(Wzmocnienie cecha: bohater.getCechy()) {
            for(Wzmocnienie cechaWzorcowa: wzorzec.getCechy()) {
                if (cecha.getNazwa().equals(cechaWzorcowa.getNazwa())) licznikCech++;
            }
        }
        if (licznikCech < wzorzec.getCechy().size()) return false;
        return true;
    }
}
