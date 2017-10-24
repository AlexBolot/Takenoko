package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Bamboo;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.plot.Plot;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PandaTest extends TestCase
{
    private Panda panda = Panda.getInstance();

    public void testGetAndSetCoords () throws Exception
    {
        Point p1 = panda.getCoords();
        Point p2 = new Point(p1.x + 1, p1.y + 1);

        panda.setCoords(p2);

        assertEquals(p2, panda.getCoords());
    }

    @Ignore ("find a better way to test this...")
    @Test
    public void testGatherBamboo ()
    {
        GameBoard board = GameBoard.getInstance();
        Bamboo b = new Bamboo(Enums.Color.PINK);
        HashMap<Point, Cell> grid = new HashMap<>();

        Plot plot = new Plot(Enums.Color.PINK);
        plot.addBamboo();

        grid.put(plot.getCoords(), plot);
        board.setGrid(grid);

        panda.setCoords(new Point(0, 1));

        //assertEquals(b, panda.gatherBamboo());
        assertEquals(new ArrayList<Bamboo>(), plot.getBamboo());
    }
}