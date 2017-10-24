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

        AI ai = new AI("Ma");

        ArrayList<Bamboo> bamboos = new ArrayList<Bamboo>();
        ai.addBamboo(new Bamboo(Enums.Color.GREEN));
        ai.addBamboo(new Bamboo(Enums.Color.GREEN));

        assertTrue(bg1.validate(ai));
        assertFalse(bg2.validate(ai));
        assertFalse(bg3.validate(ai));
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

        fullAi.addBamboo(new Bamboo(Enums.Color.PINK));
        fullAi.addBamboo(new Bamboo(Enums.Color.GREEN));
        fullAi.addBamboo(new Bamboo(Enums.Color.PINK));

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

        BambooGoal b = new BambooGoal(1, 1, Enums.Color.GREEN);
        b.validate(null);
    }
}