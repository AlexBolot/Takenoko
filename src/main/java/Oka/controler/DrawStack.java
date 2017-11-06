package Oka.controler;

import Oka.model.Enums;
import Oka.model.Irrigation;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.GardenerGoalMultiPlot;
import Oka.model.goal.Goal;
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
    private ArrayList<BambooGoal> listBambooGoal = new ArrayList<>();
    private ArrayList<GardenerGoal> listGardenerGoal = new ArrayList<>();
    private int remainingChannels = 20;
    //endregion

    public DrawStack(){
        // on créer la pioche des BamboosGoal
        HashMap<Color,Integer> bamboos = new HashMap();
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

        // on créer la pioche des GardenerGoal
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

        Enums.DrawStackPlot.nbGreenPlot.setTabState(new int[]{6,2,2,1});
        Enums.DrawStackPlot.nbPinkPlot.setTabState(new int[]{6,1,1,1});
        Enums.DrawStackPlot.nbYellowPlot.setTabState(new int[]{4,1,1,1});
    }
    //region==========METHODS==============

    public static DrawStack getInstance ()
    {
        return ourInstance;
    }
    public Goal drawGoal (GoalType goalType)
    {
        Random random = new Random();
        Color colors[] = Color.values();

        Color randomColor = colors[random.nextInt(colors.length)];

        switch (goalType)
        {
            case BambooGoal:
                return listBambooGoal.get(random.nextInt(listBambooGoal.size()-1));

            case GardenGoal:
                return listGardenerGoal.get(random.nextInt(listGardenerGoal.size()-1));

            default:
                return drawGoal(GoalType.values()[random.nextInt(GoalType.values().length)]);

        }
    }

    public ArrayList<Plot> giveTreePlot ()
    {
        Random rand = new Random();
        ArrayList<Plot> listP = new ArrayList<>();
        Plot plot;
        int totalPlotFree = Enums.DrawStackPlot.getNbPlot(), randInt, indexColorPlot;

        if (totalPlotFree > 0)
        {
            for (int i = 0; i < (totalPlotFree > 3 ? 3 : totalPlotFree); i++)
            {
                randInt = rand.nextInt(totalPlotFree);
                totalPlotFree = Enums.DrawStackPlot.getNbPlot();

                indexColorPlot = chooseColor(randInt, Enums.DrawStackPlot.values());
                randInt = rand.nextInt(Enums.DrawStackPlot.values()[indexColorPlot].getnbPlotByColor());
                plot = setPlotState(indexColorPlot, randInt);
                listP.add(plot);
            }
            return listP;
        }
        return null;
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
    private static int chooseColor (int randInt, Enums.DrawStackPlot tab[])
    {
        int index = 0, total = tab[index].getnbPlotByColor();

        while (randInt >= total && tab[index].getnbPlotByColor() <= 0)
        {
            index++;
            total += tab[index].getnbPlotByColor();
        }
        return index;
    }

    private static Plot setPlotState (int indexColor, int randInt)
    {
        Enums.DrawStackPlot drawStackPlot = Enums.DrawStackPlot.values()[indexColor];
        Enums.Color color = Enums.Color.values()[indexColor];
        int index = 0, tabState[] = drawStackPlot.getTabState(), total = tabState[index];

        while (randInt >= total)
        {
            index++;
            total += tabState[index];
        }
        tabState[index]--;
        drawStackPlot.setTabState(tabState);

        if (index == 0) return new Plot(color, new NeutralState());
        else if (index == 1) return new Plot(color, new PondState());
        else if (index == 2) return new Plot(color, new EnclosureState());
        else return new Plot(color, new FertilizerState());
    }

    public void giveBackPlot (ArrayList<Plot> listP)
    {
        Enums.Color colorPlot;
        int index = 0;
        for (int i = 0; i < listP.size(); i++)
        {
            colorPlot = listP.get(i).getColor();
            while (colorPlot.equals(Enums.Color.values()[index]))
            {
                index++;
            }
            setNbState(Enums.DrawStackPlot.values()[index], listP.get(i));
        }
    }

    private static void setNbState (Enums.DrawStackPlot nbColorPlot, Plot plot)
    {
        int tab[] = nbColorPlot.getTabState(), index = 0;
        while (plot.getState().getState() != Enums.State.values()[index])
        {
            index++;
        }
        tab[index]++;
        nbColorPlot.setTabState(tab);
    }

    public static void resetDrawStack() {
        ourInstance = new DrawStack();
    }
}