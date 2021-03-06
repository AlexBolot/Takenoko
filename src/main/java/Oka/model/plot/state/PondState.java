package Oka.model.plot.state;

import Oka.model.Enums;

public class PondState extends NeutralState
{
    private static final Enums.State state = Enums.State.Pond;

    @Override
    public Enums.State getState() {
        return state;
    }

    @Override
    /**
     * @return true because the pondstate are always irrigated.
     */
    public boolean getIsIrrigated ()
    {
        return true;
    }

    public String toString(){
        return state.toString();
    }
}
