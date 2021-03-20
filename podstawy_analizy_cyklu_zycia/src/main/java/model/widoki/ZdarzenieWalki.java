package model.widoki;

import model.Postac;
import model.Wzmocnienie;
import utils.Parser;
import utils.Renderer;
import utils.ZapisOdczytException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ZdarzenieWalki extends Zdarzenie{
    private Postac przeciwnik;
    List<Wzmocnienie> zalozonePrzedmioty = new ArrayList<>();


    public ZdarzenieWalki() {
        super();
    }

    public Postac getPrzeciwnik() {
        return przeciwnik;
    }

    public void setPrzeciwnik(Postac przeciwnik) {
        this.przeciwnik = przeciwnik;
    }

    //bohater to bohater
    //przeciwnik to przeciwnik
    @Override
    public Zdarzenie wykonajZdarzenie(Renderer renderer, Postac bohater) throws IOException, ZapisOdczytException {

        String tekst = przeciwnik.wypiszInformacjeOStatystykach("PRZECIWNIK NA TYM POZIOMIE") +
                bohater.wypiszInformacjeOStatystykach("TWOJE AKTUALNE STATYSTYKI") +
                System.lineSeparator().repeat(4) +
                bohater.wypiszInformacjeOPrzedmiotach() +
                System.lineSeparator() +
                renderer.wypiszWzmocnienia("Założone przedmioty", zalozonePrzedmioty, "brak");

        renderer.renderujTekst(tekst);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Wybierz przedmioty do walki: ");
        String input = br.readLine();

        String[] wejscie = input.split(" ", 2);

        String polecenie = wejscie[0];

        switch (polecenie) {

            case "wyposaż":
                String argument = wejscie[1];
                Wzmocnienie wybrane = bohater.getPrzedmioty().stream().filter(wzmocnienie -> wzmocnienie.getNazwa().equals(argument)).findFirst().orElse(null);
                if(zalozonePrzedmioty.size() > 2) {
                    renderer.renderujOknoInformacyjne("Bohater ugiąłwszy się pod ilością przedmiotów stwierdza iż nie jest w stanie udźwignąć już ani grama więcej");
                }
                else if(wybrane != null) {
                    bohater.zalozPrzedmiot(wybrane.getNazwa());
                    bohater.getPrzedmioty().remove(wybrane);
                    zalozonePrzedmioty.add(wybrane);
                } else {
                    renderer.renderujOknoInformacyjne("Bohater fantazji swej nadużywając przywdziać chciał wytwór swej imaginacji");
                }
                break;


            case "zdejmij":
                String argumentZdejmowania = wejscie[1];
                Wzmocnienie wybranyDoZdjecia = zalozonePrzedmioty.stream().filter(wzmocnienie -> wzmocnienie.getNazwa().equals(argumentZdejmowania)).findFirst().orElse(null);
                if(wybranyDoZdjecia != null){
                    wybranyDoZdjecia.zdejmijPrzedmiot(bohater);
                    zalozonePrzedmioty.remove(wybranyDoZdjecia);
                    bohater.getPrzedmioty().add(wybranyDoZdjecia);
                } else {
                    renderer.renderujOknoInformacyjne("Bohaterowi na wzrok padło chcąc zdjąć rzecz, której przywdziać wpierw nie raczył");
                }
                break;

            case "walcz":
                renderer.renderujOknoInformacyjne(bohater.walka(przeciwnik));
                if(bohater.getIloscPunktowZycia() > 0) {
                    bohater.getPrzedmioty().addAll(przeciwnik.getPrzedmioty());
                    for (Wzmocnienie przedmiot: zalozonePrzedmioty) {
                        przedmiot.zdejmijPrzedmiot(bohater);
                    }
                    return Parser.stworzZdarzenie(getNastepne()[1]);
                } else {
                    return Parser.stworzZdarzenie(getNastepne()[0]);
                }



            case "pomoc":
                renderer.renderujOknoInformacyjne(Parser.odczytajzPliku("pomoc_walki"));
                break;

            case "bohater":
                renderer.renderujOknoInformacyjne(bohater.wypiszInformacjeOBohaterze("KARTA BOHATERA"));
                break;

            case  "wyjście":
                System.exit(0);

            default:
                renderer.renderujOknoInformacyjne(Parser.odczytajzPliku("błąd"));
        }
        return this;

    }
}
