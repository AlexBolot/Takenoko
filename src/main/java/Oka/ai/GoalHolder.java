package Oka.ai;

import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;

import java.util.ArrayList;

public class GoalHolder extends ArrayList{

    public ArrayList<Goal> getGoalValidated(boolean validate){
        BambooGoal bambooGoal;
        ArrayList<Goal> listGoal = new ArrayList<>();

        for(Object o : this){
            bambooGoal = (BambooGoal) o;
            if( (bambooGoal.isValidated() && validate) || (!bambooGoal.isValidated() && !validate) )
                listGoal.add(bambooGoal);
        }
        return listGoal;
    }

    public BambooHolder checkGoal(BambooHolder bambooHolder){
        Goal goal;
        for(Object o : this) {
            goal = (Goal) o;

            if (!goal.isValidated() && goal.validate(bambooHolder)) {
                goal.setValidated(true);
                if (goal instanceof BambooGoal) {
                    BambooGoal bambooGoal = (BambooGoal) goal;
                    bambooHolder.removeByColor(bambooGoal.getBambooColor(),bambooGoal.getBambooAmount());
                }
            }
        }
        return bambooHolder;
    }

    public void addGoal(Goal goal){
        this.add(goal);
    }
}
