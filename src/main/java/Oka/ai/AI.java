package Oka.ai;

import Oka.model.Enums;
import Oka.model.goal.Goal;

import java.util.ArrayList;

public class AI
{
    //region==========ATTRIBUTES===========
    protected int actionsLeft;
    private String name;
    private BambooHolder bambooHolder = new BambooHolder();
    private GoalHolder goalHolder = new GoalHolder();
    //endregion//

    //region==========CONSTRUCTORS=========
    public AI(String name){
        this.name=name;
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
        return bambooHolder;
    }

    public ArrayList<Goal> getGoals ()
    {
        return goalHolder;
    }

    public String getName () {
        return name;
    }
    //endregion

    //region==========METHODS==============
    public void addBamboo (Enums.Color color)
    {
        this.bambooHolder.addBamboo(color);
    }
    public void addGoal (Goal goal)
    {
        this.goalHolder.addGoal(goal);
    }
    public ArrayList<Goal> getGoalValidated(boolean validated)
    {
        return new ArrayList<>(goalHolder.getGoalValidated(validated));
    }

    public int checkGoal ()
    {
        goalHolder.checkGoal(bambooHolder);
        return goalHolder.getGoalValidated(true).size();

    }
    //endregion


}

