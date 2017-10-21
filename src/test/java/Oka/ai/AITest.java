package Oka.ai;

import Oka.model.Bamboo;
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
        goals.add(new BambooGoal(3, 1, Color.green));
        goals.add(new BambooGoal(3, 2, Color.green));
        goals.add(new BambooGoal(3, 2, Color.blue));
        goals.add(new BambooGoal(3, 3, Color.green));
        goals.add(new BambooGoal(3, 5, Color.green));

        ArrayList<Bamboo> bamboos = new ArrayList<>();
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));

        AI AI = new AI("Ma");

        for (Goal goal : goals)
        {
            AI.addGoal(goal);
        }

        for (Bamboo bamboo : bamboos)
        {
            AI.addBamboo(bamboo);
        }

        AI.checkGoal();

        assertEquals(5, AI.getGoals().size());
        assertEquals(2, AI.getDoneGoals().size());
        assertEquals(3, AI.getPendingGoals().size());
    }
}