package Oka.ai;

import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GoalHolder extends ArrayList<Goal>
{
    //region==========METHODS==============
    public ArrayList<Goal> getGoalValidated (boolean validate)
    {
        return this.stream()
                   .filter(goal -> (goal.isValidated() && validate) || (!goal.isValidated() && !validate))
                   .collect(Collectors.toCollection(ArrayList::new));
    }

    public void checkGoal (BambooHolder bambooHolder)
    {
        this.forEach(goal -> {
            if (!goal.isValidated())
            {
                if (goal instanceof BambooGoal)
                {
                    ((BambooGoal) goal).validate(bambooHolder);
                }
                else if (goal instanceof GardenerGoal)
                {
                    ((GardenerGoal) goal).validate();
                }
            }
        });
    }

    public void addGoal (Goal goal)
    {
        this.add(goal);
    }
    //endregion
}
