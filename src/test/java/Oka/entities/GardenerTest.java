package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.Pond;
import Oka.model.plot.Plot;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GardenerTest {
    @Before
    public void setUp() throws Exception {
    }

    //todo: update with different colors on plot, once plot's color is implemented
    @Test
    public void move() throws Exception {
        GameBoard board = GameBoard.getInstance();
        ArrayList<Cell> grid = new ArrayList<>();
        grid.add(new Pond());
        grid.add(new Plot(new Point(1, 1)));
        grid.add(new Plot(new Point(0, 1)));
        grid.add(new Plot(new Point(1, 0)));
        board.setGrid(grid);
        gardener.move(new Point(1, 1));
        assertEquals(2, ((Plot) board.getCell(new Point(1, 1))).getBamboo().size());
        assertEquals(2, ((Plot) board.getCell(new Point(0, 1))).getBamboo().size());
        assertEquals(2, ((Plot) board.getCell(new Point(1, 0))).getBamboo().size());
    }

    private Gardener gardener = Gardener.getInstance();

    @Test
    public void testGetAndSetCoords() {
        Point p1 = gardener.getCoords();
        Point p2 = new Point(p1.x + 1, p1.y + 1);

        gardener.setCoords(p2);

        assertEquals(p2, gardener.getCoords());
    }


}