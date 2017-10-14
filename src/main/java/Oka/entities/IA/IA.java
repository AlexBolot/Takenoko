package Oka.entities.IA;

import Oka.model.Bamboo;
import Oka.model.Goal;

import java.util.ArrayList;

public class IA {

    private int actionsLeft;
    private ArrayList<Bamboo> bamboos = new ArrayList<Bamboo>();
    private ArrayList<Goal> pendinggoal = new ArrayList<Goal>();
    private ArrayList<Goal> donegoal = new ArrayList<Goal>();

    public int getActionsLeft () {
        return actionsLeft;
    }

    public void setActionsLeft (int actionsLeft) {
            this.actionsLeft = actionsLeft;
        }

        public ArrayList<Bamboo> getBamboos () {
            return bamboos;
        }

    public void setBamboos (ArrayList<Bamboo> bamboos) {
        this.bamboos = bamboos;
    }

    public ArrayList<Goal> getObjectivespending () {
        return pendinggoal;
    }

    public void setObjectivespending (ArrayList<Goal> objectivespending) {
        this.pendinggoal = objectivespending;
    }

    public ArrayList<Goal> getObjectivesdone () {
        return donegoal;
    }

    public void setObjectivesdone (ArrayList<Goal> objectivesdone) {
        this.donegoal = objectivesdone;
    }
}

