package Oka.controler;

import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

public class DrawStackTest {
    DrawStack ourInstance = new DrawStack();
    ArrayList<BambooGoal> listBambooGoal = new ArrayList<>();
    ArrayList<GardenerGoal> listGardenerGoal = new ArrayList<>();


    @Before
    public void init()
    {
        HashMap<Enums.Color,Integer> bamboos = new HashMap();
        bamboos.put(Enums.Color.GREEN,1);
        bamboos.put(Enums.Color.PINK,1);
        bamboos.put(Enums.Color.YELLOW,1);

        listBambooGoal.add(new BambooGoal(3,2,Enums.Color.GREEN ));
        listBambooGoal.add(new BambooGoal(4,2, Enums.Color.YELLOW));
        listBambooGoal.add(new BambooGoal(5,2, Enums.Color.PINK));

    }
    @Test
    public void testDrawStack()
    {

        assertEquals(listBambooGoal.get(1),new BambooGoal(4,2, Enums.Color.YELLOW ));
        assertEquals(listBambooGoal.get(0),new BambooGoal(3,2,Enums.Color.GREEN ));
        assertEquals(listBambooGoal.get(2),new BambooGoal(5,2, Enums.Color.PINK));

    }
  /*  @Test
    public void testDrawGoal(){
        Random random = new Random();
        Enums.GoalType goalType = Enums.GoalType.GardenGoal;
        if (ourInstance.drawGoal(goalType) instanceof GardenerGoal){
            assertEquals(ourInstance.drawGoal(goalType), new Goal {
            });
        }

    }
*/
}