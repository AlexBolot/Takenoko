package Oka.ai;

import Oka.entities.Entity;
import Oka.model.Enums;

public abstract class AI extends Playable
{
    //region==========CONSTRUCTORS=========
    public AI (String name)
    {
        super(name);
    }
    //endregion

    //region==========ABSTRACT METHODS=====
    @Override
    public abstract void play ();

    protected abstract boolean moveGardener ();

    protected abstract boolean movePanda ();

    protected abstract boolean placePlot ();

    protected abstract boolean drawChannel();

    protected abstract boolean placeChannel();

    protected abstract boolean moveEntity (Entity entity, int bambooSize, Enums.Color color);

    //endregion
}

