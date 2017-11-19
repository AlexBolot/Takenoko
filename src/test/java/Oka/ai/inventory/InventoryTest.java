package Oka.ai.inventory;

import Oka.model.goal.Goal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {
    private Inventory inventory;

    @Before
    public void init(){
        inventory = new Inventory();
        inventory.addGoal(new Goal(3,true));
        inventory.addGoal(new Goal(3,true));
        inventory.addGoal(new Goal(3,false));
    }
    @Test
    public void getValueOfGoalHolder() throws Exception {
        assertEquals(6,inventory.getValueOfGoalHolder());
    }

}