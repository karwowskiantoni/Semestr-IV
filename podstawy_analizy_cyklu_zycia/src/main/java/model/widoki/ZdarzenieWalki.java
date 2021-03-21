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
        Parser parser = new Parser();
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

            case "wyposaz":
                String argumentWyposazania = wejscie[1];
                Wzmocnienie przedmiotDoZalozenia = bohater.getPrzedmioty().stream().filter(wzmocnienie -> wzmocnienie.getNazwa().equals(argumentWyposazania)).findFirst().orElse(null);
                if(zalozonePrzedmioty.size() > 2) {
                    renderer.renderujOknoInformacyjne("W krzyżu głośne łupnięcie bohaterowi naszemu uświadomiło, że chyba mu już ich wystarczy");
                }
                else if(przedmiotDoZalozenia != null) {
                    przedmiotDoZalozenia.wzmocnij(bohater);
                    bohater.usunPrzedmiot(przedmiotDoZalozenia);
                    zalozonePrzedmioty.add(przedmiotDoZalozenia);
                } else {
                    renderer.renderujOknoInformacyjne("Bohater fantazji swej nadużywając przywdziać chciał wytwór swej imaginacji");
                }
                break;


            case "zdejmij":
                String argumentZdejmowania = wejscie[1];
                Wzmocnienie przedmiotDoZdjecia = zalozonePrzedmioty.stream().filter(wzmocnienie -> wzmocnienie.getNazwa().equals(argumentZdejmowania)).findFirst().orElse(null);
                if(przedmiotDoZdjecia != null){
                    przedmiotDoZdjecia.anulujWzmocnienie(bohater);
                    zalozonePrzedmioty.remove(przedmiotDoZdjecia);
                    bohater.zdobadzPrzedmiot(przedmiotDoZdjecia);
                } else {
                    renderer.renderujOknoInformacyjne("Bohaterowi na wzrok padło chcąc zdjąć rzecz, której przywdziać wpierw nie raczył");
                }
                break;

            case "walcz":
                renderer.renderujOknoInformacyjne(bohater.walka(przeciwnik));
                if(bohater.getIloscPunktowZycia() > 0) {
                    bohater.getPrzedmioty().addAll(przeciwnik.getPrzedmioty());
                    for (Wzmocnienie przedmiot: zalozonePrzedmioty) {
                        przedmiot.anulujWzmocnienie(bohater);
                    }
                    return parser.stworzZdarzenie(getNastepne()[1],false);
                } else {
                    return parser.stworzZdarzenie(getNastepne()[0], false);
                }



            case "pomoc":
                renderer.renderujOknoInformacyjne(parser.odczytajzPliku("pomoc_walki", false));
                break;

            case "bohater":
                renderer.renderujOknoInformacyjne(bohater.wypiszInformacjeOBohaterze("KARTA BOHATERA"));
                break;

            case  "wyjdz":
                System.exit(0);

            default:
                renderer.renderujOknoInformacyjne(parser.odczytajzPliku("błąd", false));
        }
        return this;

    }
}
