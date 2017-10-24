package Oka.entities;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Pond;
import Oka.model.plot.Plot;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class GardenerTest
{
    @Before
    public void setUp () throws Exception
    {
    }

    //todo: update with different colors on plot, once plot's color is implemented
    @Test
    public void moveUniformColor() throws Exception
    {
        GameBoard board = GameBoard.getInstance();

        HashMap<Point, Cell> grid = new HashMap<>();

        Pond pond = new Pond();
        grid.put(pond.getCoords(), pond);

        Plot plot = new Plot(new Point(1, 1), Enums.Color.PINK);
        grid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(0, 1), Enums.Color.PINK);
        grid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(1, 0), Enums.Color.PINK);
        grid.put(plot.getCoords(), plot);

        board.setGrid(grid);
        gardener.setCoords(new Point(0, 1));
        gardener.move(new Point(1, 1));

        assertEquals(2, ((Plot) board.getGrid().get(new Point(1, 1))).getBamboo().size());
        assertEquals(2, ((Plot) board.getGrid().get(new Point(0, 1))).getBamboo().size());
        assertEquals(2, ((Plot) board.getGrid().get(new Point(1, 0))).getBamboo().size());
    }

    @Test
    public void moveMixedColor() {
        GameBoard board = GameBoard.getInstance();

        HashMap<Point, Cell> grid = new HashMap<>();
        Pond pond = new Pond();
        grid.put(pond.getCoords(), pond);

        Plot plot = new Plot(new Point(1, 1), Enums.Color.GREEN);
        grid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(0, 1), Enums.Color.GREEN);
        grid.put(plot.getCoords(), plot);

        plot = new Plot(new Point(1, 0), Enums.Color.PINK);
        grid.put(plot.getCoords(), plot);

        board.setGrid(grid);
        gardener.setCoords(new Point(0, 1));
        gardener.move(new Point(1, 1));

        assertEquals(2, ((Plot) board.getGrid().get(new Point(1, 1))).getBamboo().size());
        assertEquals(2, ((Plot) board.getGrid().get(new Point(0, 1))).getBamboo().size());
        assertEquals(1, ((Plot) board.getGrid().get(new Point(1, 0))).getBamboo().size());
    }

    private Gardener gardener = Gardener.getInstance();

    @Test
    public void testGetAndSetCoords ()
    {
        Point p1 = gardener.getCoords();
        Point p2 = new Point(p1.x + 1, p1.y + 1);

        gardener.setCoords(p2);

        assertEquals(p2, gardener.getCoords());
    }


}