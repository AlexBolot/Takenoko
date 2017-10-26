package Oka.ai;

import Oka.model.Enums;
import Oka.model.goal.Goal;

import java.util.ArrayList;

public class AI
{

    protected int actionsLeft;
    private String name;
    private BambooHolder bambooholder = new BambooHolder();
    private GoalHolder   goalholder   = new GoalHolder();

    public AI(String name){
        this.name=name;
    }

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
        return bambooholder;
    }

    public void addBamboo (Enums.Color color)
    {
        this.bambooholder.addBamboo(color);
    }

    public ArrayList<Goal> getGoals ()
    {
        return goalholder;
    }

    public void addGoal (Goal goal)
    {
        this.goalholder.addGoal(goal);
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public ArrayList<Goal> getGoalValidated(boolean validated)
    {
        return new ArrayList<Goal>(goalholder.getGoalValidated(validated));
    }

    public int checkGoal ()
    {
        goalholder.checkGoal(bambooholder);
        return goalholder.getGoalValidated(true).size();

    }


}

