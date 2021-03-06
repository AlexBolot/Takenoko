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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import static Oka.model.Enums.Color.GREEN;
import static Oka.model.Enums.Color.YELLOW;
import static org.junit.Assert.*;

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
    public void testGetCell() {
        GameBoard board = GameBoard.getInstance();

        board.setGrid(emptyGrid);

        assertNull(board.getGrid().get(new Point(0, 1)));
        assertEquals(pond, board.getGrid().get(new Point()));

        board.setGrid(mediumGrid);
        Cell plot = mediumGrid.get(new Point(0, 1));
        assertEquals(plot, board.getGrid().get(plot.getCoords()));
    }

    @Test
    public void testAddCell() {
        GameBoard board = GameBoard.getInstance();

        board.setGrid(emptyGrid);
        Plot plot = new Plot(new Point(1, 0), Enums.Color.PINK);

        board.addCell(plot);

        assertEquals(plot, board.getGrid().get(new Point(1, 0)));
    }


    @Test
    public void moveEntityForbidenAxis ()
    {
        Cleaner.cleanGameBoard();
        GameBoard board = GameBoard.getInstance();
        Plot plot = new Plot(new Point(1, -2), GREEN);
        board.getGrid().put(new Point(1, -2), plot);

        GameController.getInstance().setCurrentPlayer(new AISimple("dummy"));

        assertEquals(false, GameBoard.getInstance().moveEntity(Panda.getInstance(), new Point(1, -2)));

    }

    @Test
    public void addIrrigation() {
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

        assertTrue(GameBoard.getInstance().addIrrigation(point, point1));
        assertEquals(1, GameBoard.getInstance().getIrrigation().size());
        assertTrue(GameBoard.getInstance().addIrrigation(point1, point2));
        assertEquals(2, GameBoard.getInstance().getIrrigation().size());
        assertTrue(GameBoard.getInstance().addIrrigation(point, point2));
        assertEquals(3, GameBoard.getInstance().getIrrigation().size());

    }

    @Test
    public void refusedIrrigation() {
        Cleaner.clearAll();
        Point point = new Point(1, 0);
        Point point1 = new Point(0, 1);
        Point point2 = new Point(1, 1);
        Point point3 = new Point(2, 0);

        Plot plot = new Plot(point, Enums.Color.GREEN);
        Plot plot1 = new Plot(point1, Enums.Color.GREEN);
        Plot plot2 = new Plot(point2, Enums.Color.GREEN);
        Plot plot3 = new Plot(point3, Enums.Color.GREEN);

        GameBoard.getInstance().addCell(plot);
        GameBoard.getInstance().addCell(plot1);
        GameBoard.getInstance().addCell(plot2);
        GameBoard.getInstance().addCell(plot3);

        assertFalse(GameBoard.getInstance().addIrrigation(point, point2));
        assertFalse(GameBoard.getInstance().getIrrigation().contains(new Irrigation(plot, plot2)));

        assertFalse(GameBoard.getInstance().addIrrigation(point1, point3));
    }

    @Test
    public void canPlaceIrigation() {
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

        Plot plot01 = (Plot) board.getGrid().get(p01);
        Plot plot10 = (Plot) board.getGrid().get(p10);

        Irrigation irg1001 = new Irrigation(plot01, plot10);
        board.setIrrigation(new HashSet<>(Collections.singletonList(irg1001)));

        assertTrue(board.canPlaceIrigation(p01, p11));
        assertTrue(board.canPlaceIrigation(p10, p11));

    }

    @Test
    public void verifIrrigationTest() {
        Cleaner.clearAll();

        GameBoard board = GameBoard.getInstance();
        mediumGrid.forEach((point, cell) -> {
            if (!point.equals(new Point())) board.addCell(cell);
        });
        Point p10 = new Point(1, 0);
        Point p01 = new Point(0, 1);
        Point p11 = new Point(1, 1);

        assertFalse(board.verifIrrigation(p01, p10));
        assertFalse(board.verifIrrigation(p10, p01));

        Irrigation irg = new Irrigation((Plot) board.getGrid().get(p01), (Plot) board.getGrid().get(p10));
        board.getIrrigation().add(irg);

        assertTrue(board.verifIrrigation(p01, p10));
        assertTrue(board.verifIrrigation(p10, p01));

        Plot plot11 = new Plot(p11, Enums.Color.GREEN);

        board.addCell(plot11);

        assertFalse(board.verifIrrigation(p01, p11));
        assertFalse(board.verifIrrigation(p10, p11));

        irg = new Irrigation((Plot) board.getGrid().get(p01), (Plot) board.getGrid().get(p11));
        board.getIrrigation().add(irg);
        irg = new Irrigation((Plot) board.getGrid().get(p10), (Plot) board.getGrid().get(p11));
        board.getIrrigation().add(irg);

        assertTrue(board.verifIrrigation(p01, p11));
        assertTrue(board.verifIrrigation(p10, p11));

    }

    @Test
    public void bambooOnIrrigated() {

        GameBoard board = GameBoard.getInstance();
        Cleaner.cleanGameBoard();


        Point p01 = new Point(0, 1);
        Point p10 = new Point(1, 0);
        Point p11 = new Point(1, 1);

        Plot plot01 = new Plot(p01, GREEN);

        assertEquals(0, plot01.getBamboo().size());

        board.addCell(plot01);
        assertEquals(1, plot01.getBamboo().size());

        Cleaner.cleanGameBoard();
        board = GameBoard.getInstance();

        mediumGrid.values().stream().filter(cell -> !(cell instanceof Pond))
                .forEach(board::addCell);

        board.addIrrigation(p01, p10);
        Plot plot11 = new Plot(p11, GREEN);
        board.addCell(plot11);

        assertEquals(0, plot11.getBamboo().size());

        board.addIrrigation(p01, p11);

        assertEquals(1, plot11.getBamboo().size());


    }

    @Test
    public void distanceToPondTest() {
        GameBoard board = GameBoard.getInstance();

        assertEquals(0, board.distanceToPond(new Point()));

        assertEquals(1, board.distanceToPond(new Point(1, 0)));
        assertEquals(1, board.distanceToPond(new Point(0, 1)));
        assertEquals(1, board.distanceToPond(new Point(-1, 0)));
        assertEquals(1, board.distanceToPond(new Point(0, -1)));
        assertEquals(1, board.distanceToPond(new Point(1, -1)));
        assertEquals(1, board.distanceToPond(new Point(-1, 1)));


    }

    @Test
    public void negativeVectorCanMoves() {
        Cleaner.clearAll();

        GameBoard board = GameBoard.getInstance();

        Plot p10 = new Plot(new Point(1, 0), YELLOW);
        board.getGrid().put(new Point(1, 0), p10);

        Plot p11 = new Plot(new Point(1, 1), YELLOW);
        board.getGrid().put(new Point(1, 1), p11);

        Plot p1N1 = new Plot(new Point(1, -1), YELLOW);
        board.getGrid().put(new Point(1, -1), p1N1);

        Plot p1N2 = new Plot(new Point(1, -2), GREEN);
        board.getGrid().put(new Point(1, -2), p1N2);

        Panda panda = Panda.getInstance();

        panda.setCoords(new Point(1, 1));
        GameBoard gc = GameBoard.getInstance();
        assertTrue(gc.canMoveEntity(Panda.getInstance(), new Point(1, -2)));
        board.getGrid().remove(new Point(1, 0));
        assertFalse(gc.canMoveEntity(Panda.getInstance(), new Point(1, -2)));


    }

}