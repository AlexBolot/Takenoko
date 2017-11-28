package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Enums;
import Oka.model.plot.Plot;
import Oka.model.plot.state.NeutralState;

import java.util.List;

public class GardenerGoalMultiPlot extends GardenerGoal
{
    private int plotAmount;

    public GardenerGoalMultiPlot (int value, int bambooAmount, Enums.Color color, NeutralState state, int plotAmount)
    {
        super(value, bambooAmount, color, state);
        this.plotAmount = plotAmount;
    }

    public int getPlotAmount ()
    {
        return plotAmount;
    }

    @Override
    /**
     * A method which tries to validate the goal
     */
    public boolean validate ()
    {
        List<Plot> plots = GameBoard.getInstance().getPlots();

        int compteur = 0;
        for (Plot plot : plots)
        {
            if (plot.getColor().equals(getColor()) && plot.getBamboo().size() == (getBambooAmount())) compteur++;

            if (compteur == plotAmount)
            {
                setValidated(true);
                return true;
            }
        }
        return false;
    }

}
