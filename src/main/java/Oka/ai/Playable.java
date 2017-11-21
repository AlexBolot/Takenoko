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

public abstract class Playable
{
    //region==========ATTRIBUTES===========
    private Inventory inventory = new Inventory();
    private String name;
    //endregion

    //region==========CONSTRUCTORS=========
    protected Playable (String name)
    {
        this.name = name;

        Goal goal = DrawStack.getInstance().drawGoal(GoalType.BambooGoal).get();
        getInventory().addGoal(goal);
        Logger.printLine(getName() + " a pioché : " + goal);

        goal = DrawStack.getInstance().drawGoal(GoalType.GardenGoal).get();
        getInventory().addGoal(goal);
        Logger.printLine(getName() + " a pioché : " + goal);

        goal = DrawStack.getInstance().drawGoal(GoalType.PlotGoal).get();
        getInventory().addGoal(goal);
        Logger.printLine(getName() + " a pioché : " + goal);
    }
    protected  Playable(){

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

    public abstract void play ();


    //endregion

    //region==========EQUALS/TOSTRING======
    @Override
    public String toString ()
    {
        return getName();
    }

    //endregion
}
