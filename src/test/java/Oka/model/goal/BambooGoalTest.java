package Oka.model.goal;

import Oka.ai.AISimple;
import Oka.model.Enums;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BambooGoalTest
{
    @Test
    public void validateRIGHT () throws Exception
    {
        BambooGoal bg1 = new BambooGoal(3, 1, Enums.Color.GREEN);
        BambooGoal bg2 = new BambooGoal(3, 2, Enums.Color.PINK);
        BambooGoal bg3 = new BambooGoal(3, 3, Enums.Color.GREEN);
        BambooGoal bg4 = new BambooGoal(3, 3, Enums.Color.GREEN);

        AISimple ai = new AISimple("Ma");

        ai.addBamboo(Enums.Color.GREEN);
        ai.addBamboo(Enums.Color.GREEN);

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
        BambooGoal bg2 = new BambooGoal(1, -1, Enums.Color.PINK);
        bg2.validate(nAI.getInventory().bambooHolder());
    }

    @Test
    public void validateBorder ()
    {
        BambooGoal bg1 = new BambooGoal(1, 1234, Enums.Color.GREEN);
        BambooGoal bg3 = new BambooGoal(1, 4, Enums.Color.PINK);

        AISimple nAI = new AISimple("normal");
        AISimple emptyAi = new AISimple("empty");
        AISimple fullAi = new AISimple("Full");
        Enums.Color[] colors = new Enums.Color[3];
        colors[0] = Enums.Color.GREEN;
        colors[1] = Enums.Color.PINK;
        colors[2] = Enums.Color.PINK;

        Random random = new Random();

        fullAi.addBamboo(Enums.Color.PINK);
        fullAi.addBamboo(Enums.Color.GREEN);
        fullAi.addBamboo(Enums.Color.PINK);

        for (int i = 0; i < 1000; i++)
        {
            fullAi.addBamboo(colors[random.nextInt(colors.length)]);
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

        BambooGoal b = new BambooGoal(1, 1, Enums.Color.GREEN);
        b.validate(null);
    }
}