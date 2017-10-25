package Oka.ai;

import Oka.model.Bamboo;
import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AITest
{
    @Test
    public void checkGoal () throws Exception
    {

        ArrayList<Goal> goals = new ArrayList<>();
        goals.add(new BambooGoal(3, 1, Enums.Color.GREEN));
        goals.add(new BambooGoal(3, 2, Enums.Color.GREEN));
        goals.add(new BambooGoal(3, 2, Enums.Color.PINK));
        goals.add(new BambooGoal(3, 3, Enums.Color.GREEN));
        goals.add(new BambooGoal(3, 5, Enums.Color.GREEN));

        AI AI = new AI("Ma");

        for (Goal goal : goals)
        {
            AI.addGoal(goal);
        }

        for (int i = 0; i < 5; i++)
        {
            AI.addBamboo(Enums.Color.GREEN);
        }

        AI.checkGoal();

        assertEquals(5, AI.getGoals().size());
        assertEquals(2, AI.getGoalValidated(true).size());
        assertEquals(3, AI.getGoalValidated(false).size());
    }
}