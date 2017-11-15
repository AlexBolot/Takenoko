package Oka.model.goal;

import Oka.ai.AISimple;
import Oka.model.Enums;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static Oka.model.Enums.Color.GREEN;
import static Oka.model.Enums.Color.PINK;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BambooGoalTest
{
    private BambooGoal bg1;
    private BambooGoal bg2;
    private BambooGoal bg3;
    private BambooGoal bg4;

    @Before
    public void setUp () throws Exception
    {
        bg1 = new BambooGoal(3, 1, GREEN);
        bg2 = new BambooGoal(3, 2, PINK);
        bg3 = new BambooGoal(3, 3, GREEN);
        bg4 = new BambooGoal(3, 3, GREEN);
    }

    @Test
    public void validateRIGHT () throws Exception
    {
        AISimple ai = new AISimple("Ma");

        ai.getInventory().addBamboo(GREEN);
        ai.getInventory().addBamboo(GREEN);
        ai.getInventory().addBamboo(GREEN);
        ai.getInventory().addBamboo(GREEN);

        bg1.validate(ai.getInventory().bambooHolder());
        assertTrue(bg1.isValidated());

        bg2.validate(ai.getInventory().bambooHolder());
        assertFalse(bg2.isValidated());

        bg3.validate(ai.getInventory().bambooHolder());
        assertTrue(bg3.isValidated());

        bg4.validate(ai.getInventory().bambooHolder());
        assertFalse(bg4.isValidated());
    }

    @Test (expected = NegativeArraySizeException.class)
    public void validateBorderNegative ()
    {
        AISimple nAI = new AISimple("normal");

        bg2 = new BambooGoal(1, -1, PINK);

        bg2.validate(nAI.getInventory().bambooHolder());
    }

    @Test
    public void validateBorder ()
    {
        bg1 = new BambooGoal(1, 1234, GREEN);
        bg3 = new BambooGoal(1, 4, PINK);

        AISimple nAI = new AISimple("normal");
        AISimple emptyAi = new AISimple("empty");
        AISimple fullAi = new AISimple("Full");
        Enums.Color[] colors = new Enums.Color[3];
        colors[0] = GREEN;
        colors[1] = PINK;
        colors[2] = PINK;

        Random random = new Random();

        fullAi.getInventory().addBamboo(PINK);
        fullAi.getInventory().addBamboo(GREEN);
        fullAi.getInventory().addBamboo(PINK);

        for (int i = 0; i < 1000; i++)
        {
            fullAi.getInventory().addBamboo(colors[random.nextInt(colors.length)]);
        }

        bg3.validate(fullAi.getInventory().bambooHolder());
        assertTrue(bg3.isValidated());

        bg1.validate(nAI.getInventory().bambooHolder());
        assertFalse(bg1.isValidated());

        bg1.validate(fullAi.getInventory().bambooHolder());
        assertFalse(bg1.isValidated());

    }

    @Test (expected = NullPointerException.class)
    public void validateNull ()
    {
        bg1 = new BambooGoal(1, 1, GREEN);
        bg1.validate(null);
    }
}