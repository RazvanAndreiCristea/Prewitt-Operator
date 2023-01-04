package packWork;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Consumator extends Executant
{
    private String locatie;
    private Conector buffer;
    public Consumator(Conector buffer)
    {
        super(false);
        this.buffer = buffer;
    }
    @Override
    public void starter()
    {
        for(int j = 0; j < 4; j++)
        {
            BufferedImage imagine = new BufferedImage(buffer.getLatura(), buffer.getInaltime(), BufferedImage.TYPE_INT_RGB);
            imagine = buffer.scrie(j);

            try
            {
                locatie = "segmentul_" + (j + 1) + ".bmp";
                ImageIO.write(imagine, "bmp", new File(locatie));
            }

            catch(IOException exceptie)
            {
                exceptie.printStackTrace();
            }

            System.out.println("Consumatorul " + (j + 1));

            try
            {
                sleep(1000);
            }

            catch (InterruptedException exceptie)
            {
                exceptie.printStackTrace(); // metoda arata de unde vine exceptia (de la ce linie si din ce fisier .java)
            }
        }
    }
}