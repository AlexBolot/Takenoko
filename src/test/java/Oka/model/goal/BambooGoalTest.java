package Oka.model.goal;

import Oka.ai.AI;
import Oka.model.Bamboo;
import Oka.model.Enums;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BambooGoalTest {
    @Test
    public void validateRIGHT() throws Exception {

        BambooGoal bg1 = new BambooGoal(3, 1, Enums.Color.GREEN);
        BambooGoal bg2 = new BambooGoal(3, 2, Enums.Color.PINK);
        BambooGoal bg3 = new BambooGoal(3, 3, Enums.Color.GREEN);
        BambooGoal bg4 = new BambooGoal(3, 3, Enums.Color.GREEN);

        AI ai = new AI("Ma");
        ai.addBamboo(Enums.Color.GREEN);
        ai.addBamboo(Enums.Color.GREEN);

        assertTrue(bg1.validate(ai.getBamboos()));
        assertFalse(bg2.validate(ai.getBamboos()));
        assertTrue(bg3.validate(ai.getBamboos()));
        assertFalse(bg4.validate(ai.getBamboos()));
    }

    @Test
    public void validateBorder() {
        BambooGoal bg1 = new BambooGoal(1, 1234, Enums.Color.GREEN);
        BambooGoal bg2 = new BambooGoal(1, -1, Enums.Color.PINK);
        BambooGoal bg3 = new BambooGoal(1, 4, Enums.Color.PINK);

        AI nAI = new AI("normal");
        AI emptyAi = new AI("empty");
        AI fullAi = new AI("Full");
        Enums.Color[] colors = new Enums.Color[3];
        colors[0] = Enums.Color.GREEN;
        colors[1] = Enums.Color.PINK;
        colors[2] = Enums.Color.PINK;

        Random random = new Random();

        fullAi.addBamboo(Enums.Color.PINK);
        fullAi.addBamboo(Enums.Color.GREEN);
        fullAi.addBamboo(Enums.Color.PINK);

        for (int i = 0; i < 1000; i++) {
            fullAi.addBamboo(colors[random.nextInt(colors.length)]);
        }

        assertTrue(bg2.validate(nAI.getBamboos()));
        assertTrue(bg3.validate(fullAi.getBamboos()));
        assertTrue(bg2.validate(emptyAi.getBamboos()));
        assertFalse(bg1.validate(nAI.getBamboos()));
        assertFalse(bg1.validate(fullAi.getBamboos()));

    }

    @Test(expected = NullPointerException.class)
    public void validateNull() {

        BambooGoal b = new BambooGoal(1, 1, Enums.Color.GREEN);
        b.validate(null);
    }
}