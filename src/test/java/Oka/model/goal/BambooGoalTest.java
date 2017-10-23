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

        BambooGoal bg1 = new BambooGoal(3,1, Color.green);
        BambooGoal bg2 = new BambooGoal(3,2, Color.pink);
        BambooGoal bg3 = new BambooGoal(3,3, Color.green);

        AI AI = new AI("Ma");

        ArrayList<Bamboo> bamboos = new ArrayList<Bamboo>();
        AI.addBamboo(new Bamboo(Color.green));
        AI.addBamboo(new Bamboo(Color.green));



        assertTrue(bg1.validate(AI));
        assertFalse(bg2.validate(AI));
        assertFalse(bg3.validate(AI));
    }

}