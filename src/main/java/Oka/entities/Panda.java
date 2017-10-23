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

    private Panda ()
    {

    }

    public static Panda getInstance ()
    {
        return ourInstance;
    }

    @Override
    public void setCoords (Point point)
    {
        //Temporary
        super.setCoords(point);
    }

    public Bamboo gatherBamboo ()
    {
        GameBoard board = GameBoard.getInstance();
        Cell tile = board.getGrid().get(getCoords());
        if (tile.getCoords().equals(new Point())) return null;
        Plot currentPlot = (Plot) tile;

        return currentPlot.giveBamboo();
    }

    /**
     moves the panda and eat bamboo at ariving state

     @param newCoords Point, destination Point
     */
    public Bamboo move (Point newCoords) throws IllegalArgumentException
    {
        if (!Vector.areAligned(getCoords(), newCoords)) throw new IllegalArgumentException("Panda must move in straight line");
        this.setCoords(newCoords);
        return this.gatherBamboo();
    }
}
