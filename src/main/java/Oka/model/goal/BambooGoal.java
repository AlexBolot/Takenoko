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

import Oka.ai.inventory.BambooHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import static Oka.model.Enums.Color;

public class BambooGoal extends Goal
{
    //region==========ATTRIBUTES===========
    private HashMap<Color, Integer> values = new HashMap<>();
    //endregion

    //region==========CONSTRUCTORS=========
    public BambooGoal (int value, int bambooAmount, Color bambooColor)
    {
        super(value);
        this.values.put(bambooColor, bambooAmount);
    }

    public BambooGoal (int value, HashMap<Color, Integer> values)
    {
        super(value);
        this.values = values;
    }
    //endregion

    //region==========GETTER/SETTER========
    public HashMap<Color, Integer> getValues ()
    {
        return values;
    }

    public int getAmountByColor (Color color)
    {
        return values.get(color);
    }

    /**
     @return Randomly, one of the colors required for this goal.
     */
    public Color getColor ()
    {
        ArrayList<Color> colors = values.keySet()
                                        .stream()
                                        .filter(color -> values.get(color) > 0)
                                        .collect(Collectors.toCollection(ArrayList::new));

        return colors.get(new Random().nextInt(colors.size()));
    }

    public Color getColor (BambooHolder bambooHolder)
    {
        for (Color color : values.keySet())
        {
            if (bambooHolder.countBamboo(color) < values.get(color)) return color;
        }

        //Should never be able to return null
        //(should be validated before that)
        return null;
    }

    public double getRatio ()
    {
        double totalBambooRequired = values.values().stream().reduce((val1, val2) -> val1 + val2).orElse(0);

        return sigmoid(getValue() / totalBambooRequired);
    }
    //endregion

    //region==========METHODS==============
    /**
     * @param bambooHolder the BambooHolder of the AI who's playing.
     * A method which tries to validate the goal
     */
    public void validate (BambooHolder bambooHolder)
    {
        for (Color color : values.keySet())
        {
            if (bambooHolder.countBamboo(color) < values.get(color)) return;
        }

        for (Color color : values.keySet())
        {
            bambooHolder.removeByColor(color, values.get(color));
        }

        setValidated(true);
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    public String toString ()
    {
        StringBuilder str = new StringBuilder();
        str.append(getClass().getSimpleName());

       /* for (Map.Entry<Color, Integer> entry : values.entrySet())
        {
            //noinspection StringConcatenationInsideStringBufferAppend -> blablablabla :D
            str.append(" {color:" + entry.getKey() + ", amount:" + entry.getValue() + "} +");
        }*/
        str.append(" ->value : " + this.getValue());
        //str.deleteCharAt(str.length() - 1);

        return str.toString();
    }

    @Override
    public boolean equals (Object obj)
    {

        if (obj == null || !(obj instanceof BambooGoal)) return false;

        BambooGoal c = (BambooGoal) obj;

        return ((BambooGoal) obj).getColor()
                                 .equals(this.getColor()) && this.getAmountByColor(this.getColor()) == ((BambooGoal) obj).getAmountByColor(((BambooGoal) obj)
                                                                                                                                                   .getColor());
    }
    //endregion
}
