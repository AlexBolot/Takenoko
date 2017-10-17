package Oka.entities.IA;

import Oka.model.Bamboo;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;

import java.util.ArrayList;

public class IA {

    private int actionsLeft;
    private ArrayList<Bamboo> bamboos = new ArrayList<Bamboo>();
    private ArrayList<Goal> pendinggoal = new ArrayList<Goal>();
    private ArrayList<Goal> donegoal = new ArrayList<Goal>();

    public IA(){};
    public IA(ArrayList<Goal> pendinggoal,ArrayList<Bamboo> bamboos){
        this.bamboos = bamboos;
        this.pendinggoal = pendinggoal;
    }
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

    public void checkGoal(){
        int tabIndex[] = new int[pendinggoal.size()],
                comptBamboo=0,
                comptGoal=0;
        for(int i=0; i<pendinggoal.size(); i++){
            Goal g = pendinggoal.get(i);
            if(g instanceof BambooGoal) {
                BambooGoal g1 = (BambooGoal) g;
                if(bamboos.size()-comptBamboo >= g1.getBambooAmount()) {
                    donegoal.add(g1);
                    tabIndex[comptGoal] = i;
                    comptGoal++;
                    comptBamboo += g1.getBambooAmount();
                }
            }
        }
        for(int z=tabIndex.length-1; z>0; z--){
            BambooGoal g1 = (BambooGoal) pendinggoal.get(z);

            for(int j=0; j<g1.getBambooAmount(); j++){
                bamboos.remove(bamboos.size()-1);
            }
            pendinggoal.remove(z);
        }
    }
}

