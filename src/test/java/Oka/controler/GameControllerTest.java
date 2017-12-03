package Oka.controler;

import Oka.ai.AI;
import Oka.ai.AIRandom;
import Oka.ai.AISimple;
import Oka.ai.inventory.GoalHolder;
import Oka.model.goal.Goal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class GameControllerTest
{
    private AISimple       p1;
    private AISimple       p2;
    private ArrayList<AI>  listPlayable;
    private GameController gameController;

    @Before
    public void init ()
    {
        gameController = new GameController();

        p1 = new AISimple();
        p1.getInventory().addGoal(new Goal(3, true));
        p1.getInventory().addGoal(new Goal(3, false));
        p1.getInventory().addGoal(new Goal(3, true));

        p2 = new AISimple();
        p2.getInventory().addGoal(new Goal(3, true));
        p2.getInventory().addGoal(new Goal(3, false));
        p2.getInventory().addGoal(new Goal(2, true));

        listPlayable = new ArrayList<>();
        listPlayable.add(p1);
        listPlayable.add(p2);
    }

    @After
    public void after ()
    {
        gameController = new GameController();
    }

    @Test
    public void maxValuesObjectives() {
        assertEquals(p1, gameController.getAIWins(listPlayable).get(0));
        p2.getInventory().addGoal(new Goal(1, true));
        assertEquals(2, gameController.getAIWins(listPlayable).size());
    }

    @Test
    public void winnerEmperorGoal ()
    {
        GameController gc = GameController.getInstance();

        AISimple AM = new AISimple("AISimple1");
        // AISimple IL = new AISimple("AISimple2");
        AIRandom IL = new AIRandom("AIRandom");

        ArrayList<AI> Playable = new ArrayList<>(Arrays.asList(AM, IL));

        gc.play(Playable);

        GoalHolder AMGoalHolder = AM.getInventory().goalHolder();

        int emmperorC = (int) AMGoalHolder.stream().filter(goal -> goal.getClass().equals(Goal.class)).count();
        GoalHolder ILGoalHolder = IL.getInventory().goalHolder();

        emmperorC += ILGoalHolder.stream().filter(goal -> goal.getClass().equals(Goal.class)).count();
        assertEquals(1, emmperorC);
    }

    @Test
    public void lastTurn() {
        GameController gc = GameController.getInstance();

        AISimple AM = new AISimple("AISimple1");
        // AISimple IL = new AISimple("AISimple2");
        AIRandom IL = new AIRandom("AIRandom");
        AIRandom sm = new AIRandom("Mosseb");

        ArrayList<AI> Playable = new ArrayList<>(Arrays.asList(AM, IL, sm));

        gc.lastTurn(Playable, AM);
        assertEquals(0, IL.getInventory().getActionHolder().getActionLeft());
        assertEquals(0, sm.getInventory().getActionHolder().getActionLeft());
        assertEquals(2, AM.getInventory().getActionHolder().getActionLeft());

        IL.getInventory().resetActionHolder();
        sm.getInventory().resetActionHolder();
        gc.lastTurn(Playable, sm);
        assertEquals(0, IL.getInventory().getActionHolder().getActionLeft());
        assertEquals(2, sm.getInventory().getActionHolder().getActionLeft());
        assertEquals(0, AM.getInventory().getActionHolder().getActionLeft());
    }

}