package Oka.entities.IA;

import Oka.model.Bamboo;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;

import java.util.ArrayList;

public class IA
{

    private int actionsLeft;
    private ArrayList<Bamboo> bamboos = new ArrayList<Bamboo>();
    private ArrayList<Goal>   goals   = new ArrayList<>();

    public int getActionsLeft ()
    {
        return actionsLeft;
    }

    public void setActionsLeft (int actionsLeft)
    {
        this.actionsLeft = actionsLeft;
    }

    public ArrayList<Bamboo> getBamboos ()
    {
        return bamboos;
    }

    public void addBamboo (Bamboo bamboo)
    {
        this.bamboos.add(bamboo);
    }

    public ArrayList<Goal> getGoals ()
    {
        return goals;
    }

    public void addGoal (Goal goal)
    {
        this.goals.add(goal);
    }

    public ArrayList<Goal> getDoneGoals ()
    {
        ArrayList<Goal> doneGoals = new ArrayList<>();

        for (Goal goal : goals)
        {
            if (goal.isValidated()) doneGoals.add(goal);
        }

        return doneGoals;
    }

    public ArrayList<Goal> getPendingGoals ()
    {
        ArrayList<Goal> pendingGoals = new ArrayList<>();

        for (Goal goal : goals)
        {
            if (!goal.isValidated()) pendingGoals.add(goal);
        }

        return pendingGoals;
    }

    public int checkGoal ()
    {
        for (Goal goal : goals)
        {
            if (!goal.isValidated() && goal.validate(this))
            {
                goal.setValidated(true);

                if (goal instanceof BambooGoal)
                {
                    for (int j = 0; j < ((BambooGoal) goal).getBambooAmount(); j++)
                    {
                        bamboos.remove(bamboos.size() - 1);
                    }
                }
            }
        }

        return getDoneGoals().size();
    }
}

