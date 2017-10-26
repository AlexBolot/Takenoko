package Oka.ai;

import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;

import java.util.ArrayList;

public class GoalHolder extends ArrayList<Goal>{

    public ArrayList<Goal> getGoalValidated(boolean validate){
        ArrayList<Goal> listGoal = new ArrayList<>();

        for(Goal goal : this){
            if( (goal.isValidated() && validate) || (!goal.isValidated() && !validate) )
                listGoal.add(goal);
        }
        return listGoal;
    }

    public void checkGoal(BambooHolder bambooHolder){
        this.forEach(goal -> {
            if (!goal.isValidated()){
                if (goal instanceof BambooGoal){
                    goal.validate(bambooHolder);
                }
                else if (goal instanceof GardenerGoal) {
                    ((GardenerGoal) goal).validate();
                }
            }
        });
    }
    public void addGoal(Goal goal){
        this.add(goal);
    }
}
