package Oka.ai.inventory;

import Oka.controler.GameBoard;
import Oka.model.Bamboo;
import Oka.model.Enums;
import Oka.model.Vector;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.PlotGoal;
import Oka.model.plot.Plot;
import Oka.model.plot.state.NeutralState;
import Oka.utils.Cleaner;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GoalHolderTest {
    private GoalHolder goalHolder;
    private BambooHolder bambooholder;
    @Before
    public void init(){
        Cleaner.clearAll();

        goalHolder = new GoalHolder();
        goalHolder.add(new BambooGoal(5,1, Enums.Color.GREEN));
        goalHolder.add(new BambooGoal(5,2, Enums.Color.PINK));
        goalHolder.add(new BambooGoal(5,3, Enums.Color.YELLOW));

        goalHolder.add(new GardenerGoal(5, 2, Enums.Color.GREEN, new NeutralState()));

        HashMap<Vector, PlotGoal> subGoals = new HashMap<>();
        subGoals.put(new Vector(Enums.Axis.x, 1), new PlotGoal(0, Enums.Color.GREEN));

        goalHolder.add(new PlotGoal(1, Enums.Color.GREEN, subGoals));

        bambooholder = new BambooHolder();
        bambooholder.add(new Bamboo(Enums.Color.GREEN));
        bambooholder.add(new Bamboo(Enums.Color.GREEN));
        bambooholder.add(new Bamboo(Enums.Color.GREEN));
        bambooholder.add(new Bamboo(Enums.Color.PINK));
        bambooholder.add(new Bamboo(Enums.Color.PINK));
        bambooholder.add(new Bamboo(Enums.Color.YELLOW));
    }

    @Test
    public void checkGoal() {

        goalHolder.checkGoal(bambooholder);
        assertEquals(2,goalHolder.getGoalValidated(true).size());
        GameBoard board = GameBoard.getInstance();
        board.addCell(new Plot(new Point(1, 0), Enums.Color.GREEN));
        board.addCell(new Plot(new Point(0, 1), Enums.Color.GREEN));
        board.addCell(new Plot(new Point(1, 1), Enums.Color.GREEN));
        board.addIrrigation(new Point(0, 1), new Point(1, 0));
        board.addIrrigation(new Point(1, 1), new Point(1, 0));
        goalHolder.checkGoal(bambooholder);
        assertEquals(4, goalHolder.getGoalValidated(true).size());

    }

    @Test
    public void addGoal() {
        assertEquals(goalHolder.get(0),new BambooGoal(5,1,Enums.Color.GREEN));
        assertEquals(goalHolder.get(1),new BambooGoal(5,2,Enums.Color.PINK));
        assertEquals(goalHolder.get(2),new BambooGoal(5,3,Enums.Color.YELLOW));


    }

    @Test
    public void maxGoal() {
        Cleaner.clearAll();
        GoalHolder gh = new GoalHolder();
        BambooGoal firstGoal = new BambooGoal(3, 1, Enums.Color.GREEN);
        gh.addGoal(firstGoal);
        gh.addGoal(new BambooGoal(3, 1, Enums.Color.GREEN));
        gh.addGoal(new BambooGoal(3, 1, Enums.Color.GREEN));
        gh.addGoal(new BambooGoal(3, 1, Enums.Color.GREEN));
        gh.addGoal(new BambooGoal(3, 1, Enums.Color.GREEN));
        BambooGoal lastGoal = new BambooGoal(3, 2, Enums.Color.GREEN);
        gh.addGoal(lastGoal);
        assertEquals(5, gh.size());
        assertFalse(gh.contains(lastGoal));

    }

}