package Oka.entities.IA;

import Oka.model.Bamboo;
import Oka.model.Objective;

import java.util.ArrayList;

public class IA {

    private int actionsLeft;
    private ArrayList<Bamboo> bamboos = new ArrayList<Bamboo>();
    private ArrayList<Objective> objectivespending = new ArrayList<Objective>();
    private ArrayList<Objective> objectivesdone = new ArrayList<Objective>();

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

    public ArrayList<Objective> getObjectivespending () {
        return objectivespending;
    }

    public void setObjectivespending (ArrayList<Objective> objectivespending) {
        this.objectivespending = objectivespending;
    }

    public ArrayList<Objective> getObjectivesdone () {
        return objectivesdone;
    }

    public void setObjectivesdone (ArrayList<Objective> objectivesdone) {
        this.objectivesdone = objectivesdone;
    }
}

