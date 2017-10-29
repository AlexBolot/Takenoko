package Oka.controler;

import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;

import java.util.ArrayList;
import java.util.Random;

import static Oka.model.Enums.Color;
import static Oka.model.Enums.goalType;

public class DrawStack
{
    //region==========ATTRIBUTES===========
    private static final int MaxBambooGoal = 10;
    private static       int nbPinkPlot    = Enums.DrawStack.nbPinkPlot.getNb();
    private static       int nbYellowPlot  = Enums.DrawStack.nbYellowPlot.getNb();
    private static       int nbGreenPlot   = Enums.DrawStack.nbGreenPlot.getNb();
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

            case GardenerGoal:
                return new GardenerGoal(2, random.nextInt(3) + 1, Color.PINK);

        }
        return null;
    }

    public static ArrayList<Plot> giveTreePlot ()
    {
        Random rand = new Random();
        ArrayList<Plot> listP = new ArrayList<>();
        Plot plot;

        int totalPlotFree = nbPinkPlot + nbYellowPlot + nbGreenPlot;
        int index;
        int randInt;

        if (totalPlotFree > 3)
        {
            for (int i = 0; i < 3; i++)
            {
                totalPlotFree = nbPinkPlot + nbYellowPlot + nbGreenPlot;
                randInt = rand.nextInt(totalPlotFree);

                if (randInt < nbPinkPlot)
                {
                    index = 0;
                    nbPinkPlot--;
                }
                else if (randInt >= nbPinkPlot && randInt < nbPinkPlot + nbYellowPlot)
                {
                    index = 1;
                    nbYellowPlot--;
                }
                else
                {
                    index = 2;
                    nbGreenPlot--;
                }

                plot = new Plot(Color.values()[index]);

                listP.add(plot);
            }
            return listP;
        }
        return null;
    }

    public static void giveBackPlot (ArrayList<Plot> listP)
    {
        Color colorPlot;
        for (Plot aListP : listP)
        {
            colorPlot = aListP.getColor();
            if (colorPlot.equals(Color.YELLOW)) nbYellowPlot++;
            else if (colorPlot.equals(Color.GREEN)) nbGreenPlot++;
            else nbPinkPlot++;
        }
    }
    //endregion
}
