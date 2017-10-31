package Oka.ai;

import Oka.entities.Entity;
import Oka.model.Enums;
import Oka.model.goal.Goal;
import Oka.model.plot.state.NeutralState;

import java.util.ArrayList;

public abstract class AI extends Playable
{
    //region==========CONSTRUCTORS=========
    public AI (String name)
    {
        super(name);
    }
    //endregion

    //region==========GETTER/SETTER========
    @Override
    public abstract void play ();

    protected abstract boolean moveGardener ();

    protected abstract boolean movePanda ();

    protected abstract void placePlot ();

    protected abstract boolean moveEntity (Entity entity, int bambooSize, Enums.Color color);
    //endregion

    //region==========METHODS==============
    public void addBamboo (Enums.Color color)
    {
        getInventory().addBamboo(color);
    }

    public void addGoal (Goal goal)
    {
        getInventory().addGoal(goal);
    }

    public void addPlotState (NeutralState plotState)
    {
        getInventory().addPlotState(plotState);
    }

    public ArrayList<Goal> getGoalValidated (boolean validated)
    {
        return new ArrayList<>(getInventory().validatedGoals(validated));
    }

    public int checkGoal ()
    {
        return getInventory().checkGoals().size();
    }
    //endregion
}

