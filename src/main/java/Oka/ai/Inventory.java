package Oka.ai;

import Oka.model.Enums;
import Oka.model.goal.Goal;
import Oka.model.plot.state.NeutralState;

import java.util.ArrayList;

public class Inventory {

    private BambooHolder bambooHolder = new BambooHolder();
    private GoalHolder goalHolder = new GoalHolder();
    private PlotStateHolder plotStateHolder = new PlotStateHolder();


    //============ Active functions ============

    /**
     * check the goals and returns all the validated ones
     *
     * @return List of all the validated goals ( including the one already valid )
     */
    public ArrayList<Goal> checkGoals() {
        this.goalHolder.checkGoal(bambooHolder);
        return this.validatedGoals(true);

    }


    // =============== Adders ==================
    protected void addBamboo(Enums.Color color) {
        this.bambooHolder.addBamboo(color);
    }

    void addGoal(Goal goal) {
        this.goalHolder.addGoal(goal);
    }

    void addPlotState(NeutralState plotState) {
        this.plotStateHolder.add(plotState);
    }

    //=============== Getters =================
    BambooHolder bambooHolder() {
        return this.bambooHolder;
    }

    ArrayList<Goal> goalHolder() {
        return this.goalHolder;
    }

    ArrayList<Goal> validatedGoals(boolean validated) {
        return this.goalHolder.getGoalValidated(validated);
    }

    PlotStateHolder plotStates() {
        return this.plotStateHolder;
    }
}
