import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameBook {

    static String tekstPomocy =
            "komendy dostępne w grze: " + System.lineSeparator() +
            "pomoc - wyświetla tę pomoc" + System.lineSeparator() +
            "wybieram x - wybiera x w ciągu fabularnym" + System.lineSeparator() +
            "bohater - wyświetla informacje o bohaterze" + System.lineSeparator() +
            "NACIŚNIJ ENTER ABY POWRÓCIĆ DO GRY";

    static String tekstBledu =
            "NIEPOPRAWNE POLECENIE" + System.lineSeparator() +
            "(polecenie 'pomoc' wyświetla listę dostępnych komend)" + System.lineSeparator() +
            "NACIŚNIJ ENTER ABY POWRÓCIĆ DO GRY";

    public static void main(String args[]) throws IOException {
        gra();
//        wyszukiwarkaZdarzen();
    }

    private static void wyszukiwarkaZdarzen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print("wprowadź numer zdarzenia: ");
                String input = br.readLine();
                Zdarzenie aktualneZdarzenie = ParserZdarzen.stworzZdarzenie(input);
                Renderer.RenderujTekst(aktualneZdarzenie.Opis, 30, 100, 90);
            } catch (IOException exception) {
                System.out.println("Nie ma takiego zdarzenia, spróbuj ponownie");
            }
        }
    }

    private static void gra() throws IOException{
        Zdarzenie aktualneZdarzenie = ParserZdarzen.stworzZdarzenie("0");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        while (true) {
            Renderer.RenderujTekst(aktualneZdarzenie.Opis, 30, 100, 90);
            System.out.print("WPROWADŹ POLECENIE: ");
            String input = br.readLine();


            String[] polecenie = input.split(" ");

            // wybór nowego poziomu
            if (polecenie[0].equals("wybieram")) {
                if(polecenie[1].equals("1")) {
                    aktualneZdarzenie = ParserZdarzen.stworzZdarzenie(Integer.toString(aktualneZdarzenie.nastepneID[0]));
                    Renderer.RenderujTekst("ZARAZ PRZEJDZIESZ DALEJ, WCIŚNIJ ENTER ABY KONTYNUOWAĆ", 30, 100, 90);
                    br.readLine();
                }
                if(polecenie[1].equals("2")) {
                    aktualneZdarzenie = ParserZdarzen.stworzZdarzenie(Integer.toString(aktualneZdarzenie.nastepneID[1]));
                    Renderer.RenderujTekst("ZARAZ PRZEJDZIESZ DALEJ, WCIŚNIJ ENTER ABY KONTYNUOWAĆ", 30, 100, 90);
                    br.readLine();
                }
            }
            // wyświetlenie pomocy
            else if (polecenie[0].equals("pomoc")) {
                Renderer.RenderujTekst(tekstPomocy, 30, 100, 90);
                br.readLine();
            }

            // błędne polecenie
            else {
                Renderer.RenderujTekst(aktualneZdarzenie.Opis, 30, 100, 90);
                Renderer.RenderujTekst(tekstBledu, 30, 100 ,90);
                br.readLine();
            }

        }
    }
}

