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

public class ZdarzenieWzmocnienia extends Zdarzenie{

    List<Wzmocnienie> cechy = new ArrayList<>();
    List<Wzmocnienie> przedmioty = new ArrayList<>();
    boolean serafinNiewiadomskiego = true;

    public ZdarzenieWzmocnienia() {
        super();
    }

    @Override
    public Zdarzenie wykonajZdarzenie(Renderer renderer, Postac bohater) throws IOException, ZapisOdczytException {
        Parser parser = new Parser();
        renderer.renderujTekst(getOpis());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        if(serafinNiewiadomskiego) {
            for (Wzmocnienie cecha : cechy)
                bohater.zdobadzCeche(cecha);
            for (Wzmocnienie przedmiot : przedmioty)
                bohater.zdobadzPrzedmiot(przedmiot);
            serafinNiewiadomskiego = false;
        }
        System.out.print("WPROWADŹ POLECENIE: ");
        String input = br.readLine();
        String[] wejscie = input.split(" ");

        String polecenie = wejscie[0];

        switch (polecenie) {

            case "opcja":
                int argument = Integer.parseInt(wejscie[1]);
                if(getNastepne().length >= argument) {
                    return parser.stworzZdarzenie(this.getNastepne()[argument -1], false);
                } else {
                    renderer.renderujOknoInformacyjne("Nie ma takiego wyboru");
                }
                break;

            case "pomoc":
                renderer.renderujOknoInformacyjne(parser.odczytajzPliku("pomoc", false));
                break;

            case "bohater":
                renderer.renderujOknoInformacyjne(bohater.wypiszInformacjeOBohaterze("KARTA BOHATERA"));
                break;

            case  "wyjdz":
                System.exit(0);

            case "wczytaj":
                renderer.renderujOknoInformacyjne("WCZYTANO");
                throw new ZapisOdczytException("wczytaj");

            case "zapisz":
                renderer.renderujOknoInformacyjne("ZAPISANO");
                throw new ZapisOdczytException("zapisz");

            default:
                renderer.renderujOknoInformacyjne(parser.odczytajzPliku("błąd", false));
        }
        return this;
    }
}
