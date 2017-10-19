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

import Oka.ai.AI;

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

    public boolean validate (AI AI)
    {
        if (AI.getBamboos().size() >= bambooAmount)
            return true;
        else
            return false;

    }
}
