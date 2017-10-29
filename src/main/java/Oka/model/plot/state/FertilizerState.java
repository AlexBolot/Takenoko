package Oka.model.plot.state;

import Oka.model.Enums;

public class FertilizerState extends NeutralState
{

    private static final Enums.State state = Enums.State.Fertilizer;

    public static Enums.State getState() {
        return state;
    }

    @Override
    public int getHowManyaddBambo ()
    {
        return 2;
    }
}
