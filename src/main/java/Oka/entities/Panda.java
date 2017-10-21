package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Bamboo;
import Oka.model.Cell;
import Oka.model.Vector;
import Oka.model.plot.Plot;

import java.awt.*;

public class Panda extends Entity
{
    private static Panda ourInstance = new Panda();
    private        Point coords      = new Point();


    private Panda ()
    {

    }

    public static Panda getInstance ()
    {
        return ourInstance;
    }

    public Point getCoords ()
    {
        return coords;
    }

    public void setCoords (Point coords) {
        this.coords = coords;
    }

    public Bamboo gatherBamboo ()
    {
        GameBoard board = GameBoard.getInstance();
        Cell tile = board.getGrid().get(coords);
        if (tile.getCoords().equals(new Point())) return null;
        Plot currentPlot = (Plot) tile;

        return currentPlot.giveBamboo();
    }

    /**
     * moves the panda and eat bamboo at ariving state
     *
     * @param newCoords Point, destination Point
     */
    public Bamboo move(Point newCoords) throws IllegalArgumentException {
        if (!Vector.areAligned(this.coords, newCoords))
            throw new IllegalArgumentException("Panda must move in straight line");
        this.setCoords(newCoords);
        return this.gatherBamboo();
    }
}
