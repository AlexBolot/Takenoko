package Oka.controler;

import Oka.ai.AISimple;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Irrigation;
import Oka.model.Pond;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import static Oka.model.Enums.Color.GREEN;
import static org.junit.Assert.*;

public class GameBoardTest {


    HashMap<Point, Cell> emptyGrid;
    HashMap<Point, Cell> mediumGrid;
    Pond pond;

    @Before
    public void init() {
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
    public void testGetCell() throws Exception {
        GameBoard board = GameBoard.getInstance();

        board.setGrid(emptyGrid);

        assertNull(board.getGrid().get(new Point(0, 1)));
        assertEquals(pond, board.getGrid().get(new Point()));

        board.setGrid(mediumGrid);
        Cell plot = mediumGrid.get(new Point(0, 1));
        assertEquals(plot, board.getGrid().get(plot.getCoords()));
    }

    @Test
    public void testAddCell() throws Exception {
        GameBoard board = GameBoard.getInstance();

        board.setGrid(emptyGrid);
        Plot plot = new Plot(new Point(1, 0), Enums.Color.PINK);

        board.addCell(plot);

        assertEquals(plot, board.getGrid().get(new Point(1, 0)));
    }

    @Test
    public void testGivePlot() throws Exception {
        //todo right
    }

    @Test
    public void moveEntityForbidenAxis() {
        Cleaner.cleanGameBoard();
        GameBoard board = GameBoard.getInstance();
        Plot plot = new Plot(new Point(1, -2), GREEN);
        board.getGrid().put(new Point(1, -2), plot);

        GameController.getInstance().setCurrentPlayer(new AISimple("dummy"));

        assertEquals(false, GameBoard.getInstance().moveEntity(Panda.getInstance(), new Point(1, -2)));

    }

    @Test
    public void addIrrigation() throws Exception {
        Cleaner.clearAll();
        Point point = new Point(1, 0);
        Point point1 = new Point(0, 1);
        Point point2 = new Point(1, 1);

        Plot plot = new Plot(point, Enums.Color.GREEN);
        Plot plot1 = new Plot(point1, Enums.Color.GREEN);
        Plot plot2 = new Plot(point2, Enums.Color.GREEN);

        GameBoard.getInstance().addCell(plot);
        GameBoard.getInstance().addCell(plot1);
        GameBoard.getInstance().addCell(plot2);

        GameBoard.getInstance().addIrrigation(point, point1);
        assertEquals(1, GameBoard.getInstance().getIrrigation().size());
        GameBoard.getInstance().addIrrigation(point1, point2);
        assertEquals(2, GameBoard.getInstance().getIrrigation().size());
        GameBoard.getInstance().addIrrigation(point, point2);
        assertEquals(3, GameBoard.getInstance().getIrrigation().size());

        // JE VOULAIS METTRE DES IRRIGATIONS ET DES CELLULES ET TESTER SI LA Méthode ajoute bien l'irrigation qu'on veut
    }

    @Test
    public void canPlaceIrigation() throws Exception {
        Cleaner.clearAll();

        GameBoard board = GameBoard.getInstance();
        mediumGrid.forEach((point, cell) -> {
            if (!point.equals(new Point())) board.addCell(cell);
        });
        Point p10 = new Point(1, 0);
        Point p01 = new Point(0, 1);
        Point p11 = new Point(1, 1);

        assertTrue(board.canPlaceIrigation(p01, p10));
        assertTrue(board.canPlaceIrigation(p10, p01));

        Plot plot11 = new Plot(p11, Enums.Color.GREEN);

        board.addCell(plot11);

        assertFalse(board.canPlaceIrigation(p01, p11));

        assertFalse(board.canPlaceIrigation(p10, p11));

        Irrigation irg1001 = new Irrigation(p01, p10);
        board.setIrrigation(new HashSet<Irrigation>(Collections.singletonList(irg1001)));

        assertTrue(board.canPlaceIrigation(p01, p11));
        assertTrue(board.canPlaceIrigation(p10, p11));

    }
}