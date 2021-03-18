package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Renderer {
    public static void RenderujTekstCiagly(String tekst) {
        System.out.println("*****==========================================================*****");
        System.out.println("********************************************************************");
        System.out.println("*****==========================================================*****");

        List<String> wiersze = tekstWysrodkowany(tekst, 38);

        renderujMarginesPionowy(wiersze.size(),20, 58);

        for(String wiersz : wiersze){
            renderujwWierszu(wiersz, 58);
        }

        renderujMarginesPionowy(wiersze.size(), 20, 58);

        System.out.println("*****==========================================================*****");
        System.out.println("********************************************************************");
        System.out.println("*****==========================================================*****");
    }


    public static void RenderujTekstZeZnakamiKoncaLinii(String tekst) {
        System.out.println("*****==========================================================*****");
        System.out.println("********************************************************************");
        System.out.println("*****==========================================================*****");

        String[] wiersze = tekst.split(System.lineSeparator());

        renderujMarginesPionowy(wiersze.length, 20, 58);

        for(String wiersz : wiersze){
            renderujwWierszu(wiersz, 58);
        }

        renderujMarginesPionowy(wiersze.length, 20, 58);

        System.out.println("*****==========================================================*****");
        System.out.println("********************************************************************");
        System.out.println("*****==========================================================*****");
    }


    private static void renderujwWierszu(String tekst, int szerokoscWiersza){
        String margines = powtorz(" ", (szerokoscWiersza - tekst.length())/2);

        System.out.print("||*||");
        System.out.print(margines + tekst + margines);
        System.out.println("||*||");
    }

    private static void renderujMarginesPionowy(int liczbaPelnychWierszy, int liczbaWszystkichWierszy, int szerokoscWiersza){
        for(int i = 0; i < (liczbaWszystkichWierszy - liczbaPelnychWierszy) / 2; i++) {
            renderujwWierszu("", szerokoscWiersza);
        }
    }

    private static String powtorz(String tekst, int liczba_powtorzen) {
        return IntStream.range(0, liczba_powtorzen).mapToObj(i -> tekst).collect(Collectors.joining(""));
    }

    private static List<String> tekstWysrodkowany(String tekst, int liczbaZnakowwWierszu) {
        List<String> wiersze = new ArrayList<>();
        String kopia_tekstu = new String(tekst);
        for(int i = 0; i < tekst.length()/liczbaZnakowwWierszu; i++) {
                wiersze.add(kopia_tekstu.substring(0, liczbaZnakowwWierszu));
                kopia_tekstu = kopia_tekstu.substring(liczbaZnakowwWierszu);
        }
        wiersze.add(kopia_tekstu + " ");

        for(int i = 0; i < wiersze.size(); i++) {
        }
        return wiersze;
    }

}
