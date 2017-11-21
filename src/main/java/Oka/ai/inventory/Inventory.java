package Oka.ai.inventory;

import Oka.model.Enums;
import Oka.model.goal.Goal;
import Oka.model.plot.state.NeutralState;

import java.util.ArrayList;

/*..................................................................................................
 . Copyright (c)
 .
 . The Inventory	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 30/10/17 21:09
 .................................................................................................*/

public class Inventory
{
    private BambooHolder    bambooHolder         = new BambooHolder();
    private GoalHolder      goalHolder           = new GoalHolder();
    private PlotStateHolder plotStateHolder      = new PlotStateHolder();
    private int             channels             = 0;
    private int             turnsWithoutPickGoal = 0;
    private ActionHolder    actionHolder         = new ActionHolder();

    //region============ Active functions ============

    /**
     check the goals and returns all the validated ones

     @return List of all the validated goals ( including the one already valid )
     */
    public ArrayList<Goal> checkGoals ()
    {
        this.goalHolder.checkGoal(bambooHolder);
        return this.validatedGoals(true);
    }

    //endregion

    //region=============== Adders ==================
    public void addBamboo (Enums.Color color)
    {
        this.bambooHolder.addBamboo(color);
    }

    public void addGoal (Goal goal)
    {
        this.goalHolder.addGoal(goal);
    }

    public void addPlotState (NeutralState plotState)
    {
        this.plotStateHolder.add(plotState);
    }

    public void addChannel ()
    {
        channels++;
    }
    //endregion

    //region =============== Getters =================
    public BambooHolder bambooHolder ()
    {
        return this.bambooHolder;
    }

    public GoalHolder goalHolder ()
    {
        return this.goalHolder;
    }

    public ArrayList<Goal> validatedGoals (boolean validated)
    {
        return this.goalHolder.getGoalValidated(validated);
    }

    public PlotStateHolder plotStates ()
    {
        return this.plotStateHolder;
    }

    public int getTurnsWithoutPickGoal ()
    {
        return turnsWithoutPickGoal;
    }

    public void addTurnWithoutPickGoal ()
    {
        turnsWithoutPickGoal++;
    }

    public void resetTurnWithoutPickGoal ()
    {
        turnsWithoutPickGoal = 0;
    }

    public int getChannelAmount ()
    {
        return channels;
    }

    public boolean hasChannel ()
    {
        return channels > 0;
    }

    public void removeChannel ()
    {
        channels--;
    }

    public void resetActionHolder ()
    {
        actionHolder = new ActionHolder();
    }

    public ActionHolder getActionHolder ()
    {
        return actionHolder;
    }

    public int getValueOfGoalHolder (GoalHolder goalholder)
    {
        int s = 0;
        for (Goal goall : goalholder.getGoalValidated(true))
        {
            s = s + goall.getValue();
        }
        return (s);
    }
    //endregion
}
