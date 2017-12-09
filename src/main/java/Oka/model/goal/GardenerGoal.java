package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Enums.Color;
import Oka.model.plot.Plot;
import Oka.model.plot.state.NeutralState;

import java.util.List;

public class GardenerGoal extends Goal
{
    //region==========ATTRIBUTES===========
    private Color        color;
    private int          bambooAmount;
    private NeutralState state;
    //endregion

    //region==========CONSTRUCTORS=========
    public GardenerGoal (int value, int bambooAmount, Color color, NeutralState state)
    {
        super(value);
        this.bambooAmount = bambooAmount;
        this.color = color;
        this.state = state;
    }
    //endregion

    //region==========GETTER/SETTER========
    public int getBambooAmount ()
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

    public double getRatio ()
    {
        return sigmoid(getValue() / (double) bambooAmount);
    }

    public NeutralState getState ()
    {
        return state;
    }

    //endregion

    //region==========METHODS==============

    /**
     A method which tries to validate the goal
     */
    public boolean validate ()
    {
        List<Plot> plots = GameBoard.getInstance().getPlots();
        boolean valid = plots.stream().anyMatch(plot -> plot.getColor().equals(getColor()) && plot.getBamboo()
                                                                                                  .size() == getBambooAmount() && plot.getState()
                                                                                                                                      .equals(state));
        setValidated(valid);
        return valid;
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    public String toString ()
    {
        return String.format("%s {%s}{%s}{%d}",
                             getClass().getSimpleName(),
                             color.toString().substring(0, 2),
                             state.toString().substring(0, 2),
                             bambooAmount);
    }
    //endregion
}
