package Oka.model.goal;

import Oka.ai.AI;
import Oka.model.Bamboo;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BambooGoalTest {
    @Test
    public void validate() throws Exception {

        BambooGoal bg1 = new BambooGoal(3,1);
        BambooGoal bg2 = new BambooGoal(3,2);
        BambooGoal bg3 = new BambooGoal(3,3);

        ArrayList<Bamboo> bamboos = new ArrayList<Bamboo>();
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));
        ArrayList<Goal> pendinggoal = new ArrayList<Goal>();

        AI AI = new AI();

        assertTrue(bg1.validate(AI));
        assertTrue(bg2.validate(AI));
        assertFalse(bg3.validate(AI));
    }

}