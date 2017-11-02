package Oka.controler;

import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Pond;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class GameBoardTest
{

    HashMap<Point, Cell> emptyGrid;
    HashMap<Point, Cell> mediumGrid;
    Pond                 pond;

    @Before
    public void init ()
    {
        Cleaner.clearAll();
        pond = new Pond();

        emptyGrid = new HashMap<>();
        emptyGrid.put(pond.getCoords(), pond);

        mediumGrid = new HashMap<>();
        mediumGrid.put(pond.getCoords(), pond);

        Plot plot = new Plot(new Point(0, 1), Enums.Color.PINK);

        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(1, 0), Enums.Color.GREEN);
        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(0, -1), Enums.Color.PINK);
        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(-1, 0), Enums.Color.YELLOW);
        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(-1, 1), Enums.Color.PINK);
        mediumGrid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(1, -1), Enums.Color.GREEN);
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
        Plot plot = new Plot(new Point(1, 0), Enums.Color.PINK);

        board.addCell(plot);

        assertEquals(plot, board.getGrid().get(new Point(1, 0)));
    }

    @Test
    public void testGivePlot () throws Exception
    {
        //todo right

    }

    @Test
    public void addIrrigation () throws Exception {
        Cleaner.clearAll();
        Point point = new Point(1,0);
        Point point1 = new Point (0,1);
        Point point2 = new Point (1,1);
        Plot plot = new Plot(point, Enums.Color.GREEN);
        Plot plot1 = new Plot(point1, Enums.Color.GREEN);
        Plot plot2 = new Plot(point2, Enums.Color.GREEN);
        GameBoard.getInstance().addCell(plot);
        GameBoard.getInstance().addCell(plot1);
        //GameBoard.getInstance().addCell(plot2);
        GameBoard.getInstance().addIrrigation(point,point1);
        assertEquals(2,GameBoard.getInstance().getIrrigation().size());
        // JE VOULAIS METTRE DES IRRIGATIONS ET DES CELLULES ET TESTER SI LA Méthode ajoute bien l'irrigation qu'on veut
}

}