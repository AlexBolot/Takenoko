package Oka.controler;

import Oka.model.Cell;
import Oka.model.Pond;
import Oka.model.plot.Plot;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GameBoardTest {
    ArrayList<Cell> emptyGrid;
    ArrayList<Cell> mediumGrid;
    Pond pond;

    @Before
    public void init() {


        pond = new Pond();
        emptyGrid = new ArrayList<Cell>();
        emptyGrid.add(pond);
        mediumGrid = new ArrayList<Cell>();
        mediumGrid.add(pond);
        mediumGrid.add(new Plot(new Point(0, 1)));
        mediumGrid.add(new Plot(new Point(1, 0)));
        mediumGrid.add(new Plot(new Point(0, -1)));
        mediumGrid.add(new Plot(new Point(-1, 0)));
        mediumGrid.add(new Plot(new Point(-1, 1)));
        mediumGrid.add(new Plot(new Point(1, -1)));


    }

    @Test
    public void testGetCell() throws Exception {
        GameBoard board = GameBoard.getInstance();
        board.setGrid(emptyGrid);
        assertNull(board.getCell(new Point(0, 1)));
        assertEquals(pond, board.getCell(new Point()));
        board.setGrid(mediumGrid);
        assertEquals(new Plot(new Point(0, 1)), board.getCell(new Point(0, 1)));
    }

    @Test
    public void testAddCell() throws Exception {
        GameBoard board = GameBoard.getInstance();
        board.setGrid(emptyGrid);
        Plot plot = new Plot(new Point(1, 0));
        board.addCell(plot);
        assertEquals(plot, board.getCell(new Point(1, 0)));
    }

    @Test
    public void testGivePlot() throws Exception {
        //todo wright

    }


}