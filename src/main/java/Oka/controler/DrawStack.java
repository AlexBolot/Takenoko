package Oka.controler;

import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.NeutralState;
import Oka.model.plot.state.PondState;

import java.util.ArrayList;
import java.util.Random;

import static Oka.model.Enums.Color;
import static Oka.model.Enums.goalType;

public class DrawStack
{
    //region==========ATTRIBUTES===========
    private static final int MaxBambooGoal = 10;
    //endregion

    //region==========METHODS==============
    public static Goal drawGoal (goalType goalType)
    {
        Random random = new Random();
        switch (goalType)
        {
            case BambooGoal:
                return new BambooGoal(2, random.nextInt(3) + 2, Color.PINK);

            case PlotGoal:
                break;

            case GardenGoal:
                return new GardenerGoal(2, random.nextInt(3) + 1, Color.PINK);

        }
        return null;
    }

    public static ArrayList<Plot> giveTreePlot() {
        Random rand = new Random();
        ArrayList<Plot> listP =  new ArrayList<>();
        Plot plot;
        int totalPlotFree = Enums.DrawStack.getNbPlot(),
                randInt;

        if(totalPlotFree > 3) {
            for (int i = 0; i < 3; i++) {
                totalPlotFree = Enums.DrawStack.getNbPlot();
                randInt = rand.nextInt(totalPlotFree);

                int indexColorPlot = whereIsRandInt(randInt,Enums.DrawStack.values());
                randInt = rand.nextInt(Enums.DrawStack.values()[indexColorPlot].getnbPlotByColor())+1;

                plot = setPlotState(indexColorPlot,randInt);
                listP.add(plot);
            }
            return listP;
        }
        return null;
    }

    private static int whereIsRandInt(int randInt, Enums.DrawStack tab[]){
        int index = 0,
                total = tab[index].getnbPlotByColor();
        while(randInt > total ){
            index ++;
            total += tab[index].getnbPlotByColor();
        }
        return index;
    }

    private static Plot setPlotState(int indexColor, int randInt){
        Enums.DrawStack drawStack = Enums.DrawStack.values()[indexColor];
        Enums.Color color = Enums.Color.values()[indexColor];
        int index = 0,
                tabState[] = drawStack.getTabState(),
                total = tabState[index];

        while(randInt > total){
            index++;
            total += tabState[index];
        }
        tabState[index]--;
        drawStack.setTabState(tabState);

        if(index == 0) return new Plot(color,new NeutralState());
        else if(index == 1) return new Plot(color,new PondState());
        else if(index == 2) return new Plot(color,new EnclosureState());
        else return new Plot(color,new FertilizerState());
    }

    public static void giveBackPlot(ArrayList<Plot> listP) {
        Enums.Color colorPlot;
        int index = 0;
        for (int i = 0; i < listP.size(); i++) {
            colorPlot = listP.get(i).getColor();
            while(colorPlot.equals(Enums.Color.values()[index])){
                index++;
            }
            setNbState(Enums.DrawStack.values()[index],listP.get(i));
        }
    }
    private static void setNbState (Enums.DrawStack nbColorPlot, Plot plot)
    {
        int tab[] = nbColorPlot.getTabState(), index = 0;
        while (plot.getState().getState() != Enums.State.values()[index])
        {
            index++;
        }
        tab[index]++;
        nbColorPlot.setTabState(tab);
    }
}