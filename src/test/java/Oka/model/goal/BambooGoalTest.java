package Oka.model.goal;

import Oka.ai.AI;
import Oka.model.Bamboo;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BambooGoalTest {
    @Test
    public void validateRIGHT() throws Exception {

        BambooGoal bg1 = new BambooGoal(3, 1, Color.green);
        BambooGoal bg2 = new BambooGoal(3, 2, Color.pink);
        BambooGoal bg3 = new BambooGoal(3, 3, Color.green);

        AI ai = new AI("Ma");

        ArrayList<Bamboo> bamboos = new ArrayList<Bamboo>();
        ai.addBamboo(new Bamboo(Color.green));
        ai.addBamboo(new Bamboo(Color.green));

        assertTrue(bg1.validate(ai));
        assertFalse(bg2.validate(ai));
        assertFalse(bg3.validate(ai));
    }

    @Test
    public void validateBorder() {
        BambooGoal bg1 = new BambooGoal(1, 1234, Color.green);
        BambooGoal bg2 = new BambooGoal(1, -1, Color.pink);
        BambooGoal bg3 = new BambooGoal(1, 4, Color.red);

        AI nAI = new AI("normal");
        AI emptyAi = new AI("empty");
        AI fullAi = new AI("Full");
        Color[] colors = new Color[3];
        colors[0] = Color.green;
        colors[1] = Color.pink;
        colors[2] = Color.red;

        Random random = new Random();

        fullAi.addBamboo(new Bamboo(Color.pink));
        fullAi.addBamboo(new Bamboo(Color.green));
        fullAi.addBamboo(new Bamboo(Color.red));

        for (int i = 0; i < 1000; i++) {
            fullAi.addBamboo(
                    new Bamboo(colors[random.nextInt(colors.length)])
            );
        }

        assertTrue(bg2.validate(nAI));
        assertTrue(bg3.validate(fullAi));
        assertTrue(bg2.validate(emptyAi));
        assertFalse(bg1.validate(nAI));
        assertFalse(bg1.validate(fullAi));

    }

    @Test(expected = NullPointerException.class)
    public void validateNull() {

        BambooGoal b = new BambooGoal(1, 1, Color.green);
        b.validate(null);
    }
}