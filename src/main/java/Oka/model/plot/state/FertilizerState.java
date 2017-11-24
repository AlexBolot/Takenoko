package Oka.model.plot.state;

import Oka.model.Enums;

public class FertilizerState extends NeutralState
{

    private static final Enums.State state = Enums.State.Fertilizer;

    @Override
    public Enums.State getState() {
        return state;
    }

    /**
     * @return 2 because the gardener can grow two bamboos on the plot instead of one on a fertilizerstate.
     */
    @Override
    public int getHowManyaddBambo ()
    {
        return 2;
    }

    public String toString(){
        return state.toString();
    }
}
