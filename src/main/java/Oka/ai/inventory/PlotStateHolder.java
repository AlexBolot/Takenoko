package Oka.ai.inventory;

import Oka.model.Enums;
import Oka.model.plot.state.NeutralState;

import java.util.ArrayList;



public class PlotStateHolder extends ArrayList<NeutralState>
{
    public int countByState (Enums.State state)
    {
        return (int) this.stream().filter(item -> item.getState().equals(state)).count();
    }
}
