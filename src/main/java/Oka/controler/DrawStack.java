package Oka.controler;

import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Oka.model.Enums.goalType.BambooGoal;

public class DrawStack {
    private static final int MaxBambooGoal = 10;

    private static int nbPinkPlot = Enums.DrawStack.nbPinkPlot.getNb();
    private static int nbYellowPlot = Enums.DrawStack.nbYellowPlot.getNb();
    private static int nbGreenPlot = Enums.DrawStack.nbGreenPlot.getNb();


    public static Goal drawGoal(Enums.goalType goalType) {
        switch (goalType) {

            case BambooGoal:
                Random random = new Random();
                BambooGoal bambooGoal = new BambooGoal(2, random.nextInt(3) + 2, Enums.Color.PINK);
                return bambooGoal;
            case PlotGoal:
                break;
            case GardenGoal:
                break;
        }
        return null;
    }
    public static ArrayList<Plot> giveTreePlot() {
        Random rand = new Random();
        Enums.Color tabColor[] = {Enums.Color.GREEN, Enums.Color.PINK, Enums.Color.YELLOW};
        ArrayList<Plot> listP =  new ArrayList<>();
        Plot plot;
        int totalPlotFree = nbPinkPlot + nbYellowPlot + nbGreenPlot,
                index,
                randInt;
        if(totalPlotFree > 3) {
            for (int i = 0; i < 3; i++) {
                totalPlotFree = nbPinkPlot + nbYellowPlot + nbGreenPlot;
                randInt = rand.nextInt(totalPlotFree);

                if (randInt < nbPinkPlot) {
                    index = 0;
                    nbPinkPlot--;
                } else if (randInt >= nbPinkPlot && randInt < nbPinkPlot + nbYellowPlot) {
                    index = 1;
                    nbYellowPlot--;
                } else {
                    index = 2;
                    nbGreenPlot--;
                }

                plot = new Plot(tabColor[index]);

                listP.add(plot);
            }
            return listP;
        }else
            return null;
    }

    public void giveBackPlot(ArrayList<Plot> listP){
        Enums.Color colorPlot;
        for(int i = 0; i<listP.size(); i++){
            colorPlot = listP.get(i).getColor();
            if(colorPlot.equals(Color.yellow))
                nbYellowPlot++;
            else if (colorPlot.equals(Color.green))
                nbGreenPlot++;
            else
                nbPinkPlot++;
        }
    }


}
