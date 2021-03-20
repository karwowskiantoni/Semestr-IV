package widoki;

import model.Postac;
import utils.ParserZdarzen;
import utils.Renderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Zdarzenie {
    private String opis;
    private String[] nastepne;

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

    public Zdarzenie(String opis, String[] nastepne) {
        this.opis = opis;
        this.nastepne = nastepne;
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
                    renderer.wyswietlOknoInformacyjne( "WYBRANO " + argument + System.lineSeparator() + ParserZdarzen.odczytajzPliku("dalej"));
                    return ParserZdarzen.stworzZdarzenie(this.nastepne[argument -1]);
                } else {
                    renderer.wyswietlOknoInformacyjne("Nie ma takiego wyboru");
                }
                break;

            case "pomoc":
                renderer.wyswietlOknoInformacyjne(ParserZdarzen.odczytajzPliku("pomoc"));
                break;

            case "bohater":
                renderer.wyswietlOknoInformacyjne(bohater.nazwaPostaci);
                break;

            case  "wyjście":
                System.exit(0);

            default:
                renderer.wyswietlOknoInformacyjne(ParserZdarzen.odczytajzPliku("błąd"));
        }
        return this;
    }

    public boolean zmienPunktyZycia(Postac bohater) {
        bohater.iloscPunktowZycia -= bohater.regeneracjaPunktowZycia;
        return bohater.iloscPunktowZycia >= 0;
    }
}
