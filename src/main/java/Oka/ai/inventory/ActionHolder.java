package Oka.ai.inventory;

import Oka.model.Enums;

import java.util.HashMap;

public class ActionHolder extends HashMap<Enums.Action,Integer>
{
    private int actionLeft = 2;

    public ActionHolder(){
        for(Enums.Action action : Enums.Action.values()){
            this.put(action,1);
        }
    }

    public int getActionLeft () {
        return actionLeft;
    }

    public boolean hasActionsLeft ()
    {
        return actionLeft > 0;
    }

    public boolean hasActionsLeft (Enums.Action action){
        return this.get(action)>=1 && hasActionsLeft();
    }

    public void consumeAction (Enums.Action action)
    {
        Integer oldAmount = this.get(action);

        if (oldAmount > 0)
        {
            this.replace(action, oldAmount - 1);
            actionLeft--;
        }
    }

    public void sunWeather ()
    {
        this.actionLeft = 3;
    }

    public void windWeather()
    {
        for(Enums.Action action : this.keySet()){
            this.replace(action,2);
        }
    }
}
