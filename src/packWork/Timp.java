package packWork;

public class Timp extends TimpScurs implements Print
{
    private int timpulTotal;

    public Timp()
    {
        super();
        this.timpulTotal = 0;
    }

    protected int getTimpulTotal()
    {
        return timpulTotal;
    }

    private void setTimpulTotal()
    {
        this.timpulTotal = (int) (System.currentTimeMillis() - getTimpTotalStart());
    }

    @Override
    public void afiseazaTimpScursEtapa(String etapa)
    {
        System.out.println("Timpul scurs in etapa de " + etapa + " este de: " + getTimpScurs() + " milisecunde." );
    }

    @Override
    public void afiseazaTimpTotal()
    {
        setTimpulTotal();
        System.out.println("Timpul total de executie a fost: " + getTimpulTotal() + " milisecunde");
    }
}
