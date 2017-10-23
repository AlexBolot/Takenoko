package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.plot.Plot;

import java.awt.*;

public class Gardener extends Entity
{
    private static Gardener ourInstance = new Gardener();

    public static Gardener getInstance ()
    {
        return ourInstance;
    }

    /**
     When moving the gardener, makes bamboos grow, on the tile where the gardener is present and neightbour tiles of same color
     */
    @Override
    public void setCoords (Point point)
    {
        if (point == null) throw new IllegalArgumentException("Parameter is null");

        super.setCoords(point);

        GameBoard board = GameBoard.getInstance();

        Cell arrivalCell = board.getGrid().get(point);

        if (arrivalCell instanceof Plot)
        {
            Plot arrivalPlot = (Plot) arrivalCell;

            arrivalPlot.addBamboo();

            for (Cell c : board.getExistingNeihboors(this.getCoords()))
            {
                if (c instanceof Plot)
                {
                    Plot plot = (Plot) c;

                    if (plot.getColor().equals(arrivalPlot.getColor())) plot.addBamboo();
                }
            }
        }
    }
}

