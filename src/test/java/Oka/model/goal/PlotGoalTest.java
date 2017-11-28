package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Enums;
import Oka.model.Vector;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static Oka.model.Enums.Axis.*;
import static org.junit.Assert.*;

public class PlotGoalTest {


    private PlotGoal pgGreen;
    private PlotGoal pgPink;
    private PlotGoal pgYellow;

    @Before
    public void setUp() throws Exception {
        Cleaner.clearAll();

        pgGreen = new PlotGoal(0, Enums.Color.GREEN);
        pgPink = new PlotGoal(0, Enums.Color.PINK);
        pgYellow = new PlotGoal(0, Enums.Color.YELLOW);

    }

    @Test
    public void neededSpots() {
        GameBoard board = GameBoard.getInstance();
        assertEquals(new HashSet<>(board.getAvailableSlots()), pgGreen.neededSpots().get(Enums.Color.GREEN));
        board.addCell(new Plot(new Point(0, 1), Enums.Color.GREEN));
        board.addCell(new Plot(new Point(1, 0), Enums.Color.GREEN));
        board.addCell(new Plot(new Point(1, 1), Enums.Color.PINK));
        board.addCell(new Plot(new Point(2, 0), Enums.Color.GREEN));

        HashMap<Enums.Color, HashSet<Point>> expected = new HashMap<>();

        HashSet<Point> points = new HashSet<>();
        points.add(new Point(2, 1));
        points.add(new Point(0, 2));

        expected.put(Enums.Color.GREEN, points);
        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgGreen);
        plots.put(new Vector(x, -1), pgGreen);
        PlotGoal pgGPG = new PlotGoal(3, Enums.Color.PINK, plots);

        assertEquals(expected, pgGPG.neededSpots());
        expected = new HashMap<>();

        points = new HashSet<>();
        points.add(new Point(-1, 1));
        points.add(new Point(1, -1));
        points.add(new Point(2, -1));
        points.add(new Point(2, 1));


