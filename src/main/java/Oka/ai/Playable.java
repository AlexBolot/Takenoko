package Oka.ai;

/*..................................................................................................
 . Copyright (c)
 .
 . The Playable	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 31/10/17 20:40
 .................................................................................................*/

import Oka.ai.inventory.Inventory;
import Oka.controler.DrawStack;
import Oka.model.Enums.GoalType;
import Oka.model.goal.Goal;
import Oka.utils.Logger;

import java.util.Random;

public abstract class Playable
{
    //region==========ATTRIBUTES===========
    private Inventory inventory = new Inventory();
    private String name;
    private int actionsLeft = 2;
    //endregion

    //region==========CONSTRUCTORS=========
    protected Playable (String name)
    {
        this.name = name;

        for (int i = 0; i < 3; i++)
        {
            GoalType[] values = GoalType.values();
            GoalType goalType = values[new Random().nextInt(values.length)];

            Goal goal = DrawStack.drawGoal(goalType);
            getInventory().addGoal(goal);
            Logger.printLine(getName() + " picked : " + goal);
        }
    }
    //endregion

    //region==========GETTER/SETTER========
    public String getName ()
    {
        return name;
    }

    public Inventory getInventory ()
    {
        return inventory;
    }
    //endregion

    //region==========METHODS==============
    protected boolean hasActionsLeft ()
    {
        return actionsLeft > 0;
    }

    protected void consumeAction ()
    {
        this.actionsLeft--;
    }

    public abstract void play ();

    protected void resetActions ()
    {
        actionsLeft = 2;
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    @Override
    public String toString ()
    {
        return getName();
    }
    //endregion
}
