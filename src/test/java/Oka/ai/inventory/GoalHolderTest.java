package Oka.ai.inventory;

import Oka.model.Bamboo;
import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoalHolderTest {
    private GoalHolder goalHolder;
    private BambooHolder bambooholder;
    @Before
    public void init(){
        goalHolder = new GoalHolder();
        goalHolder.add(new BambooGoal(5,1, Enums.Color.GREEN));
        goalHolder.add(new BambooGoal(5,2, Enums.Color.PINK));
        goalHolder.add(new BambooGoal(5,3, Enums.Color.YELLOW));

        bambooholder = new BambooHolder();
        bambooholder.add(new Bamboo(Enums.Color.GREEN));
        bambooholder.add(new Bamboo(Enums.Color.GREEN));
        bambooholder.add(new Bamboo(Enums.Color.GREEN));
        bambooholder.add(new Bamboo(Enums.Color.PINK));
        bambooholder.add(new Bamboo(Enums.Color.PINK));
        bambooholder.add(new Bamboo(Enums.Color.YELLOW));
    }

    @Test
    public void checkGoal() throws Exception {

        goalHolder.checkGoal(bambooholder);
        assertEquals(2,goalHolder.getGoalValidated(true).size());
    }


}