package utils;

import model.Wzmocnienie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Renderer {
    int wysokoscOkna;
    int szerokoscOkna;
    int szerokoscTekstu;

    public Renderer(int wysokoscOkna, int szerokoscOkna, int szerokoscTekstu) {
        this.wysokoscOkna = wysokoscOkna;
        this.szerokoscOkna = szerokoscOkna;
        this.szerokoscTekstu = szerokoscTekstu;
    }

    public String wypiszWzmocnienia(String tytul, List<Wzmocnienie> wzmocnienia, String tekstJesliPuste) {
        StringBuilder lista = new StringBuilder();
        lista.append("-------------").append(tytul).append("-------------").append(System.lineSeparator());

        if(wzmocnienia.size() == 0){
            lista.append(tekstJesliPuste);
            return lista.toString();
        }
        for(Wzmocnienie przedmiot: wzmocnienia) {
            lista.append(przedmiot.opis()).append(System.lineSeparator());
        }
        return lista.toString();

    }

    public void renderujTekst(String tekst) {

        wyczyscKonsole();
        List<String> wiersze_bez_marginesu = Arrays.asList(tekst.split(System.lineSeparator()));

        for (int i = 0; i < wiersze_bez_marginesu.size(); i++) {
            if (wiersze_bez_marginesu.get(i).length() > szerokoscTekstu) {
                List<String> lewaStrona = wiersze_bez_marginesu.subList(0, i);
                List<String> prawaStrona = wiersze_bez_marginesu.subList(i + 1, wiersze_bez_marginesu.size());
                List<String> srodeczek = dodajEntery(wiersze_bez_marginesu.get(i), szerokoscTekstu);
                wiersze_bez_marginesu = new ArrayList<>(lewaStrona);
                wiersze_bez_marginesu.addAll(srodeczek);
                wiersze_bez_marginesu.addAll(prawaStrona);
                i = 0;
            }
        }

        renderujWierszZMarginesem(powtorzTekst("* ", szerokoscOkna / 2), szerokoscOkna); // górna krawędź

        renderujMarginesPionowy(wiersze_bez_marginesu.size(), wysokoscOkna, szerokoscOkna); // górny margines

        for (String wiersz : wiersze_bez_marginesu) { // wiersze z tekstem + marginesy
            renderujWierszZMarginesem(wiersz, szerokoscOkna);
        }

        renderujMarginesPionowy(wiersze_bez_marginesu.size(), wysokoscOkna, szerokoscOkna); // dolny margines

        renderujWierszZMarginesem(powtorzTekst("* ", szerokoscOkna / 2), szerokoscOkna); //
    }

    public void renderujOknoInformacyjne(String informacja) {
        try {
            informacja += "." + System.lineSeparator() + "." + System.lineSeparator() + "WCIŚNIJ ENTER ABY KONTYNUOWAĆ";
            renderujTekst(informacja);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();

        } catch (IOException e) {
            System.out.println("BŁĄD, NIE UDAŁO SIĘ WCZYTAĆ TEKSTU");
            System.exit(1);
        }

    }

    private static void renderujWierszZMarginesem(String tekst, int szerokosc) {
        String margines = powtorzTekst(" ", (szerokosc - tekst.length()) / 2);

        System.out.print("* ");
        System.out.print(margines + tekst + margines);
        if (tekst.length() % 2 == 1) {
            System.out.print(" ");
        }
        System.out.println("*");
    }

    private static void renderujMarginesPionowy(int wysokoscZajetaPrzezTekst, int wysokosc, int szerokosc) {
        for (int i = 0; i < (wysokosc - wysokoscZajetaPrzezTekst) / 2; i++) {
            renderujWierszZMarginesem("", szerokosc);
        }
    }

    private static String powtorzTekst(String tekst, int liczba_powtorzen) {
        return IntStream.range(0, liczba_powtorzen).mapToObj(i -> tekst).collect(Collectors.joining(""));
    }

    /**
   Prawda, dodawanie dokumentacji
     bardzo ważne przy wydawaniu oprogramowania,
     czyli jak sobie skomentujesz tak sobie zakomitujesz,
     prawda, czyli jak, tak.
     ; )

     */

    private static List<String> dodajEntery(String tekst, int szerokoscTekstu) {
        List<String> wiersze = new ArrayList<>();
        String kopia_tekstu = tekst;
        for (int i = 0; i < tekst.length() / szerokoscTekstu; i++) {
            String poczatek = kopia_tekstu.substring(0, szerokoscTekstu);
            while (poczatek.charAt(poczatek.length() - 1) != ' ') {
                poczatek = poczatek.substring(0, poczatek.length() - 1);
            }
            wiersze.add(poczatek);
            kopia_tekstu = kopia_tekstu.substring(poczatek.length());
        }
        wiersze.add(kopia_tekstu + " ");
        return wiersze;
    }

    private static void wyczyscKonsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
