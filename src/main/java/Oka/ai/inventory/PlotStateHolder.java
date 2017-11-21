package Oka.ai.inventory;

import Oka.model.Enums;
import Oka.model.plot.state.NeutralState;

import java.util.ArrayList;

/*..................................................................................................
 . Copyright (c)
 .
 . The PlotStateHolder	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 30/10/17 14:05
 .................................................................................................*/

public class PlotStateHolder extends ArrayList<NeutralState>
{
    public int countByState (Enums.State state)
    {
        return (int) this.stream().filter(item -> item.getState().equals(state)).count();
    }
}
