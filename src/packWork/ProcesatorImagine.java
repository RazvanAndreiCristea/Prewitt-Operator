package packWork;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProcesatorImagine
{
    private static BufferedImage imagineOriginala = null;
    private static BufferedImage imagineRezultata = null;

    public static void deschidereImagine(String imagineSursa)
    {
        try
        {
            imagineOriginala = ImageIO.read(new File(imagineSursa));
            imagineRezultata = new BufferedImage(imagineOriginala.getWidth(), imagineOriginala.getHeight(), BufferedImage.TYPE_INT_RGB);
        }

        catch (Exception exceptie)
        {
            exceptie.printStackTrace();
        }
    }
    public static void salvareImagine(String locatieSalvata, BufferedImage imagineRezultata)
    {
        try
        {
            ImageIO.write(imagineRezultata, "bmp", new File(locatieSalvata));
        }

        catch (Exception exceptie)
        {
            exceptie.printStackTrace();
        }
    }
    public static BufferedImage getImagineOriginala()
    {
        return imagineOriginala;
    }

    public static BufferedImage getImagineRezultata()
    {
        return imagineRezultata;
    }

    private static BufferedImage conversiaGray(BufferedImage imagine) //aici facem Grayscale
    {
        BufferedImage rezultat = new BufferedImage(imagine.getWidth(), imagine.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = rezultat.getGraphics();
        g.drawImage(imagine, 0,0,null);
        g.dispose();

        return rezultat;
    }
    public static void aplicaOperatorulPrewitt(BufferedImage imagineOriginala, BufferedImage imagineaRezultata)
    {
        BufferedImage imagine = conversiaGray(imagineOriginala);

        int[] pixeliImagine = new int[imagine.getWidth() * imagine.getHeight()];
        imagine.getRGB(0, 0, imagine.getWidth(), imagine.getHeight(), pixeliImagine, 0, imagine.getWidth());

        for (int y = 1; y < imagine.getHeight() - 1; y++)
        {
            for (int x = 1; x < imagine.getWidth() - 1; x++)
            {
                int gx = pixeliImagine[(y - 1) * imagine.getWidth() + (x - 1)] + pixeliImagine[(y - 1) * imagine.getWidth() + (x)] + pixeliImagine[(y - 1) * imagine.getWidth() + (x + 1)]
                        - pixeliImagine[(y + 1) * imagine.getWidth() + (x - 1)] - pixeliImagine[(y + 1) * imagine.getWidth() + (x)] - pixeliImagine[(y + 1) * imagine.getWidth() + (x + 1)];

                int gy = pixeliImagine[(y - 1) * imagine.getWidth() + (x - 1)] + pixeliImagine[(y) * imagine.getWidth() + (x - 1)] + pixeliImagine[(y + 1) * imagine.getWidth() + (x - 1)]
                        - pixeliImagine[(y - 1) * imagine.getWidth() + (x + 1)] - pixeliImagine[(y) * imagine.getWidth() + (x + 1)] - pixeliImagine[(y + 1) * imagine.getWidth() + (x + 1)];

                int sum = Math.abs(gx) + Math.abs(gy);

                imagineaRezultata.setRGB(x, y, sum);
            }
        }
    }
}

/*Mai intai, este declarat un vector numit "pixeliImagine" care memoreaza valorile RGB ale fiecarui pixel din imagine.
Apoi, se parcurge imaginea, pixel cu pixel, prin intermediul unui "for loop" imbricat. Pentru fiecare pixel, se calculeaza
valorile gradientului in directiile x si y (gx si gy) folosind valorile RGB ale pixelilor din jurul sau.
Apoi, se calculeaza suma absoluta a acestor valori de gradient si se seteaza aceasta valoare ca valoare RGB pentru pixelul curent
in imaginea rezultat. Aceasta imagine rezultat ar trebui sa evidențieze marginile din imaginea originala.*/