package Oka.model.goal;

/*..................................................................................................
 . Copyright (c)
 .
 . The BambooGoal	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 16/10/17 14:11
 .................................................................................................*/

import Oka.entities.IA.IA;

import java.util.ArrayList;

public class BambooGoal extends Goal
{
    private int bambooAmount;

    public void setBambooAmount (int bambooAmount) {
        this.bambooAmount = bambooAmount;
    }

    public BambooGoal (int value, int bambooAmount)
    {
        super(value);
        this.bambooAmount = bambooAmount;
    }

    public int getBambooAmount() {
        return bambooAmount;
    }

    public IA validate(IA ia){
        if(ia.getBamboos().size() >= bambooAmount) {
            ia.getObjectivesdone().add(this);
            ia.setObjectivesdone(ia.getObjectivesdone());
            for(int i=0; i<bambooAmount; i++){
                ia.getBamboos().remove(i);
            }

        }
        return ia;
    }
}
