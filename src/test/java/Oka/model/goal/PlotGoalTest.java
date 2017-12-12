package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Enums;
import Oka.model.Vector;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static Oka.model.Enums.Axis.*;
import static Oka.model.Enums.Color.*;
import static org.junit.Assert.*;

public class PlotGoalTest
{


    private PlotGoal pgGreen;
    private PlotGoal pgPink;
    private PlotGoal pgYellow;

    @Before
    public void setUp() {
        Cleaner.clearAll();

        pgGreen = new PlotGoal(0, GREEN);
        pgPink = new PlotGoal(0, PINK);
        pgYellow = new PlotGoal(0, YELLOW);

    }

    @Test
    public void neededSpots ()
    {
        GameBoard board = GameBoard.getInstance();

        ArrayList<Map.Entry<Enums.Color, Point>> neededSpots = new ArrayList<>(pgGreen.neededSpots());
        List<Point> greenNeededPoints = neededSpots.stream().filter(entry -> entry.getKey().equals(GREEN)).map(Map.Entry::getValue).collect(
                Collectors.toList());

        assertEquals(board.getAvailableSlots(), greenNeededPoints);
        board.addCell(new Plot(new Point(0, 1), GREEN));
        board.addCell(new Plot(new Point(1, 0), GREEN));
        board.addCell(new Plot(new Point(1, 1), PINK));
        board.addCell(new Plot(new Point(2, 0), GREEN));

        ArrayList<Map.Entry<Enums.Color, Point>> expected = new ArrayList<>();

        expected.add(new AbstractMap.SimpleEntry<Enums.Color, Point>(GREEN, new Point(0, 2)));
        expected.add(new AbstractMap.SimpleEntry<Enums.Color, Point>(GREEN, new Point(2, 1)));



        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgGreen);
        plots.put(new Vector(x, -1), pgGreen);
        PlotGoal pgGPG = new PlotGoal(3, PINK, plots);

        assertEquals(expected, new ArrayList<>(pgGPG.neededSpots()));

        expected = new ArrayList<>();

