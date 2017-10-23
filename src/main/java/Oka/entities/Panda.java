package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Bamboo;
import Oka.model.Cell;
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
    public Bamboo setCoords (Point point)
    {
        //Temporary
        super.setCoords(point);
        return gatherBamboo();

    }

    public Bamboo gatherBamboo ()
    {
        GameBoard board = GameBoard.getInstance();
        Cell tile = board.getGrid().get(getCoords());
        if (tile.getCoords().equals(new Point())) return null;
        Plot currentPlot = (Plot) tile;

        return currentPlot.giveBamboo();
    }
}
