package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.Enums.Color;
import Oka.model.plot.Plot;
import Oka.model.plot.state.NeutralState;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class GardenerGoal extends Goal
{
    //region==========ATTRIBUTES===========
    private Color color;
    private int   bambooAmount;
    private NeutralState state;
    //endregion

    //region==========CONSTRUCTORS=========
    public GardenerGoal (int value, int bambooAmount, Color color, NeutralState State)
    {
        super(value);
        this.bambooAmount = bambooAmount;
        this.color = color;
    }
    //endregion

    //region==========GETTER/SETTER========
    int getBambooAmount()
    {
        return bambooAmount;
    }

    public void setBambooAmount (int bambooAmount)
    {
        this.bambooAmount = bambooAmount;
    }

    public Color getColor ()
    {
        return color;
    }

    public void setColor (Color color)
    {
        this.color = color;
    }
    //endregion

    //region==========METHODS==============
    public boolean validate ()
    {
        List<Plot> plots = GameBoard.getInstance().getPlots();
        boolean valid = plots.stream().anyMatch(plot -> plot.getColor().equals(getColor()) && plot.getBamboo().size() == getBambooAmount());
        setValidated(valid);
        return valid;

    }
    //endregion

    //region==========EQUALS/TOSTRING======
    public String toString ()
    {
        return super.toString() + " bambooAmount = " + bambooAmount + " plotColor = " + color;
    }
    //endregion
}
