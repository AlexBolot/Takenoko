package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static Oka.model.Enums.Color.PINK;
import static Oka.model.Enums.Color.YELLOW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GardenerTest
{
    private Gardener gardener = Gardener.getInstance();

    @Before
    public void setUp () throws Exception
    {
        //Placing gardener back on the pond.
        Cleaner.cleanGardener();

        //Reset the grid and available cells.
        Cleaner.cleanGameBoard();
    }

    @After
    public void tearDown () throws Exception
    {
        //Placing gardener back on the pond.
        Cleaner.cleanGardener();

        //Reset the grid and available cells.
        Cleaner.cleanGameBoard();
    }

    @Test
    public void move () throws Exception
    {
        GameBoard board = GameBoard.getInstance();

        Point point1 = new Point(0, 1);
        Point point2 = new Point(1, 0);
        Point point3 = new Point(1, -1);

        Plot plot1 = new Plot(point1, PINK);
        Plot plot2 = new Plot(point2, PINK);
        Plot plot3 = new Plot(point3, YELLOW);

        board.addCell(plot1);
        board.addCell(plot2);
        board.addCell(plot3);

        //Plots come with 1 bamboo when they are placed.
        assertEquals(1, plot1.getBamboo().size());
        assertEquals(1, plot2.getBamboo().size());
        assertEquals(1, plot3.getBamboo().size());

        gardener.move(point1);

        //Plot1 grew a bamboo because gardener moved ther
        //Plot3 didn't change because it has a diferent color
        //Plot2 grew a bamboo because it's next to Plot1 and same color
        assertEquals(2, plot1.getBamboo().size());
        assertEquals(2, plot2.getBamboo().size());
        assertEquals(1, plot3.getBamboo().size());
    }

    @Test
    public void testGetAndSetCoords ()
    {
        Point p1 = new Point(1, 5);
        Point p2 = new Point(3, 4);

        gardener.setCoords(p2);

        assertEquals(p2, gardener.getCoords());
        assertNotEquals(p1, gardener.getCoords());
    }


}