package packWork;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Conector
{
    private String fisierSursa;
    private boolean esteDisponibil;
    private int inaltime;
    private int latura;
    public BufferedImage imaginePartiala = null;
    public Conector(String fisierSursa)
    {
        this.fisierSursa = fisierSursa;
        this.esteDisponibil = false;
        this.inaltime = 1;
        this.latura = 1;
    }
    public int getInaltime()
    {
        return inaltime;
    }

    public int getLatura()
    {
        return latura;
    }
    public synchronized BufferedImage scrie(int sectiune)
    {
        BufferedImage segment = new BufferedImage(this.latura, this.inaltime, BufferedImage.TYPE_INT_RGB);

        while (esteDisponibil)
        {
            try
            {
                wait();
            }

            catch(InterruptedException exceptie)
            {
                exceptie.printStackTrace();
            }
        }

        esteDisponibil = true;
        notifyAll(); // trezeste celelalte thread uri care asteapta dupa thread ul curent

        try
        {
            imaginePartiala = ImageIO.read(new File(this.fisierSursa));
            segment = imaginePartiala.getSubimage(0, sectiune * this.inaltime, this.latura, this.inaltime);
        }

        catch (IOException exceptie)
        {
            exceptie.printStackTrace();
        }


        return segment;
    }
    public synchronized void citire()
    {
        try
        {
            imaginePartiala = ImageIO.read(new File(this.fisierSursa));
            latura = imaginePartiala.getWidth();
            inaltime = imaginePartiala.getHeight() / 4;
        }

        catch(IOException exceptie)
        {
            exceptie.printStackTrace();
        }

        while(!esteDisponibil) // atat timp cat citim din fisier nu scriem
        {
            try
            {
                wait();
            }

            catch(InterruptedException exceptie)
            {
                exceptie.printStackTrace();
            }
        }

        esteDisponibil = false;
        notifyAll(); // trezeste celelalte thread uri care asteapta dupa thread ul curent
    }
}