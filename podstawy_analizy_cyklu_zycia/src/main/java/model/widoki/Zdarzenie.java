package model.widoki;

import model.Postac;
import utils.Parser;
import utils.Renderer;
import utils.ZapisOdczytException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Zdarzenie {
    private String nazwa = "";
    private String opis = "";
    private String[] nastepne = null;

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setNastepne(String[] nastepne) {
        this.nastepne = nastepne;
    }

    public String getOpis() {
        return opis;
    }

    public String[] getNastepne() {
        return nastepne;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Zdarzenie() {

    }

    public Zdarzenie wykonajZdarzenie(Renderer renderer, Postac bohater) throws IOException, ZapisOdczytException {

        renderer.renderujTekst(opis);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("WPROWADŹ POLECENIE: ");
        String input = br.readLine();

        String[] wejscie = input.split(" ");

        String polecenie = wejscie[0];

        switch (polecenie) {

            case "wybieram":
                int argument = Integer.parseInt(wejscie[1]);
                if(nastepne.length >= argument) {
                    renderer.renderujOknoInformacyjne( "WYBRANO " + argument + System.lineSeparator() + Parser.odczytajzPliku("dalej"));
                    return Parser.stworzZdarzenie(this.nastepne[argument -1]);
                } else {
                    renderer.renderujOknoInformacyjne("Nie ma takiego wyboru");
                }
                break;

            case "pomoc":
                renderer.renderujOknoInformacyjne(Parser.odczytajzPliku("pomoc"));
                break;

            case "bohater":
                renderer.renderujOknoInformacyjne(bohater.wypiszInformacjeOBohaterze("KARTA BOHATERA"));
                break;

            case  "wyjście":
                System.exit(0);

            case "wczytaj":
                renderer.renderujOknoInformacyjne("WCZYTANO");
                throw new ZapisOdczytException("wczytaj");

            case "zapisz":
                renderer.renderujOknoInformacyjne("ZAPISANO");
                throw new ZapisOdczytException("zapisz");

            default:
                renderer.renderujOknoInformacyjne(Parser.odczytajzPliku("błąd"));
        }
        return this;
    }
}
