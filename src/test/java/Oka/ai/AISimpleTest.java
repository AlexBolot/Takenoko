package Oka.ai;

/*..................................................................................................
 . Copyright (c)
 .
 . The AISimpleTest	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 16/10/17 14:26
 .................................................................................................*/

import Oka.controler.GameBoard;
import Oka.controler.GameController;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class AISimpleTest
{


    private Point p01 = new Point(0, 1);
    private Point p10 = new Point(1, 0);

    @Before
    public void before ()
    {
        Cleaner.clearAll();
        Plot plot1 = new Plot(p01, Enums.Color.PINK);
        plot1.addBamboo();
        GameBoard.getInstance().addCell(plot1);

        Plot plot2 = new Plot(p10, Enums.Color.GREEN);
        GameBoard.getInstance().addCell(plot2);
    }

    @Test
    public void movePanda () throws Exception
    {
        Panda.getInstance().setCoords(new Point());

        AISimple AI = new AISimple("Momo");

        GameController.getInstance().setCurrentPlayer(AI);

        AI.addGoal(new BambooGoal(3, 1, Enums.Color.PINK));
        AI.movePanda();
        Assert.assertEquals(p01, Panda.getInstance().getCoords());

        Panda.getInstance().setCoords(new Point());

        AI = new AISimple("Momo");

        GameController.getInstance().setCurrentPlayer(AI);

        AI.addGoal(new BambooGoal(3, 1, Enums.Color.GREEN));
        AI.movePanda();
        Assert.assertEquals(p10, Panda.getInstance().getCoords());
    }

    @Test
    public void moveGardener () throws Exception
    {
        Panda.getInstance().setCoords(new Point());

        AISimple AI = new AISimple("Momo");

        AI.addGoal(new BambooGoal(3, 1, Enums.Color.PINK));
        AI.moveGardener();
        Assert.assertEquals(p01, Gardener.getInstance().getCoords());
        Gardener.getInstance().setCoords(new Point());

        AI = new AISimple("Momo");

        AI.addGoal(new BambooGoal(3, 1, Enums.Color.GREEN));
        AI.moveGardener();
        Assert.assertEquals(p10, Gardener.getInstance().getCoords());

    }
}