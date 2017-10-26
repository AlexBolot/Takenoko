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

import Oka.ai.BambooHolder;
import Oka.model.Enums;

public class BambooGoal extends Goal {
    private int bambooAmount;
    private Enums.Color bambooColor;

    public Enums.Color getBambooColor() {
        return bambooColor;
    }

    public void setBambooAmount(int bambooAmount) {
        this.bambooAmount = bambooAmount;
    }

    public BambooGoal(int value, int bambooAmount, Enums.Color bambooColor) {
        super(value);
        this.bambooAmount = bambooAmount;
        this.bambooColor = bambooColor;
    }

    public String toString(){
        return super.toString() +" bambooAmount = "+bambooAmount +" bambooColor = " + bambooColor;
    }

    public int getBambooAmount() {
        return bambooAmount;
    }

    public void validate(BambooHolder bambooHolder) {
        if (bambooHolder.countBamboo(bambooColor) >= bambooAmount){
            setValidated(true);
            bambooHolder.removeByColor(bambooColor,bambooAmount);
        }
    }

    public Enums.Color bamboocolor() {
        return bambooColor;
    }
}
