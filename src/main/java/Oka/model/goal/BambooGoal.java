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
import java.util.Map;
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
    public int getColorAmount ()
    {
        return (int) values.values().stream().filter(integer -> integer > 0).count();
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

    //endregion

    //region==========METHODS==============
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

        str.append(super.toString());

        for (Map.Entry<Color, Integer> entry : values.entrySet())
        {
            //noinspection StringConcatenationInsideStringBufferAppend -> blablablabla :D
            str.append(" {color:" + entry.getKey() + ", amount:" + entry.getValue() + "} +");
        }

        str.deleteCharAt(str.length() - 1);

        return str.toString();
    }
    //endregion
}
