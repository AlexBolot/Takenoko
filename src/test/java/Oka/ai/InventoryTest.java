package Oka.ai;

import Oka.ai.inventory.GoalHolder;
import Oka.ai.inventory.Inventory;
import Oka.controler.GameBoard;
import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;
import Oka.model.plot.state.NeutralState;
import Oka.utils.Cleaner;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class InventoryTest {
    GoalHolder goalHolder = new GoalHolder();

    @Before
    public void setUp() throws Exception {
        Cleaner.clearAll();
        goalHolder.addGoal(new BambooGoal(3, 3, Enums.Color.GREEN));

    }

    @Test
    public void checkOneGoal() throws Exception {
        Inventory i = new Inventory();
        BambooGoal bambooGoal = new BambooGoal(3, 3, Enums.Color.GREEN);
        i.addGoal(bambooGoal);

        i.addBamboo(Enums.Color.GREEN);
        i.addBamboo(Enums.Color.GREEN);
        i.addBamboo(Enums.Color.GREEN);

        assertEquals(Collections.singletonList(bambooGoal), i.checkGoals());

    }


    @Test
    public void checkMultipleGoals() {
        Inventory i = new Inventory();
        BambooGoal bambooGoal = new BambooGoal(3, 3, Enums.Color.GREEN);
        GardenerGoal gardenerGoal = new GardenerGoal(3, 3, Enums.Color.GREEN, new NeutralState());

        i.addGoal(bambooGoal);

        i.addBamboo(Enums.Color.GREEN);
        i.addBamboo(Enums.Color.GREEN);
        i.addBamboo(Enums.Color.GREEN);


        GameBoard board = GameBoard.getInstance();
        Plot plot = new Plot(new Point(1, 0), Enums.Color.GREEN);
        plot.addBamboo();
        plot.addBamboo();
        plot.addBamboo();
        board.addCell(plot);

        i.addGoal(gardenerGoal);

        Set<Goal> expected = new HashSet<>(Arrays.asList(gardenerGoal, bambooGoal));

        Set<Goal> actual = new HashSet<>(i.checkGoals());

        assertEquals(expected, actual);

    }


}