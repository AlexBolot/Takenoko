package Oka.entities.IA;

import Oka.model.Bamboo;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class IATest
{
    @Test
    public void checkGoal () throws Exception
    {

        ArrayList<Goal> goals = new ArrayList<>();
        goals.add(new BambooGoal(3, 1));
        goals.add(new BambooGoal(3, 2));
        goals.add(new BambooGoal(3, 2));
        goals.add(new BambooGoal(3, 3));
        goals.add(new BambooGoal(3, 5));

        ArrayList<Bamboo> bamboos = new ArrayList<>();
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));

        IA ia = new IA();

        for (Goal goal : goals)
        {
            ia.addGoal(goal);
        }

        for (Bamboo bamboo : bamboos)
        {
            ia.addBamboo(bamboo);
        }

        ia.checkGoal();

        assertEquals(5, ia.getGoals().size());
        assertEquals(3, ia.getDoneGoals().size());
        assertEquals(2, ia.getPendingGoals().size());
    }
}