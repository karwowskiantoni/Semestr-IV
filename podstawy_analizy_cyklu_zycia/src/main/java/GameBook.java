import utils.Renderer;

import java.io.Console;
import java.io.IOException;

public class GameBook {
    public static void main(String args[]) throws IOException {

        Wzmocnienie sila_5 = new Wzmocnienie("cecha",
                0,
                0,
                5,
                0,
                0,
                0,
                0);

        Postac bohater = new Postac(80,
                5,
                5,
                5,
                5,
                5,
                5,
                "jestes zajebisty",
                new Wzmocnienie[]{sila_5});

        Renderer.RenderujTekstZeZnakamiKoncaLinii(bohater.wypiszInformacjeOPostaci());
    }
}



//        Renderer.RenderujTekstCiagly("polały się łzy me czyste rzęsiste, na me dzieciństwo sielskie anielskie, na moją młodość durną i chmurną, na mój wiek męski, wiek klęski, polały się łzy me czyste rzęsiste");
//        String input = System.console().readLine();
//
//        Runtime.getRuntime().exec("clear");
//        Renderer.RenderujTekstZeZnakamiKoncaLinii("gdybym był" + System.lineSeparator() +
//                                                    "marynarzem" + System.lineSeparator() +
//                                                    "chciałbym mieć" + System.lineSeparator() +
//                                                    " tatuaże");
//    }