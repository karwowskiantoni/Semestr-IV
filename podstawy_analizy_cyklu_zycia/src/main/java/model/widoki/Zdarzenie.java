package model.widoki;

import model.Postac;
import utils.ParserZdarzen;
import utils.Renderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Zdarzenie {
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

    public Zdarzenie() {

    }

    public Zdarzenie wykonajZdarzenie(Renderer renderer, Postac bohater) throws IOException {

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
                    renderer.renderujOknoInformacyjne( "WYBRANO " + argument + System.lineSeparator() + ParserZdarzen.odczytajzPliku("dalej"));
                    return ParserZdarzen.stworzZdarzenie(this.nastepne[argument -1]);
                } else {
                    renderer.renderujOknoInformacyjne("Nie ma takiego wyboru");
                }
                break;

            case "pomoc":
                renderer.renderujOknoInformacyjne(ParserZdarzen.odczytajzPliku("pomoc"));
                break;

            case "bohater":
                renderer.renderujOknoInformacyjne(bohater.wypiszInformacjeOBohaterze("KARTA BOHATERA"));
                break;

            case  "wyjście":
                System.exit(0);

            case "wczytaj":
                if(bohater.getZapis() != null) {
                    renderer.renderujOknoInformacyjne("WCZYTANO");
                    return bohater.getZapis();
                }
                break;

            case "zapisz":
                renderer.renderujOknoInformacyjne("ZAPISANO");
                bohater.setZapis(this);
                break;

            default:
                renderer.renderujOknoInformacyjne(ParserZdarzen.odczytajzPliku("błąd"));
        }
        return this;
    }
}
