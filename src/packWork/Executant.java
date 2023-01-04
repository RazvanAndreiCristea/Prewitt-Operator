package packWork;

import java.lang.reflect.Executable;
public abstract class Executant extends Thread
{
    protected boolean esteConsumator;
    protected Executant(boolean esteConsumator)
    {
        super();
        this.esteConsumator = esteConsumator;
    }
    public abstract void starter() throws InterruptedException;
    public void run()
    {
        try
        {
            this.starter();
            System.out.println("Thread-ul Executant s-a finalizat!");
        }

        catch (InterruptedException exceptie)
        {
            exceptie.printStackTrace(); // metoda arata de unde vine exceptia (de la ce linie si din ce fisier .java)
        }
    }
}