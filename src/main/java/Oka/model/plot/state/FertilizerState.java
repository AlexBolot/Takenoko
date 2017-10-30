package Oka.model.plot.state;

import Oka.model.Enums;

public class FertilizerState extends NeutralState
{

    private static final Enums.State state = Enums.State.Fertilizer;

    @Override
    public Enums.State getState() {
        return state;
    }

    @Override
    public int getHowManyaddBambo ()
    {
        return 2;
    }
}
