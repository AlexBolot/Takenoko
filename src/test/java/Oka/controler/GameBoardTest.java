package Oka.controler;

import Oka.model.Cell;
import Oka.model.Pond;
import Oka.model.plot.Plot;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GameBoardTest
{
    HashMap<Point, Cell> emptyGrid;
    HashMap<Point, Cell> mediumGrid;
    Pond                 pond;

    @Before
    public void init ()
    {
        pond = new Pond();

        emptyGrid = new HashMap<>();
        emptyGrid.put(pond.getCoords(), pond);

        mediumGrid = new HashMap<>();
        mediumGrid.put(pond.getCoords(), pond);

        Plot plot = new Plot(new Point(0, 1));
        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(1, 0));
        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(0, -1));
        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(-1, 0));
        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(-1, 1));
        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(1, -1));
        mediumGrid.put(plot.getCoords(), plot);

    }

    @Test
    public void testGetCell () throws Exception
    {
        GameBoard board = GameBoard.getInstance();

        board.setGrid(emptyGrid);

        assertNull(board.getGrid().get(new Point(0, 1)));
        assertEquals(pond, board.getGrid().get(new Point()));

        board.setGrid(mediumGrid);
        Cell plot = mediumGrid.get(new Point(0, 1));
        assertEquals(plot, board.getGrid().get(plot.getCoords()));
    }

    @Test
    public void testAddCell () throws Exception
    {
        GameBoard board = GameBoard.getInstance();

        board.setGrid(emptyGrid);
        Plot plot = new Plot(new Point(1, 0));

        board.addCell(plot);

        assertEquals(plot, board.getGrid().get(new Point(1, 0)));
    }

    @Test
    public void testGivePlot () throws Exception
    {
        //todo right

    }


}