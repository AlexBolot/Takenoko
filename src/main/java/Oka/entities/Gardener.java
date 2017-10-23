package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.plot.Plot;

import java.awt.*;

public class Gardener extends Entity {
    private static Gardener ourInstance = new Gardener();

    private Gardener() {

    }

    public static Gardener getInstance() {
        return ourInstance;
    }

    @Override
    public Object setCoords (Point point)
    {
        //Temporary
        super.setCoords(point);
        this.growBamboo();
        return null;
    }

    /**
     * make bamboo grows, on the tile where the gardener is present and neightbour tiles of same color
     */
    private void growBamboo() {

        GameBoard board = GameBoard.getInstance();
        Cell currentCell = board.getGrid().get(this.getCoords());
        // there are no effects if the gardener is on the pond
        if (!currentCell.getCoords().equals(new Point(0, 0))) {
            //adding to current plot
            Plot currentPlot = (Plot) currentCell;
            Color currentColor = currentPlot.getColor();
            currentPlot.addBamboo();
            //adding to neightbours
            for (Cell c : board.getExistingNeihboors(this.getCoords()))
            {
                if (!c.getCoords().equals(new Point())) {

                    Plot plot = (Plot) c;

                    if (plot.getColor().equals(currentColor)) {
                        plot.addBamboo();
                    }
                }
            }
        }

    }
}

