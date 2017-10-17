package Oka.entities.IA;

import Oka.model.Bamboo;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class IATest {
    @Test
    public void checkGoal() throws Exception {

        ArrayList<Goal> pendinggoal = new ArrayList<Goal>();
        pendinggoal.add(new BambooGoal(3,1));
        pendinggoal.add(new BambooGoal(3,1));
        pendinggoal.add(new BambooGoal(3,1));
        pendinggoal.add(new BambooGoal(3,1));
        pendinggoal.add(new BambooGoal(3,1));

        ArrayList<Bamboo> bamboos = new ArrayList<Bamboo>();
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));
        bamboos.add(new Bamboo(Color.green));

        IA ia = new IA(pendinggoal,bamboos);

        ia.checkGoal();
        assertEquals(4, ia.getObjectivesdone().size());


    }

}