package Oka.controler;

import Oka.ai.AISimple;
import Oka.ai.Playable;
import Oka.model.goal.Goal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameControllerTest {
    private AISimple p1;
    private AISimple p2;
    private ArrayList<AISimple> listPlayable;
    private GameController gameController;

    @Before
    public void init(){
        gameController = new GameController();

        p1 = new AISimple();
        p1.getInventory().addGoal(new Goal(3,true));
        p1.getInventory().addGoal(new Goal(3,false));
        p1.getInventory().addGoal(new Goal(3,true));

        p2 = new AISimple();
        p2.getInventory().addGoal(new Goal(3,true));
        p2.getInventory().addGoal(new Goal(3,false));
        p2.getInventory().addGoal(new Goal(2,true));

        listPlayable = new ArrayList<>();
        listPlayable.add(p1);
        listPlayable.add(p2);
    }
    @After
    public void after(){
        gameController = new GameController();
    }
    @Test
    public void maxValuesObjectives() throws Exception {
        assertEquals(p1, gameController.maxValuesObjectives(listPlayable));
    }

}