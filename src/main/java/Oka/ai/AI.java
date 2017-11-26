package Oka.ai;

public abstract class AI extends Playable
{
    //region==========CONSTRUCTORS=========
    public AI (String name)
    {
        super(name);
    }

    //endregion
    public AI () {}

    //region==========ABSTRACT METHODS=====
    @Override
    public abstract void play ();

    protected abstract boolean movePanda ();

    protected abstract void placeBambooOnPlot ();

    public abstract boolean choosePlotState ();

    public abstract void printObjectives();

    //endregion
}

