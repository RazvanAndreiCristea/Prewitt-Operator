package packWork;

public class TimpScurs extends TimpStart
{
    private long timpScurs;

    public TimpScurs()
    {
        super();
        this.timpScurs = 0;
    }

    protected long getTimpScurs()
    {
        setTimpScurs();
        return timpScurs;
    }

    private void setTimpScurs()
    {
        timpScurs = System.currentTimeMillis() - getTimpStartEtapa();
        resetTimpStartEtapa();
    }
}