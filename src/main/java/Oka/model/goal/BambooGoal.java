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

import static Oka.model.Enums.Color;

public class BambooGoal extends Goal
{
    //region==========ATTRIBUTES===========
    private int   bambooAmount;
    private Color bambooColor;
    //endregion

    //region==========CONSTRUCTORS=========
    public BambooGoal (int value, int bambooAmount, Color bambooColor)
    {
        super(value);
        this.bambooAmount = bambooAmount;
        this.bambooColor = bambooColor;
    }
    //endregion

    //region==========GETTER/SETTER========
    public Color getBambooColor ()
    {
        return bambooColor;
    }

    public int getBambooAmount ()
    {
        return bambooAmount;
    }

    public void setBambooAmount (int bambooAmount)
    {
        this.bambooAmount = bambooAmount;
    }
    //endregion

    //region==========METHODS==============
    public void validate (BambooHolder bambooHolder)
    {
        if (bambooHolder.countBamboo(bambooColor) >= bambooAmount)
        {
            setValidated(true);
            bambooHolder.removeByColor(bambooColor, bambooAmount);
        }
    }

    public Color bambooColor ()
    {
        return bambooColor;
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    public String toString ()
    {
        return super.toString() + " bambooAmount = " + bambooAmount + " bambooColor = " + bambooColor;

    }
    //endregion


}
