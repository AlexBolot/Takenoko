package Oka.controler;

import Oka.ai.AI;
import Oka.ai.AIRandom;
import Oka.ai.AISimple;
import Oka.ai.Playable;
import Oka.ai.inventory.GoalHolder;
import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.state.NeutralState;
import Oka.utils.Cleaner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameControllerTest
{
    private AISimple       p1;
    private AISimple       p2;
    private ArrayList<Playable>  listPlayable;
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
        assertEquals(4, gameController.getAIWins(listPlayable).size());
    }

    @Test
    public void winnerEmperorGoal ()
    {
        GameController gc = GameController.getInstance();

        AISimple AM = new AISimple("AISimple1");
        // AISimple IL = new AISimple("AISimple2");
        AIRandom IL = new AIRandom("AIRandom");

        ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL));

        gc.play(Playable);

        GoalHolder AMGoalHolder = AM.getInventory().goalHolder();

        int emmperorC = (int) AMGoalHolder.stream().filter(goal -> goal.getClass().equals(Goal.class)).count();
        GoalHolder ILGoalHolder = IL.getInventory().goalHolder();

        emmperorC += ILGoalHolder.stream().filter(goal -> goal.getClass().equals(Goal.class)).count();
        assertEquals(1, emmperorC);
    }
    @Test
    public void lastTurn() {
        //need fix see OKA-134
        Cleaner.clearAll();
        GameController gc = GameController.getInstance();

        AISimple AM = new AISimple("AISimple1");
        // AISimple IL = new AISimple("AISimple2");
        AIRandom IL = new AIRandom("AIRandom");
        AIRandom sm = new AIRandom("Mosseb");

        ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL, sm));

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

    @Test
    public void getAiWin() {
        Cleaner.clearAll();
        GameController gc = GameController.getInstance();

        AISimple am = new AISimple("AISimple1");
        // AISimple IL = new AISimple("AISimple2");
        AIRandom IL = new AIRandom("AIRandom");
        AIRandom sm = new AIRandom("Mosseb");

        ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(am, IL, sm));
        BambooGoal bg1 = new BambooGoal(1, 3, Enums.Color.YELLOW);
        bg1.setValidated(true);
        GardenerGoal gg2 = new GardenerGoal(2, 3, Enums.Color.GREEN, new NeutralState());
        gg2.setValidated(true);
        am.getInventory().goalHolder().addGoal(bg1);
        am.getInventory().goalHolder().addGoal(gg2);


        BambooGoal bg3 = new BambooGoal(3, 3, Enums.Color.GREEN);
        bg3.setValidated(true);
        sm.getInventory().goalHolder().addGoal(bg3);
        assertEquals(1, gc.getAIWins(Playable).size());
        assertEquals(sm, gc.getAIWins(Playable).get(0));

        IL.getInventory().goalHolder().addGoal(bg3);
        assertEquals(2, gc.getAIWins(Playable).size());
        assertTrue(gc.getAIWins(Playable).containsAll(Arrays.asList(IL,sm)));

    }
}