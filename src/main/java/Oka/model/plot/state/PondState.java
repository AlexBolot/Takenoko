package Oka.model.plot.state;

import Oka.model.Enums;

public class PondState extends NeutralState
{
    private static final Enums.State state = Enums.State.Pond;

    public static Enums.State getState() {
        return state;
    }

    @Override
    public boolean getIsIrrigated ()
    {
        return true;
    }
}
