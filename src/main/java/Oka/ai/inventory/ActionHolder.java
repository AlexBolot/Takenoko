package Oka.ai.inventory;

import Oka.model.Enums;

import java.util.HashMap;

public class ActionHolder extends HashMap<Enums.Action,Integer>
{
    private int actionLeft = 2;

    /**
     * This ActionHolder is an hashmap with every action possible in it and the values corresponding are 1.
     * This implementation allows us to reduce it to 0 when an action is done.
     * Moreover it allows us to set it to 2 when the AI can do the same action two times ( wind weather )
     */
    public ActionHolder(){
        for(Enums.Action action : Enums.Action.values()){
            this.put(action,1);
        }
    }

    public int getActionLeft () {
        return actionLeft;
    }

    /**
     * @return true if the AI can still do something.
     * <br> False if he has no more actionleft.
     */
    public boolean hasActionsLeft ()
    {
        return actionLeft > 0;
    }

    /**
     * @param action Take an action of the AI that can be done.
     * @return True if he can still do this action one time.
     * <br> False if he cannot.
     */
    public boolean hasActionsLeft (Enums.Action action){
        return this.get(action)>=1 && hasActionsLeft();
    }

    /**
     * @param action take an action possible for the AI as a parameter.
     * A method which remove one to the actionleft variable each time an action is done by the AI.
     */
    public void consumeAction (Enums.Action action)
    {
        Integer oldAmount = this.get(action);

        if (oldAmount > 0)
        {
            this.replace(action, oldAmount - 1);
            actionLeft--;
        }
    }

    /**
     * The AI who's playing can have three actions instead of two.
     */
    public void sunWeather ()
    {
        this.actionLeft = 3;
    }

    /**
     * In the hashmap of action, replace 1 by 2, which allows the AI who's playing to do an action two times instead of one.
     */
    public void windWeather()
    {
        for(Enums.Action action : this.keySet()){
            this.replace(action,2);
        }
    }
}
