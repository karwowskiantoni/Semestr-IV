package utils;

import model.Postac;
import widoki.WidokZdarzenia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Interpreter {

    public static void czekajNaUzytkownika() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
        } catch (IOException e) {
            System.out.println("JAKIŚ KURWA BŁĄD NIEWIADOMO SKĄD");
        }
    }

    public static WidokZdarzenia wykonajPolecenie(WidokZdarzenia aktualneZdarzenie, Renderer skurwysynek, Postac bohater) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("WPROWADŹ POLECENIE: ");
        String input = br.readLine();

        String[] wejscie = input.split(" ");

        String polecenie = wejscie[0];

        switch (polecenie) {

            case "wybieram":
                int argument = Integer.parseInt(wejscie[1]);
                if(argument == 1 || argument == 2) {
                    skurwysynek.RenderujTekst(tekstKontynuacji(polecenie));
                    br.readLine();
                    return ParserZdarzen.stworzZdarzenie(Integer.toString(aktualneZdarzenie.nastepneID[argument -1]));
                } else {
                    skurwysynek.RenderujTekst("NIE MA TAKIEGO WYBORU");
                    br.readLine();
                }
                break;

            case "pomoc":
                skurwysynek.RenderujTekst(tekstPomocy());
                br.readLine();
                break;

            case "bohater":
                skurwysynek.RenderujTekst(bohater.nazwaPostaci + System.lineSeparator() + "WCIŚNIJ ENTER ABY KONTYNUOWAĆ");
                br.readLine();
                break;

            case  "wyjście":
                System.exit(0);

            default:
                skurwysynek.RenderujTekst(tekstBledu());
                br.readLine();
        }
        return aktualneZdarzenie;
    }

    private static String tekstKontynuacji(String S) {
        return "Wybrano " + S + System.lineSeparator() +"ZARAZ PRZEJDZIESZ DALEJ, WCIŚNIJ ENTER ABY KONTYNUOWAĆ";
    }

    private static String tekstPomocy() {
        return  "komendy dostępne w grze: " + System.lineSeparator() +
                "pomoc - wyświetla tę pomoc" + System.lineSeparator() +
                "wybieram x - wybiera x w ciągu fabularnym" + System.lineSeparator() +
                "bohater - wyświetla informacje o bohaterze" + System.lineSeparator() +
                "wyjście - wychodzi z gry" + System.lineSeparator() +
                "NACIŚNIJ ENTER ABY POWRÓCIĆ DO GRY";
    }

    private static String tekstBledu() {
        return  "NIEPOPRAWNE POLECENIE" + System.lineSeparator() +
                "(polecenie 'pomoc' wyświetla listę dostępnych komend)" + System.lineSeparator() +
                "NACIŚNIJ ENTER ABY POWRÓCIĆ DO GRY";
    }
}
