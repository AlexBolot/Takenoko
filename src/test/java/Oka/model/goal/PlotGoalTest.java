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
import java.util.Optional;

import static Oka.model.Enums.Axis.x;
import static org.junit.Assert.*;

public class PlotGoalTest {
    private PlotGoal pgGreen;
    private PlotGoal pgPink;
    private PlotGoal pgYellow;

    @Before
    public void setUp() throws Exception {


        pgGreen = new PlotGoal(0, Enums.Color.GREEN);
        pgPink = new PlotGoal(0, Enums.Color.PINK);
        pgYellow = new PlotGoal(0, Enums.Color.YELLOW);

    }

    @Test
    public void rotatedTest() {
        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(Enums.Axis.y, 1), pgPink);
        PlotGoal original = new PlotGoal(0, Enums.Color.YELLOW, plots);
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.z, -1), original);
        original = new PlotGoal(0, Enums.Color.GREEN, plots);

        plots = new HashMap<>();

        plots.put(new Vector(x, 1), pgPink);
        PlotGoal expected = new PlotGoal(0, Enums.Color.YELLOW, plots);
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), expected);
        expected = new PlotGoal(0, Enums.Color.GREEN, plots);
        assertEquals(expected, original.rotated());


    }

    @Test
    public void symetricsGeneration() {

        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgPink);
        PlotGoal original = new PlotGoal(0, Enums.Color.YELLOW, plots);

        ArrayList<PlotGoal> symetrics = new ArrayList<>();

        plots = new HashMap<>();
        plots.put(new Vector(x, 1), pgPink);
        PlotGoal symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.z, 1), pgPink);
        symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, -1), pgPink);
        symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(x, -1), pgPink);
        symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.z, -1), pgPink);
        symetric = new PlotGoal(0, Enums.Color.YELLOW, plots);
        symetrics.add(symetric);

        assertTrue(original.symmetrics().stream().allMatch(symetrics::contains));
    }

    @Test
    public void validateSimetry() {
        Cleaner.clearAll();

        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(Enums.Axis.y, 1), pgGreen);
        plots.put(new Vector(Enums.Axis.z, -1), pgGreen);
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
        plots.put(new Vector(Enums.Axis.y, 1), pgPink);
        plots.put(new Vector(x, 1), pgPink);
        plots.put(new Vector(Enums.Axis.z, 1), pgPink);

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
        subGoals.put(new Vector(Enums.Axis.y, 1), pgPink);
        subGoals.put(new Vector(Enums.Axis.z, 1), pgYellow);
        PlotGoal pgCenter = new PlotGoal(3, Enums.Color.PINK, subGoals);
        subGoals = new HashMap<>();
        subGoals.put(new Vector(x, 1), pgGreen);
        subGoals.put(new Vector(Enums.Axis.y, 1), pgPink);
        subGoals.put(new Vector(Enums.Axis.z, 1), pgYellow);
        PlotGoal pgSame = new PlotGoal(3, Enums.Color.PINK, subGoals);
        assertTrue(pgSame.equals(pgCenter));
        assertTrue(pgCenter.equals(pgSame));
    }

}