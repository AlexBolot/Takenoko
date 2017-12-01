package Oka.controler;

import Oka.model.Enums;
import Oka.model.Enums.State;
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
    private static DrawStack               ourInstance       = new DrawStack();
    private        ArrayList<Plot>         listPlot          = new ArrayList<>();
    private        ArrayList<BambooGoal>   listBambooGoal    = new ArrayList<>();
    private        ArrayList<GardenerGoal> listGardenerGoal  = new ArrayList<>();
    private        ArrayList<PlotGoal>     listPlotGoal      = new ArrayList<>();
    private        int remainingIrrigation = 20;
    private        int                     fertilizerStates  = 3;
    private        int                     pondStates        = 3;
    private        int                     enclosureStates   = 3;
    //endregion

    public DrawStack ()
    {
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

    public static void resetDrawStack ()
    {
        ourInstance = new DrawStack();
    }

    public boolean emptyGoalType (Enums.GoalType goalType)
    {
        switch (goalType)
        {
            case BambooGoal:
                return !listBambooGoal.isEmpty();

            case GardenGoal:
                return !listGardenerGoal.isEmpty();

            case PlotGoal:
                return !listPlotGoal.isEmpty();

            default:
                return false;
        }
    }

    /**
     @param goalType GardenerGoal/BambooGoal/PlotGoal
     @return A goal that the AI has chosen to draw according to the type she needs.
     */
    public Optional<Goal> drawGoal (GoalType goalType)
    {
        Random random = new Random();
        switch (goalType)
        {
            case BambooGoal:
                if (!listBambooGoal.isEmpty()) return Optional.of(listBambooGoal.remove(random.nextInt(listBambooGoal.size())));
                break;

            case GardenGoal:
                if (!listGardenerGoal.isEmpty()) return Optional.of(listGardenerGoal.remove(random.nextInt(listGardenerGoal.size())));
                break;

            case PlotGoal:
                if (!listPlotGoal.isEmpty()) return Optional.of(listPlotGoal.remove(random.nextInt(listPlotGoal.size())));
                break;
        }

        return Optional.empty();
        //return drawGoal(GoalType.values()[random.nextInt(GoalType.values().length)]);
    }

    /**
     @return This gives an Arraylist of 3 Plot to the AI ( when she has to draw one of them to place it )
     */
    public ArrayList<Plot> giveTreePlot ()
    {
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
     returns an optional irrigation if one can be drawn from the stack

     @return Optional irrigation
     */
    public Optional<Irrigation> drawIrrigation ()
    {
        if (remainingIrrigation < 1) return Optional.empty();
        remainingIrrigation--;
        return Optional.of(new Irrigation());
    }

    /**
     @return True if you can draw an FertilizerState
     <br> False if you can't.
     */
    public Optional<FertilizerState> drawFertilizerState ()
    {
        if (fertilizerStates == 0)
        {
            return Optional.empty();
        }
        fertilizerStates--;
        return Optional.of(new FertilizerState());
    }

    /**
     @return True if you can draw a PondState
     <br> False if you can't.
     */
    public Optional<PondState> drawPondState ()
    {
        if (pondStates == 0)
        {
            return Optional.empty();
        }
        pondStates--;
        return Optional.of(new PondState());

    }

    /**
     @return True if you can draw an EnclosureState
     <br> False if you can't.
     */
    public Optional<EnclosureState> drawEnclosureState ()
    {
        if (enclosureStates == 0)
        {
            return Optional.empty();
        }
        enclosureStates--;
        return Optional.of(new EnclosureState());
    }

    public Optional<NeutralState> drawState (State state)
    {
        switch (state)
        {
            case Pond:
                if (pondStates > 0)
                {
                    pondStates--;
                    return Optional.of(new PondState());
                }
                break;

            case Enclosure:
                if (enclosureStates > 0)
                {
                    enclosureStates--;
                    return Optional.of(new EnclosureState());
                }
                break;

            case Fertilizer:
                if (fertilizerStates > 0)
                {
                    fertilizerStates--;
                    return Optional.of(new FertilizerState());
                }
                break;
        }

        return Optional.empty();
    }

    /**
     A method which put the plot not chosen by the AI into the draw.
     */
    public void giveBackPlot (ArrayList<Plot> listP)
    {
        if (listP.size() > 0) listPlot.addAll(listP);
    }

    /**
     All the plot available to draw in the game.
     */
    private void initListPlot ()
    {
        //fixme : Adjuste plot quantity
        for (int i = 0; i < 6; i++)
        {
            listPlot.add(new Plot(Color.GREEN, new NeutralState()));
            listPlot.add(new Plot(Color.PINK, new NeutralState()));
        }
        listPlot.add(new Plot(Color.GREEN, new PondState()));
        listPlot.add(new Plot(Color.GREEN, new PondState()));
        listPlot.add(new Plot(Color.GREEN, new EnclosureState()));
        listPlot.add(new Plot(Color.GREEN, new EnclosureState()));
        listPlot.add(new Plot(Color.GREEN, new FertilizerState()));

        listPlot.add(new Plot(Color.PINK, new PondState()));
        listPlot.add(new Plot(Color.PINK, new EnclosureState()));
        listPlot.add(new Plot(Color.PINK, new FertilizerState()));

        listPlot.add(new Plot(Color.YELLOW, new NeutralState()));
        listPlot.add(new Plot(Color.YELLOW, new NeutralState()));
        listPlot.add(new Plot(Color.YELLOW, new NeutralState()));
        listPlot.add(new Plot(Color.YELLOW, new NeutralState()));
        listPlot.add(new Plot(Color.YELLOW, new PondState()));
        listPlot.add(new Plot(Color.YELLOW, new EnclosureState()));
        listPlot.add(new Plot(Color.YELLOW, new FertilizerState()));
    }

    /**
     All the Plotgoal possible.
     */
    private void initListPlotGoal ()
    {
        PlotGoal pgP = new PlotGoal(0, Color.PINK);
        PlotGoal pgG = new PlotGoal(0, Color.GREEN);
        PlotGoal pgY = new PlotGoal(0, Color.YELLOW);
        HashMap<Vector, PlotGoal> plots = new HashMap<>();

        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.x, 1), pgG);
        listPlotGoal.add(new PlotGoal(2, Color.GREEN, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.x, 1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgG);
        listPlotGoal.add(new PlotGoal(3, Color.GREEN, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgP);
        plots.put(new Vector(Enums.Axis.x, 1), pgP);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(5, Color.YELLOW, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.x, 1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgP);
        listPlotGoal.add(new PlotGoal(4, Color.PINK, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.x, 1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(3, Color.YELLOW, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.z, -1), pgY);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(3, Color.YELLOW, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgY);
        plots.put(new Vector(Enums.Axis.x, 1), pgY);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(4, Color.YELLOW, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgY);
        plots.put(new Vector(Enums.Axis.z, 1), pgY);
        listPlotGoal.add(new PlotGoal(3, Color.YELLOW, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.z, -1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgG);
        listPlotGoal.add(new PlotGoal(2, Color.GREEN, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgG);
        plots.put(new Vector(Enums.Axis.z, 1), pgG);
        listPlotGoal.add(new PlotGoal(2, Color.GREEN, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgP);
        plots.put(new Vector(Enums.Axis.z, 1), pgP);
        listPlotGoal.add(new PlotGoal(4, Color.PINK, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgP);
        plots.put(new Vector(Enums.Axis.x, 1), pgP);
        listPlotGoal.add(new PlotGoal(4, Color.PINK, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgP);
        plots.put(new Vector(Enums.Axis.x, 1), pgP);
        plots.put(new Vector(Enums.Axis.z, 1), pgP);
        listPlotGoal.add(new PlotGoal(5, Color.PINK, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.z, -1), pgP);
        plots.put(new Vector(Enums.Axis.z, 1), pgP);
        listPlotGoal.add(new PlotGoal(4, Color.PINK, plots));
        plots = new HashMap<>();
        plots.put(new Vector(Enums.Axis.y, 1), pgY);
        plots.put(new Vector(Enums.Axis.x, 1), pgY);
        listPlotGoal.add(new PlotGoal(4, Color.YELLOW, plots));
    }

    /**
     All the GardenerGoal possible.
     */
    private void initListGardenGoal ()
    {
        listGardenerGoal.add(new GardenerGoal(5, 4, Color.YELLOW, new PondState()));
        listGardenerGoal.add(new GardenerGoal(6, 4, Color.YELLOW, new NeutralState()));
        listGardenerGoal.add(new GardenerGoalMultiPlot(6, 3, Color.PINK, new NeutralState(), 2));
        listGardenerGoal.add(new GardenerGoalMultiPlot(7, 3, Color.YELLOW, new NeutralState(), 3));
        listGardenerGoal.add(new GardenerGoalMultiPlot(8, 3, Color.GREEN, new NeutralState(), 4));
        listGardenerGoal.add(new GardenerGoal(4, 4, Color.GREEN, new EnclosureState()));
        listGardenerGoal.add(new GardenerGoal(4, 4, Color.GREEN, new PondState()));
        listGardenerGoal.add(new GardenerGoal(5, 4, Color.GREEN, new NeutralState()));
        listGardenerGoal.add(new GardenerGoal(4, 4, Color.YELLOW, new FertilizerState()));
        listGardenerGoal.add(new GardenerGoal(5, 4, Color.YELLOW, new EnclosureState()));
        listGardenerGoal.add(new GardenerGoal(5, 4, Color.PINK, new FertilizerState()));
        listGardenerGoal.add(new GardenerGoal(6, 4, Color.PINK, new PondState()));
        listGardenerGoal.add(new GardenerGoal(6, 4, Color.PINK, new EnclosureState()));
        listGardenerGoal.add(new GardenerGoal(7, 4, Color.PINK, new NeutralState()));
        listGardenerGoal.add(new GardenerGoal(3, 4, Color.GREEN, new FertilizerState()));
    }

    /**
     All the BambooGoal possible.
     */
    private void initListBambooGoal ()
    {
        HashMap<Color, Integer> bamboos = new HashMap<>();
        bamboos.put(Color.GREEN, 1);
        bamboos.put(Color.PINK, 1);
        bamboos.put(Color.YELLOW, 1);

        for (int i = 0; i < 3; i++)
        {
            listBambooGoal.add(new BambooGoal(3, 2, Color.GREEN));
            listBambooGoal.add(new BambooGoal(4, 2, Color.YELLOW));
            listBambooGoal.add(new BambooGoal(5, 2, Color.PINK));
            listBambooGoal.add(new BambooGoal(6, bamboos));
        }
        listBambooGoal.add(new BambooGoal(3, 2, Color.GREEN));
        listBambooGoal.add(new BambooGoal(3, 2, Color.GREEN));
        listBambooGoal.add(new BambooGoal(4, 2, Color.YELLOW));
    }
}