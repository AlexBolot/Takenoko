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

    //region==========ATTRIBUTES===========
    private int bambooAmount;
    private Enums.Color bambooColor;
    //endregion

    //region==========CONSTRUCTORS=========
    public BambooGoal(int value, int bambooAmount, Enums.Color bambooColor) {
        super(value);
        this.bambooAmount = bambooAmount;
        this.bambooColor = bambooColor;
    }
    //endregion

    //region==========GETTER/SETTER========
    public Enums.Color getBambooColor() {
        return bambooColor;
    }

    public void setBambooAmount(int bambooAmount) {
        this.bambooAmount = bambooAmount;
    }

    public int getBambooAmount() {
        return bambooAmount;
    }
    //endregion

    //region==========METHODS==============
    public void validate(BambooHolder bambooHolder) {
        if (bambooHolder.countBamboo(bambooColor) >= bambooAmount){
            setValidated(true);
            bambooHolder.removeByColor(bambooColor,bambooAmount);
        }
    }

    public Enums.Color bambooColor () {
        return bambooColor;
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    public String toString(){
        return super.toString() +" bambooAmount = "+bambooAmount +" bambooColor = " + bambooColor;

    }
    //endregion




}