        expected.add(new AbstractMap.SimpleEntry<Enums.Color, Point>(PINK, new Point(-1, 1)));
        expected.add(new AbstractMap.SimpleEntry<Enums.Color, Point>(PINK, new Point(1, -1)));


        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgPink);
        plots.put(new Vector(x, -1), pgPink);
        PlotGoal pgPGP = new PlotGoal(3, GREEN, plots);

        assertEquals(expected, new ArrayList<>(pgPGP.neededSpots()));

    }

    @Test
    public void size ()
    {
        assertEquals(1, pgGreen.getSize());
        assertEquals(1, pgPink.getSize());
        assertEquals(1, pgYellow.getSize());
        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(y, 1), pgGreen);
        plots.put(new Vector(x, 1), pgPink);
        plots.put(new Vector(z, 1), pgYellow);
        PlotGoal pgSimple4 = new PlotGoal(3, YELLOW, plots);

        assertEquals(4, pgSimple4.getSize());
        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgPink);
        PlotGoal subGreen = new PlotGoal(3, GREEN, plots);
        plots = new HashMap<>();
        plots.put(new Vector(y, 1), subGreen);
        plots.put(new Vector(z, 1), pgPink);

        PlotGoal pgDeep4 = new PlotGoal(3, GREEN, plots);

        assertEquals(4, pgDeep4.getSize());
    }

    @Test
    public void toCompletion ()
    {
        GameBoard board = GameBoard.getInstance();
        board.addCell(new Plot(new Point(0, 1), YELLOW));
        board.addCell(new Plot(new Point(1, 0), GREEN));
        board.addCell(new Plot(new Point(1, 1), GREEN));

        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(Enums.Axis.x, 1), pgGreen);

        PlotGoal pgYG = new PlotGoal(3, YELLOW, plots);

        assertEquals(0, pgGreen.toCompletion());
        assertEquals(0, pgYG.toCompletion());

        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgYellow);

        PlotGoal pgGY = new PlotGoal(3, GREEN, plots);

        assertEquals(0, pgGY.toCompletion());
        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgGY);

        PlotGoal pgColoredLine = new PlotGoal(3, PINK, plots);
        assertEquals(2, pgColoredLine.toCompletion());

        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgColoredLine);

        PlotGoal pgLongColoredLine = new PlotGoal(3, YELLOW, plots);
        assertEquals(0, pgLongColoredLine.toCompletion());

    }

    @Test
    public void completion() {
        GameBoard board = GameBoard.getInstance();
        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(Enums.Axis.x, 1), pgGreen);
        PlotGoal pgYG = new PlotGoal(3, YELLOW, plots);

        board.addCell(new Plot(new Point(1, 0), GREEN));
        assertEquals(1, pgGreen.completion(new Point(1, 0)));
        assertEquals(-1, pgGreen.completion(new Point()));

        board.addCell(new Plot(new Point(0, 1), YELLOW));
        board.addCell(new Plot(new Point(1, 1), GREEN));

        assertEquals(2, pgYG.completion(new Point(0, 1)));


    }

    @Test
    public void rotatedTest ()
    {
        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(y, 1), pgPink);
        PlotGoal original = new PlotGoal(0, YELLOW, plots);
        plots = new HashMap<>();
        plots.put(new Vector(z, -1), original);
        original = new PlotGoal(0, GREEN, plots);

        plots = new HashMap<>();

        plots.put(new Vector(x, 1), pgPink);
        PlotGoal expected = new PlotGoal(0, YELLOW, plots);
        plots = new HashMap<>();
        plots.put(new Vector(y, 1), expected);
        expected = new PlotGoal(0, GREEN, plots);
        assertEquals(expected, original.rotated());


    }

    @Test
    public void symetricsGeneration ()
    {

        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(y, 1), pgPink);
        PlotGoal original = new PlotGoal(0, YELLOW, plots);

        ArrayList<PlotGoal> symetrics = new ArrayList<>();

        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgPink);
        PlotGoal symetric = new PlotGoal(0, YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(z, 1), pgPink);
        symetric = new PlotGoal(0, YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(y, -1), pgPink);
        symetric = new PlotGoal(0, YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(x, -1), pgPink);
        symetric = new PlotGoal(0, YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(z, -1), pgPink);
        symetric = new PlotGoal(0, YELLOW, plots);
        symetrics.add(symetric);

        assertTrue(original.symmetrics().stream().allMatch(symetrics::contains));
    }

    @Test
    public void validateSimetry ()
    {
        Cleaner.clearAll();

        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(y, 1), pgGreen);
        plots.put(new Vector(z, -1), pgGreen);
        plots.put(new Vector(x, -1), pgPink);

        PlotGoal pg3 = new PlotGoal(3, PINK, plots);

        assertFalse(pg3.validate(Optional.empty()));

        GameBoard board = GameBoard.getInstance();
        board.addCell(new Plot(new Point(-1, 0), GREEN));
        board.addCell(new Plot(new Point(-1, 1), PINK));
        board.addCell(new Plot(new Point(-2, 1), PINK));
        board.addCell(new Plot(new Point(-2, 0), GREEN));

        assertFalse(pg3.validate(Optional.empty()));

        board.addIrrigation(new Point(-1, 0), new Point(-1, 1));
        board.addIrrigation(new Point(-1, 0), new Point(-2, 1));
        board.addIrrigation(new Point(-2, 0), new Point(-2, 1));

        assertTrue(pg3.validate(Optional.empty()));

        Cleaner.clearAll();

        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgPink);

        PlotGoal theOnePG = new PlotGoal(3, GREEN, plots);
        board = GameBoard.getInstance();
        board.addCell(new Plot(new Point(-1, 0), GREEN));
        board.addCell(new Plot(new Point(-1, 1), PINK));
        board.addCell(new Plot(new Point(0, 1), YELLOW));
        assertTrue(theOnePG.validate(Optional.empty()));


    }


    @Test
    public void validateSimple() {
        Cleaner.clearAll();

        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(y, 1), pgPink);
        plots.put(new Vector(x, 1), pgPink);
        plots.put(new Vector(z, 1), pgPink);

        PlotGoal pg4 = new PlotGoal(3, PINK, plots);

        GameBoard board = GameBoard.getInstance();

        assertFalse(pgPink.validate(Optional.empty()));
        assertFalse(pgPink.validate(Optional.empty()));
        assertFalse(pgPink.validate(Optional.empty()));
        assertFalse(pg4.validate(Optional.empty()));

        board.addCell(new Plot(new Point(0, 1), PINK));
        assertTrue(pgPink.validate(Optional.empty()));
        assertTrue(pgPink.isValidated());
        assertTrue(pgPink.validate(Optional.empty()));
        assertTrue(pgPink.isValidated());
        assertTrue(pgPink.validate(Optional.empty()));
        assertTrue(pgPink.isValidated());

        board.addCell(new Plot(new Point(1, 0), PINK));
        board.addCell(new Plot(new Point(1, 1), PINK));
        board.addCell(new Plot(new Point(0, 2), PINK));
        assertFalse(pg4.validate(Optional.empty()));

        board.addIrrigation(new Point(1, 0), new Point(0, 1));
        board.addIrrigation(new Point(1, 1), new Point(0, 1));
        board.addIrrigation(new Point(1, 1), new Point(0, 2));
        assertTrue(pg4.validate(Optional.empty()));

    }

    @Test
    public void equalsTest ()
    {

        HashMap<Vector, PlotGoal> subGoals = new HashMap<>();
        subGoals.put(new Vector(x, 1), pgGreen);
        subGoals.put(new Vector(y, 1), pgPink);
        subGoals.put(new Vector(z, 1), pgYellow);
        PlotGoal pgCenter = new PlotGoal(3, PINK, subGoals);
        subGoals = new HashMap<>();
        subGoals.put(new Vector(x, 1), pgGreen);
        subGoals.put(new Vector(y, 1), pgPink);
        subGoals.put(new Vector(z, 1), pgYellow);
        PlotGoal pgSame = new PlotGoal(3, PINK, subGoals);
        assertTrue(pgSame.equals(pgCenter));
        assertTrue(pgCenter.equals(pgSame));
    }

}