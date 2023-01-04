package packWork;

public class TimpStart
{
    private long timpStartEtapa;
    private long timpTotalStart;

    public TimpStart()
    {
        this.timpStartEtapa = System.currentTimeMillis();
        this.timpTotalStart = System.currentTimeMillis();
    }

    protected long getTimpTotalStart()
    {
        return timpTotalStart;
    }

    protected long getTimpStartEtapa()
    {
        return timpStartEtapa;
    }

    protected void resetTimpStartEtapa()
    {
        this.timpStartEtapa = System.currentTimeMillis();
    }
}