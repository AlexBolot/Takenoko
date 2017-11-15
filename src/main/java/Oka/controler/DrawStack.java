package Oka.controler;

import Oka.model.Enums;
import Oka.model.Irrigation;
import Oka.model.Vector;
import Oka.model.goal.*;
import Oka.model.plot.Plot;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.NeutralState;
import Oka.model.plot.state.PondState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import static Oka.model.Enums.Color;
import static Oka.model.Enums.GoalType;

public class DrawStack
{
    //region==========ATTRIBUTES===========
    private static final int MaxBambooGoal = 10;

    private static DrawStack ourInstance    = new DrawStack();
    private ArrayList<Plot> listPlot = new ArrayList<>();
    private ArrayList<BambooGoal> listBambooGoal = new ArrayList<>();
    private ArrayList<GardenerGoal> listGardenerGoal = new ArrayList<>();
    private ArrayList<PlotGoal> listPlotGoal = new ArrayList<>();
    private int remainingChannels = 20;
    private int fertilizerStates = 3;
    private int pondStates = 3;
    private int enclosureStates = 3;
    //endregion

    public DrawStack(){
        initListBambooGoal();
        initListGardenGoal();
        initListPlotGoal();
        initListPlot();
    }

    //region==========METHODS==============

    public static DrawStack getInstance ()
    {
        return ourInstance;
    }
    public Goal drawGoal (GoalType goalType)
    {
        Random random = new Random();
        switch (goalType)
        {
            case BambooGoal:
                return listBambooGoal.remove(random.nextInt(listBambooGoal.size()-1));

            case GardenGoal:
                return listGardenerGoal.remove(random.nextInt(listGardenerGoal.size()-1));

            case PlotGoal:
                return listPlotGoal.remove(random.nextInt(listPlotGoal.size()-1));

            default:
                return drawGoal(GoalType.values()[random.nextInt(GoalType.values().length)]);
        }
    }
    public ArrayList<Plot> giveTreePlot () {
        Random rand = new Random();
        ArrayList<Plot> listP = new ArrayList<>();
        if (listPlot.size() > 2)
        {
            for (int i = 0; i < 3; i++)
            {
                listP.add(listPlot.remove(rand.nextInt(listPlot.size())));
            }
            return listP;
        }
        else if (listPlot.size() > 0)
        {
            for (int i = 0; i < listPlot.size(); i++)
            {
                listP.add(listPlot.remove(0));
            }
        return listP;
        }
        else return null;
    }
    /**
     * returns an optional irrigation channel if one can be drawn from the stack
     *
     * @return Optional irrigation
     */
    public Optional<Irrigation> drawChannel() {
        if (remainingChannels < 1) return Optional.empty();
        remainingChannels--;
        return Optional.of(new Irrigation());
    }

    public Optional<FertilizerState> drawFertilizerState() {
        if (fertilizerStates == 0) {
            return Optional.empty();
        }
        fertilizerStates--;
        return Optional.of(new FertilizerState());
    }

    public Optional<PondState> drawPondState() {
        if (pondStates == 0) {
            return Optional.empty();
        }
        pondStates--;
        return Optional.of(new PondState());

    }

    public Optional<EnclosureState> drawEnclosureState() {
        if (enclosureStates == 0) {
            return Optional.empty();
        }
        enclosureStates--;
        return Optional.of(new EnclosureState());
    }

    public void giveBackPlot (ArrayList<Plot> listP)
    {
        if (listP.size() > 0) listPlot.addAll(listP);
    }

    public static void resetDrawStack() {
        ourInstance = new DrawStack();
    }

