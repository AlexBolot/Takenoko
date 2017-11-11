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
        return state.toString();
    }

    /**
     * Two State are equals if they are the same kind of state
     *
     * @param obj Other State
     * @return Boolean True if state are of same kind.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NeutralState && this.getState() == ((NeutralState) obj).getState();
    }
}
