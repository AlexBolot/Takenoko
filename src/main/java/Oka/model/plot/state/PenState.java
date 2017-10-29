package Oka.model.plot.state;

import Oka.model.Enums;

public class PenState extends NeutralState
{
    private static final Enums.State state = Enums.State.Pen;

    public static Enums.State getState() {
        return state;
    }

    @Override
    public boolean authorizationGetBamboo ()
    {
        return false;
    }
}
