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
import Oka.model.Bamboo;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class BambooGoal extends Goal {
    private int bambooAmount;
    private Color bambooColor;

    public void setBambooAmount(int bambooAmount) {
        this.bambooAmount = bambooAmount;
    }

    public BambooGoal(int value, int bambooAmount, Color bambooColor) {
        super(value);
        this.bambooAmount = bambooAmount;
        this.bambooColor = bambooColor;
    }

    public String toString(){
        return super.toString() +"bambooAmount = "+bambooAmount;
    }

    public int getBambooAmount() {
        return bambooAmount;
    }

    public boolean validate(AI AI) {
        ArrayList<Bamboo> listBamboo = AI.getBamboos();
        Stream rightBamboos = listBamboo
                .stream()
                .filter(b -> b.getColor().equals(this.bambooColor));
        int nbBamboo = (int) rightBamboos.count();
        return nbBamboo >= bambooAmount;

    }

    public Color bamboocolor() {
        return bambooColor;
    }
}