        expected.put(Enums.Color.PINK, points);
        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgPink);
        plots.put(new Vector(x, -1), pgPink);
        PlotGoal pgPGP = new PlotGoal(3, Enums.Color.GREEN, plots);

        assertEquals(expected, pgPGP.neededSpots());

    }
    @Test
    public void size() {
        assertEquals(1, pgGreen.getSize());
        assertEquals(1, pgPink.getSize());
        assertEquals(1, pgYellow.getSize());
        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(y, 1), pgGreen);
        plots.put(new Vector(x, 1), pgPink);
        plots.put(new Vector(z, 1), pgYellow);
        PlotGoal pgSimple4 = new PlotGoal(3, Enums.Color.YELLOW, plots);

        assertEquals(4, pgSimple4.getSize());
        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgPink);
        PlotGoal subGreen = new PlotGoal(3, Enums.Color.GREEN, plots);
        plots = new HashMap<>();
        plots.put(new Vector(y, 1), subGreen);
        plots.put(new Vector(z, 1), pgPink);

        PlotGoal pgDeep4 = new PlotGoal(3, Enums.Color.GREEN, plots);

        assertEquals(4, pgDeep4.getSize());
    }

    @Test
    public void toCompletion() {
        GameBoard board = GameBoard.getInstance();
        board.addCell(new Plot(new Point(0, 1), Enums.Color.YELLOW));
        board.addCell(new Plot(new Point(1, 0), Enums.Color.GREEN));
        board.addCell(new Plot(new Point(1, 1), Enums.Color.GREEN));

        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(Enums.Axis.x, 1), pgGreen);

        PlotGoal pgYG = new PlotGoal(3, Enums.Color.YELLOW, plots);

        assertEquals(0, pgGreen.toCompletion());
        assertEquals(0, pgYG.toCompletion());

        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgYellow);

        PlotGoal pgGY = new PlotGoal(3, Enums.Color.GREEN, plots);

        assertEquals(0, pgGY.toCompletion());
        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgGY);

        PlotGoal pgColoredLine = new PlotGoal(3, Enums.Color.PINK, plots);
        assertEquals(2, pgColoredLine.toCompletion());

        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgColoredLine);

        PlotGoal pgLongColoredLine = new PlotGoal(3, Enums.Color.YELLOW, plots);
        assertEquals(0, pgLongColoredLine.toCompletion());

    }

    @Test
    public void completion() throws Exception {
        GameBoard board = GameBoard.getInstance();
        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(Enums.Axis.x, 1), pgGreen);
        PlotGoal pgYG = new PlotGoal(3, Enums.Color.YELLOW, plots);

        board.addCell(new Plot(new Point(1, 0), Enums.Color.GREEN));
        assertEquals(1, pgGreen.completion(new Point(1, 0)));
        assertEquals(-1, pgGreen.completion(new Point()));

        board.addCell(new Plot(new Point(0, 1), Enums.Color.YELLOW));
        board.addCell(new Plot(new Point(1, 1), Enums.Color.GREEN));

        assertEquals(2, pgYG.completion(new Point(0, 1)));


    }

    @Test
    public void rotatedTest() {
        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(y, 1), pgPink);
        PlotGoal original = new PlotGoal(0, Enums.Color.YELLOW, plots);
        plots = new HashMap<>();
        plots.put(new Vector(z, -1), original);
        original = new PlotGoal(0, Enums.Color.GREEN, plots);

        plots = new HashMap<>();

        plots.put(new Vector(x, 1), pgPink);
        PlotGoal expected = new PlotGoal(0, Enums.Color.YELLOW, plots);
        plots = new HashMap<>();
        plots.put(new Vector(y, 1), expected);
        expected = new PlotGoal(0, Enums.Color.GREEN, plots);
        assertEquals(expected, original.rotated());


    }

    @Test
    public void symetricsGeneration() {

        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(y, 1), pgPink);
        PlotGoal original = new PlotGoal(0, Enums.Color.YELLOW, plots);

        ArrayList<PlotGoal> symetrics = new ArrayList<>();

        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgPink);
        PlotGoal symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(z, 1), pgPink);
        symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(y, -1), pgPink);
        symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(x, -1), pgPink);
        symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(z, -1), pgPink);
        symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        assertTrue(original.symmetrics().stream().allMatch(symetrics::contains));
    }

    @Test
    public void validateSimetry() {
        Cleaner.clearAll();

        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(y, 1), pgGreen);
        plots.put(new Vector(z, -1), pgGreen);
        plots.put(new Vector(x, -1), pgPink);

        PlotGoal pg3 = new PlotGoal(3, Enums.Color.PINK, plots);

        assertFalse(pg3.validate(Optional.empty()));

        GameBoard board = GameBoard.getInstance();
        board.addCell(new Plot(new Point(-1, 0), Enums.Color.GREEN));
        board.addCell(new Plot(new Point(-1, 1), Enums.Color.PINK));
        board.addCell(new Plot(new Point(-2, 1), Enums.Color.PINK));
        board.addCell(new Plot(new Point(-2, 0), Enums.Color.GREEN));

        assertFalse(pg3.validate(Optional.empty()));

        board.addIrrigation(new Point(-1, 0), new Point(-1, 1));
        board.addIrrigation(new Point(-1, 0), new Point(-2, 1));
        board.addIrrigation(new Point(-2, 0), new Point(-2, 1));

        assertTrue(pg3.validate(Optional.empty()));

        Cleaner.clearAll();

        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgPink);

        PlotGoal theOnePG = new PlotGoal(3, Enums.Color.GREEN, plots);
        board = GameBoard.getInstance();
        board.addCell(new Plot(new Point(-1, 0), Enums.Color.GREEN));
        board.addCell(new Plot(new Point(-1, 1), Enums.Color.PINK));
        board.addCell(new Plot(new Point(0, 1), Enums.Color.YELLOW));
        assertTrue(theOnePG.validate(Optional.empty()));


    }


    @Test
    public void validateSimple() throws Exception {
        Cleaner.clearAll();

        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(y, 1), pgPink);
        plots.put(new Vector(x, 1), pgPink);
        plots.put(new Vector(z, 1), pgPink);

        PlotGoal pg4 = new PlotGoal(3, Enums.Color.PINK, plots);

        GameBoard board = GameBoard.getInstance();

        assertFalse(pgPink.validate(Optional.empty()));
        assertFalse(pgPink.validate(Optional.empty()));
        assertFalse(pgPink.validate(Optional.empty()));
        assertFalse(pg4.validate(Optional.empty()));

        board.addCell(new Plot(new Point(0, 1), Enums.Color.PINK));
        assertTrue(pgPink.validate(Optional.empty()));
        assertTrue(pgPink.isValidated());
        assertTrue(pgPink.validate(Optional.empty()));
        assertTrue(pgPink.isValidated());
        assertTrue(pgPink.validate(Optional.empty()));
        assertTrue(pgPink.isValidated());

        board.addCell(new Plot(new Point(1, 0), Enums.Color.PINK));
        board.addCell(new Plot(new Point(1, 1), Enums.Color.PINK));
        board.addCell(new Plot(new Point(0, 2), Enums.Color.PINK));
        assertFalse(pg4.validate(Optional.empty()));

        board.addIrrigation(new Point(1, 0), new Point(0, 1));
        board.addIrrigation(new Point(1, 1), new Point(0, 1));
        board.addIrrigation(new Point(1, 1), new Point(0, 2));
        assertTrue(pg4.validate(Optional.empty()));

    }

    @Test
    public void equalsTest() {

        HashMap<Vector, PlotGoal> subGoals = new HashMap<>();
        subGoals.put(new Vector(x, 1), pgGreen);
        subGoals.put(new Vector(y, 1), pgPink);
        subGoals.put(new Vector(z, 1), pgYellow);
        PlotGoal pgCenter = new PlotGoal(3, Enums.Color.PINK, subGoals);
        subGoals = new HashMap<>();
        subGoals.put(new Vector(x, 1), pgGreen);
        subGoals.put(new Vector(y, 1), pgPink);
        subGoals.put(new Vector(z, 1), pgYellow);
        PlotGoal pgSame = new PlotGoal(3, Enums.Color.PINK, subGoals);
        assertTrue(pgSame.equals(pgCenter));
        assertTrue(pgCenter.equals(pgSame));
    }

}