    private void initListPlot(){
        for(int i=0; i<6; i++){
            listPlot.add(new Plot(Color.GREEN,new NeutralState()));
            listPlot.add(new Plot(Color.PINK,new NeutralState()));
        }
        listPlot.add(new Plot(Color.GREEN,new PondState()));
        listPlot.add(new Plot(Color.GREEN,new PondState()));
        listPlot.add(new Plot(Color.GREEN,new EnclosureState()));
        listPlot.add(new Plot(Color.GREEN,new EnclosureState()));
        listPlot.add(new Plot(Color.GREEN,new FertilizerState()));

        listPlot.add(new Plot(Color.PINK,new PondState()));
        listPlot.add(new Plot(Color.PINK,new EnclosureState()));
        listPlot.add(new Plot(Color.PINK,new FertilizerState()));

        listPlot.add(new Plot(Color.YELLOW,new NeutralState()));
        listPlot.add(new Plot(Color.YELLOW,new NeutralState()));
        listPlot.add(new Plot(Color.YELLOW,new NeutralState()));
        listPlot.add(new Plot(Color.YELLOW,new NeutralState()));
        listPlot.add(new Plot(Color.YELLOW,new PondState()));
        listPlot.add(new Plot(Color.YELLOW,new EnclosureState()));
        listPlot.add(new Plot(Color.YELLOW,new FertilizerState()));
    }
    private void initListPlotGoal() {
        PlotGoal pgP = new PlotGoal(0,Color.PINK);
        PlotGoal pgG = new PlotGoal(0,Color.GREEN);
        PlotGoal pgY = new PlotGoal(0,Color.YELLOW);
        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.x, 1), pgG);
        listPlotGoal.add(new PlotGoal(2,Color.GREEN,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.x, 1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgG);
        listPlotGoal.add(new PlotGoal(3,Color.GREEN,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgP);
        plots.put(new Vector(Enums.Axis.x, 1), pgP);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(5,Color.YELLOW,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.x, 1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgP);
        listPlotGoal.add(new PlotGoal(4,Color.PINK,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.x, 1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(3,Color.YELLOW,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.z, -1), pgY);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(3,Color.YELLOW,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgY);
        plots.put(new Vector(Enums.Axis.x, 1), pgY);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(4,Color.YELLOW,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgY);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(3,Color.YELLOW,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.z, -1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgG);
        listPlotGoal.add(new PlotGoal(2,Color.GREEN,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgG);
        listPlotGoal.add(new PlotGoal(2,Color.GREEN,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgP);
        plots.put(new Vector(Enums.Axis.z, 1), pgP);
        listPlotGoal.add(new PlotGoal(4,Color.PINK,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgP);
        plots.put(new Vector(Enums.Axis.x, 1), pgP);
        listPlotGoal.add(new PlotGoal(4,Color.PINK,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgP);
        plots.put(new Vector(Enums.Axis.x, 1), pgP);
        plots.put(new Vector(Enums.Axis.z, 1), pgP);
        listPlotGoal.add(new PlotGoal(5,Color.PINK,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.z, -1), pgP);
        plots.put(new Vector(Enums.Axis.z, 1), pgP);
        listPlotGoal.add(new PlotGoal(4,Color.PINK,plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgY);
        plots.put(new Vector(Enums.Axis.x, 1), pgY);
        listPlotGoal.add(new PlotGoal(4,Color.YELLOW,plots));
    }
    private void initListGardenGoal() {
        listGardenerGoal.add(new GardenerGoal(5,4,Color.YELLOW, new PondState()));
        listGardenerGoal.add(new GardenerGoal(6,4,Color.YELLOW, new NeutralState()));
        listGardenerGoal.add(new GardenerGoalMultiPlot(6,3,Color.PINK, new NeutralState(),2));
        listGardenerGoal.add(new GardenerGoalMultiPlot(7,3,Color.YELLOW, new NeutralState(),3));
        listGardenerGoal.add(new GardenerGoalMultiPlot(8,3,Color.GREEN, new NeutralState(), 4));
        listGardenerGoal.add(new GardenerGoal(4,4,Color.GREEN, new EnclosureState()));
        listGardenerGoal.add(new GardenerGoal(4,4,Color.GREEN, new PondState()));
        listGardenerGoal.add(new GardenerGoal(5,4,Color.GREEN, new NeutralState()));
        listGardenerGoal.add(new GardenerGoal(4,4,Color.YELLOW, new FertilizerState()));
        listGardenerGoal.add(new GardenerGoal(5,4,Color.YELLOW, new EnclosureState()));
        listGardenerGoal.add(new GardenerGoal(5,4,Color.PINK, new FertilizerState()));
        listGardenerGoal.add(new GardenerGoal(6,4,Color.PINK, new PondState()));
        listGardenerGoal.add(new GardenerGoal(6,4,Color.PINK, new EnclosureState()));
        listGardenerGoal.add(new GardenerGoal(7,4,Color.PINK, new NeutralState()));
        listGardenerGoal.add(new GardenerGoal(3,4,Color.GREEN, new FertilizerState()));
    }
    private void initListBambooGoal() {
        HashMap<Color, Integer> bamboos = new HashMap<>();
        bamboos.put(Color.GREEN,1);
        bamboos.put(Color.PINK,1);
        bamboos.put(Color.YELLOW,1);

        for(int i = 0; i < 3; i++){
            listBambooGoal.add(new BambooGoal(3,2,Color.GREEN));
            listBambooGoal.add(new BambooGoal(4,2,Color.YELLOW));
            listBambooGoal.add(new BambooGoal(5,2,Color.PINK));
            listBambooGoal.add(new BambooGoal(6,bamboos));
        }
        listBambooGoal.add(new BambooGoal(3,2,Color.GREEN));
        listBambooGoal.add(new BambooGoal(3,2,Color.GREEN));
        listBambooGoal.add(new BambooGoal(4,2,Color.YELLOW));
    }
}