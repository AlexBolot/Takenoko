package Oka.ai.inventory;

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

import static org.junit.Assert.*;

public class InventoryTest {
    private Inventory inventory;
    GoalHolder goalHolder = new GoalHolder();

    @Before
    public void init(){
        inventory = new Inventory();
        inventory.addGoal(new Goal(3,true));
        inventory.addGoal(new Goal(3,true));
        inventory.addGoal(new Goal(3,false));

        Cleaner.clearAll();
        goalHolder.addGoal(new BambooGoal(3, 3, Enums.Color.GREEN));

    }

    @Test
    public void checkOneGoal ()
    {
        Inventory i = new Inventory();
        BambooGoal bambooGoal = new BambooGoal(3, 3, Enums.Color.GREEN);
        i.addGoal(bambooGoal);

        i.addBamboo(Enums.Color.GREEN);
        i.addBamboo(Enums.Color.GREEN);
        i.addBamboo(Enums.Color.GREEN);

        assertEquals(Collections.singletonList(bambooGoal), i.checkGoals());

    }

    @Test
    public void checkMultipleGoals ()
    {
        Inventory i = new Inventory();
        BambooGoal bambooGoal = new BambooGoal(3, 3, Enums.Color.GREEN);
        GardenerGoal gardenerGoal = new GardenerGoal(3, 3, Enums.Color.GREEN, new NeutralState());

        i.addGoal(bambooGoal);
        i.addGoal(gardenerGoal);

        i.addBamboo(Enums.Color.GREEN);
        i.addBamboo(Enums.Color.GREEN);
        i.addBamboo(Enums.Color.GREEN);

        GameBoard board = GameBoard.getInstance();
        Plot plot = new Plot(new Point(1, 0), Enums.Color.GREEN);
        plot.addBamboo();
        plot.addBamboo();

        //fixed : adding only 2 bamboos because plot already has one (when placed)

        board.addCell(plot);

        Set<Goal> expected = new HashSet<>(Arrays.asList(gardenerGoal, bambooGoal));

        Set<Goal> actual = new HashSet<>(i.checkGoals());

        assertEquals(expected, actual);

    }
    @Test
    public void getValueOfGoalHolder() throws Exception {
        assertEquals(6,inventory.getValueOfGoalHolder());
    }

}