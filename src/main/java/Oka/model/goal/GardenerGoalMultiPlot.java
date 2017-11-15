package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.plot.Plot;
import Oka.model.plot.state.NeutralState;

import java.awt.*;
import java.util.HashMap;

public class GardenerGoalMultiPlot extends GardenerGoal
{
    private int plotAmount;

    public GardenerGoalMultiPlot (int value, int bambooAmount, Enums.Color color, NeutralState State, int plotAmount)
    {
        super(value, bambooAmount, color, State);
        this.plotAmount = plotAmount;
    }

    public int getPlotAmount ()
    {
        return plotAmount;
    }

    @Override
    public boolean validate ()
    {
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();
        int compteur = 0;
        for (Cell cell : grid.values())
        {
            if ((cell instanceof Plot) && ((Plot) cell).getColor().equals(getColor()) && (((Plot) cell).getBamboo()
                                                                                                       .size() == (getBambooAmount())))
                compteur++;

            if (compteur == plotAmount)
            {
                setValidated(true);
                return true;
            }
        }
        return false;
    }

}
