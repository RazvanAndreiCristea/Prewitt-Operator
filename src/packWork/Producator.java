package packWork;

public class Producator extends Executant
{
    private Conector buffer;
    public Producator(Conector buffer)
    {
        super(false);
        this.buffer = buffer;
    }
    @Override
    public void starter()
    {
        for(int j = 0; j < 4; j++)
        {
            buffer.citire();
            System.out.println("Producatorul " + (j + 1));
        }
    }
}