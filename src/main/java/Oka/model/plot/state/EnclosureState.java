package Oka.model.plot.state;

import Oka.model.Enums;

public class EnclosureState extends NeutralState
{
    private static final Enums.State state = Enums.State.Enclosure;

    @Override
    public Enums.State getState() {
        return state;
    }

    @Override
    /**
     * @return false because the enclosurestate doesn't allow the panda to eat bamboo.
     */
    public boolean authorizationGetBamboo ()
    {
        return false;
    }

    public String toString(){
        return state.toString();
    }
}
