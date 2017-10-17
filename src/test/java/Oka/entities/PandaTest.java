package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Bamboo;
import Oka.model.Cell;
import Oka.model.plot.Plot;
import junit.framework.TestCase;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    @Test
    public void testGatherBamboo() {
        GameBoard board = GameBoard.getInstance();
        Bamboo b = new Bamboo(Color.BLUE);
        ArrayList<Cell> grid = new ArrayList<Cell>();
        Plot p = new Plot(new Point(0, 1));
        p.addBamboo(b);
        grid.add(p);
        board.setGrid(grid);
        panda.setCoords(new Point(0, 1));
        assertEquals(b, panda.gatherBamboo());
        assertEquals(new ArrayList<Bamboo>(), p.getBamboo());
    }
}