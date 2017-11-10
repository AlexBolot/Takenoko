package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Enums;
import Oka.model.Vector;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.*;

public class PlotGoalTest {
    @Test
    public void validate() throws Exception {
        Cleaner.clearAll();

        PlotGoal pg1 = new PlotGoal(3, Enums.Color.PINK);
        PlotGoal pg2 = new PlotGoal(3, Enums.Color.PINK);
        PlotGoal pg3 = new PlotGoal(3, Enums.Color.PINK);

        HashMap<Vector, PlotGoal> plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pg1);
        plots.put(new Vector(Enums.Axis.x, 1), pg2);
        plots.put(new Vector(Enums.Axis.z, 1), pg3);

        PlotGoal pg4 = new PlotGoal(3, Enums.Color.PINK, plots);

        GameBoard board = GameBoard.getInstance();

        assertFalse(pg1.validate(Optional.empty()));
        assertFalse(pg2.validate(Optional.empty()));
        assertFalse(pg3.validate(Optional.empty()));
        assertFalse(pg4.validate(Optional.empty()));

        board.addCell(new Plot(new Point(0, 1), Enums.Color.PINK));
        assertTrue(pg1.validate(Optional.empty()));
        assertTrue(pg2.validate(Optional.empty()));
        assertTrue(pg3.validate(Optional.empty()));

        board.addCell(new Plot(new Point(1, 0), Enums.Color.PINK));
        board.addCell(new Plot(new Point(1, 1), Enums.Color.PINK));
        board.addCell(new Plot(new Point(0, 2), Enums.Color.PINK));
        assertTrue(pg4.validate(Optional.empty()));

    }


}