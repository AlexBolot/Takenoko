package Oka.controler;

import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.PlotGoal;
import Oka.model.plot.Plot;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.PondState;
import Oka.utils.Cleaner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DrawStackTest {


    DrawStack ourInstance;
    ArrayList<BambooGoal> listBambooGoal = new ArrayList<>();
    ArrayList<GardenerGoal> listGardenerGoal = new ArrayList<>();


    @Before
    public void init()
    {

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
        while(listP.size()!=2){
            assertEquals(3,listP.size());
            listP = ourInstance.giveTreePlot();
        }
        assertEquals(2,listP.size());
        listP =ourInstance.giveTreePlot();
        assertEquals(1,listP.size());
        listP = ourInstance.giveTreePlot();
        assertEquals(null,listP);
    }

    @Test
    public void plotQuantity() {
        HashSet<Plot> plots = new HashSet<>();
        ArrayList<Plot> threeBundle;
        while ((threeBundle = ourInstance.giveTreePlot()) != null) {
            plots.addAll(threeBundle);
        }
        assertEquals(27, plots.size());
        assertEquals(11, plots.stream().filter(plot -> plot.getColor().equals(Enums.Color.GREEN)).count());
        assertEquals(7, plots.stream().filter(plot -> plot.getColor().equals(Enums.Color.YELLOW)).count());
        assertEquals(9, plots.stream().filter(plot -> plot.getColor().equals(Enums.Color.PINK)).count());

    }

    @Test
    public void fertilizerStateQuantity() {
        int stateCount = 0;
        Optional<FertilizerState> state;
        while (ourInstance.drawFertilizerState().isPresent()) stateCount++;
        assertEquals(3, stateCount);

    }

    @Test
    public void pondStateQuantity() {
        int stateCount = 0;
        Optional<PondState> state;
        while (ourInstance.drawPondState().isPresent()) stateCount++;
        assertEquals(3, stateCount);

    }

    @Test
    public void bambooGoalQuantity() {
        ArrayList<BambooGoal> bGoals = new ArrayList<BambooGoal>();
        while (ourInstance.emptyGoalType(Enums.GoalType.BambooGoal))
            bGoals.add((BambooGoal) ourInstance.drawGoal(Enums.GoalType.BambooGoal).get());
        assertEquals(15, bGoals.size());
        assertEquals(5, bGoals.stream().filter(bambooGoal -> bambooGoal.getAmountByColor(Enums.Color.GREEN) > 0 && bambooGoal.getAmountByColor(Enums.Color.PINK) == 0).count());
        assertEquals(4, bGoals.stream().filter(bambooGoal -> bambooGoal.getAmountByColor(Enums.Color.YELLOW) > 0 && bambooGoal.getAmountByColor(Enums.Color.PINK) == 0).count());
        assertEquals(3, bGoals.stream().filter(bambooGoal -> bambooGoal.getAmountByColor(Enums.Color.PINK) > 0 && bambooGoal.getAmountByColor(Enums.Color.YELLOW) == 0).count());
        assertEquals(3, bGoals.stream().filter(bambooGoal -> bambooGoal.getAmountByColor(Enums.Color.PINK) > 0 && bambooGoal.getAmountByColor(Enums.Color.YELLOW) > 0 && bambooGoal.getAmountByColor(Enums.Color.GREEN) > 0).count());
    }

    // test on total quantity and lightly on color repartition
    @Test
    public void gardenerGoalQuantity() {
        ArrayList<GardenerGoal> gGoals = new ArrayList<GardenerGoal>();
        while (ourInstance.emptyGoalType(Enums.GoalType.GardenGoal))
            gGoals.add((GardenerGoal) ourInstance.drawGoal(Enums.GoalType.GardenGoal).get());
        assertEquals(15, gGoals.size());

        List<GardenerGoal> yellow = gGoals.stream().filter(gardenerGoal -> gardenerGoal.getColor().equals(Enums.Color.YELLOW)).collect(Collectors.toList());
        assertEquals(5, yellow.size());

        List<GardenerGoal> pink = gGoals.stream()
                .filter(gardenerGoal -> gardenerGoal.getColor().equals(Enums.Color.PINK)).collect(Collectors.toList());
        assertEquals(5, pink.size());
        List<GardenerGoal> green = gGoals.stream()
                .filter(gardenerGoal -> gardenerGoal.getColor().equals(Enums.Color.GREEN)).collect(Collectors.toList());
        assertEquals(5, green.size());

    }

    @Test
    public void plotGoalsQuantity() {

        ArrayList<PlotGoal> pGoals = new ArrayList<PlotGoal>();
        while (ourInstance.emptyGoalType(Enums.GoalType.PlotGoal)) {
            pGoals.add((PlotGoal) ourInstance.drawGoal(Enums.GoalType.PlotGoal).get());
        }

        assertEquals(15, pGoals.size());
        assertEquals(6, pGoals.stream().filter(plotGoal -> plotGoal.getColors().containsKey(Enums.Color.GREEN)).count());
        assertEquals(6, pGoals.stream().filter(plotGoal -> plotGoal.getColors().containsKey(Enums.Color.YELLOW)).count());
        assertEquals(6, pGoals.stream().filter(plotGoal -> plotGoal.getColors().containsKey(Enums.Color.PINK)).count());

    }

    @Test
    public void enclosureStateQuantity() {
        int stateCount = 0;
        Optional<EnclosureState> state;
        while (ourInstance.drawEnclosureState().isPresent()) stateCount++;
        assertEquals(3, stateCount);
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