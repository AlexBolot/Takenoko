package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Bamboo;
import Oka.model.Cell;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.ArrayList;

public class Gardener extends Entity {
    private static Gardener ourInstance = new Gardener();
    private Point coords = new Point();

    private Gardener() {

    }

    public static Gardener getInstance() {
        return ourInstance;
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public void move(Point newCoords) {
        this.setCoords(newCoords);
        this.growBamboo();
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
            currentPlot.addBamboo(new Bamboo(currentColor));
            //adding to neightbours
            for (Cell c : board.getExistingNeihboors(this.getCoords()))
            {
                if (!c.getCoords().equals(new Point())) {

                    Plot plot = (Plot) c;

                    if (plot.getColor().equals(currentColor)) {
                        plot.addBamboo(new Bamboo(currentColor));
                    }
                }
            }
        }

    }
}

