package packTest;

import packWork.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("In linia de comanda nu se regasesc argumentele necesare!");
            System.exit(0);
        }

        Scanner scaner = new Scanner(System.in);

        System.out.println("===================================== Salut bine ai venit! =====================================");

        String input = alegeOptiune(scaner); // citim de la tastatura

        while(!(input.equals("1") || input.equals("2")))
        {
            clearConsole();
            System.out.println("Optiunea introdusa de tine este invalida!");
            input = alegeOptiune(scaner);
        }

        int optiune = Integer.parseInt(input) - 1;

        Timp timp = new Timp();

        ProcesatorImagine.deschidereImagine(args[optiune]);

        timp.afiseazaTimpScursEtapa("de deschidere imagine");

        Conector buffer = new Conector(args[optiune]);

        buffer.imaginePartiala = new BufferedImage(ProcesatorImagine.getImagineOriginala().getWidth(), ProcesatorImagine.getImagineOriginala().getHeight(), BufferedImage.TYPE_INT_RGB);

        BufferedImage sectiune = null;

        Producator threadProducator = new Producator(buffer);

        Consumator threadConsumator = new Consumator(buffer);

        threadProducator.start();
        threadConsumator.start();

        //aici sincronizam thread-urile
        try
        {
            threadConsumator.join();
        }

        catch (InterruptedException execeptie)
        {
            execeptie.printStackTrace();
        }

        //se creaza cele 4 sectiuni ale imaginii sursa

        timp.reseteazaTimpScurs();

        for (int nrSectiune = 0; nrSectiune < 4; nrSectiune++)
        {
            try
            {
                sectiune = ImageIO.read(new File("segmentul_" + (nrSectiune + 1) + ".bmp"));
                for (int i = 0; i < sectiune.getHeight(); i++)
                {
                    for (int j = 0; j < sectiune.getWidth(); j++)
                    {
                        buffer.imaginePartiala.getRaster().setDataElements(j, nrSectiune * sectiune.getHeight(), buffer.imaginePartiala.getColorModel().getDataElements(sectiune.getRGB(j, i), null));
                    }
                }
            }

            catch (IOException exceptie)
            {
                exceptie.printStackTrace();
            }

            timp.afiseazaTimpScursEtapa("creare a sectiunii " + (nrSectiune + 1));
        }

        // aici aplicam algoritmul de prewitt pe segmente
        for (int j = 0; j < 4; j++)
        {
            String locatie = "segmentul_" + (j + 1) + ".bmp";
            ProcesatorImagine.deschidereImagine(locatie);
            ProcesatorImagine.aplicaOperatorulPrewitt(ProcesatorImagine.getImagineOriginala(), ProcesatorImagine.getImagineRezultata());
            locatie = "segmentul_" + (j + 1) + "_rezultat" + ".bmp";
            ProcesatorImagine.salvareImagine(locatie, ProcesatorImagine.getImagineRezultata());
            timp.afiseazaTimpScursEtapa("aplicare a operatorului Prewitt pe segmentul " + (j + 1));
        }

        // aici vom aplica algoritmul pentru intreaga imagine pentru a avea produsul final dorit

        ProcesatorImagine.deschidereImagine(args[optiune]);
        ProcesatorImagine.aplicaOperatorulPrewitt(ProcesatorImagine.getImagineOriginala(), ProcesatorImagine.getImagineRezultata());
        ProcesatorImagine.salvareImagine("..\\Prewitt-Operator\\src\\packTest\\rezultat.bmp", ProcesatorImagine.getImagineRezultata());
        timp.afiseazaTimpTotal();
    }
    private static String alegeOptiune(Scanner scaner)
    {
        System.out.println("Optiunile disponibile pentru aceasta aplicatie sunt: ");
        System.out.println("Tasta 1 -> Pentru a aplica algoritmul pozei unui caine");
        System.out.println("Tasta 2 -> Pentru a aplica algoritmul pozei unor papagali");
        System.out.println("Introdu cifra corespunzatoare imaginii careia vrei sa ii aplici operatorul Prewitt: ");

       return scaner.nextLine();
    }
    private static void clearConsole()
    {
        for (int j = 0; j < 100; j++)
            System.out.println();
    }
}