package Oka.ai;

import Oka.model.Enums;
import Oka.model.goal.Goal;
import Oka.model.plot.state.NeutralState;

import java.util.ArrayList;

public class AI
{
    //region==========ATTRIBUTES===========
    protected int    actionsLeft;
    private   String name;

    private Inventory inventory = new Inventory();
    //endregion//

    //region==========CONSTRUCTORS=========
    public AI (String name)
    {
        this.name = name;
    }
    //endregion

    //region==========GETTER/SETTER========
    public int getActionsLeft ()
    {
        return actionsLeft;
    }

    public void setActionsLeft (int actionsLeft)
    {
        this.actionsLeft = actionsLeft;
    }

    public BambooHolder getBamboos ()
    {
        return this.inventory.bambooHolder();
    }

    public ArrayList<Goal> getGoals ()
    {
        return this.inventory.goalHolder();
    }

    public String getName ()
    {
        return name;
    }

    public PlotStateHolder getPlotStates ()
    {
        return this.inventory.plotStates();
    }
    //endregion

    //region==========METHODS==============
    public void addBamboo (Enums.Color color)
    {
        this.inventory.addBamboo(color);
    }

    public void addGoal (Goal goal)
    {
        this.inventory.addGoal(goal);
    }

    public void addPlotState (NeutralState plotState)
    {
        this.inventory.addPlotState(plotState);
    }

    public ArrayList<Goal> getGoalValidated (boolean validated)
    {
        return new ArrayList<>(this.inventory.validatedGoals(validated));
    }

    public int checkGoal ()
    {
        return this.inventory.checkGoals().size();
    }
    //endregion
}

