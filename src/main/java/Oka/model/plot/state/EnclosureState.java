package Oka.model.plot.state;

import Oka.model.Enums;

public class EnclosureState extends NeutralState
{
    private static final Enums.State state = Enums.State.Enclosure;

    public static Enums.State getState() {
        return state;
    }

    @Override
    public boolean authorizationGetBamboo ()
    {
        return false;
    }
}
