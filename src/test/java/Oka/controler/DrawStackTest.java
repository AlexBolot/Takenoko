package Oka.controler;

import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.PondState;
import Oka.utils.Cleaner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.*;

public class DrawStackTest {


    DrawStack ourInstance;
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

        ourInstance = new DrawStack();

    }
    @After
    public void end(){
        DrawStack.resetDrawStack();
    }
    @Test
    public void testDrawStack() {

        assertEquals(listBambooGoal.get(1),new BambooGoal(4,2, Enums.Color.YELLOW ));
        assertEquals(listBambooGoal.get(0),new BambooGoal(3,2,Enums.Color.GREEN ));
        assertEquals(listBambooGoal.get(2),new BambooGoal(5,2, Enums.Color.PINK));

    }
    @Test
    public void giveTreePlot() throws Exception {
        ArrayList<Plot> listP = ourInstance.giveTreePlot();
        while(listP!=null){
            assertEquals(3,listP.size());
            listP = ourInstance.giveTreePlot();
        }
        assertEquals(null,ourInstance.giveTreePlot());
    }

    @Test
    public void giveBackPlot() throws Exception {

    }

    @Test
    public void drawFertilizerState() throws Exception {
        Cleaner.clearAll();
        Optional<FertilizerState> optFertState = DrawStack.getInstance().drawFertilizerState();
        assertTrue(optFertState.isPresent());
        assertEquals(new FertilizerState(), optFertState.get());
        assertTrue((DrawStack.getInstance().drawFertilizerState()).isPresent());
        assertTrue((DrawStack.getInstance().drawFertilizerState()).isPresent());
        assertFalse((DrawStack.getInstance().drawFertilizerState()).isPresent());
        Cleaner.cleanDrawStack();
        optFertState = DrawStack.getInstance().drawFertilizerState();
        assertTrue(optFertState.isPresent());
        assertEquals(new FertilizerState(), optFertState.get());
    }

    @Test
    public void drawPondState() throws Exception {
        Cleaner.clearAll();
        Optional<PondState> optPondState = DrawStack.getInstance().drawPondState();
        assertTrue(optPondState.isPresent());
        assertEquals(new PondState(), optPondState.get());
        assertTrue((DrawStack.getInstance().drawPondState()).isPresent());
        assertTrue((DrawStack.getInstance().drawPondState()).isPresent());
        assertFalse((DrawStack.getInstance().drawPondState()).isPresent());
        Cleaner.cleanDrawStack();
        optPondState = DrawStack.getInstance().drawPondState();
        assertTrue(optPondState.isPresent());
        assertEquals(new PondState(), optPondState.get());
    }

    @Test
    public void drawEnclosureState() throws Exception {

        Cleaner.clearAll();
        Optional<EnclosureState> optEnclosureState = DrawStack.getInstance().drawEnclosureState();
        assertTrue(optEnclosureState.isPresent());
        assertEquals(new EnclosureState(), optEnclosureState.get());
        assertTrue((DrawStack.getInstance().drawEnclosureState()).isPresent());
        assertTrue((DrawStack.getInstance().drawEnclosureState()).isPresent());
        assertFalse((DrawStack.getInstance().drawEnclosureState()).isPresent());
        Cleaner.cleanDrawStack();
        optEnclosureState = DrawStack.getInstance().drawEnclosureState();
        assertTrue(optEnclosureState.isPresent());
        assertEquals(new EnclosureState(), optEnclosureState.get());

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