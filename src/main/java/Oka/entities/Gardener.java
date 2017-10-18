package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Bamboo;
import Oka.model.Cell;
import Oka.model.plot.Plot;

import java.awt.*;

public class Gardener
{
    private static Gardener ourInstance = new Gardener();
    private        Point    coords      = new Point();

    private Gardener ()
    {

    }

    public static Gardener getInstance ()
    {
        return ourInstance;
    }

    public Point getCoords ()
    {
        return coords;
    }

    public void setCoords (Point coords)
    {
        this.coords = coords;
    }

    public void move (Point newCoords)
    {
        this.setCoords(newCoords);
        this.growBamboo();
    }

    /**
     * make bamboo grows, on the tile where the gardener is present for now, later implementation wil include
     * the neighboor tile groing effect
     */
    private void growBamboo ()
    {
        GameBoard board = GameBoard.getInstance();
        Cell cell = board.getCell(this.getCoords());
        if (!cell.getCoords().equals(new Point(0, 0))) {
            Plot plot = (Plot) cell;
            plot.addBamboo(new Bamboo(plot.getColor()));
        }

    }
}

