package Oka.model.plot.state;
import Oka.model.Enums;
import Oka.model.Enums.State;

public class NeutralState {
    private static final State state = State.Neutral;

    public State getState() {
        return state;
    }

    public int getHowManyaddBambo(){
        return 1;
    }

    public boolean authorizationGetBamboo(){
        return true;
    }

    public boolean getIsIrrigated(){
        return false;
    }

    public String toString(){
        return this.state.toString();
    }
}
