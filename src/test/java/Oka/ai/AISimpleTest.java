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
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.plot.Plot;
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
        Plot plot1 = new Plot(p01, Color.pink);
        plot1.addBamboo();
        GameBoard.getInstance().addCell(plot1);

        Plot plot2 = new Plot(p10, Color.pink);
        GameBoard.getInstance().addCell(plot2);
    }

    @Test
    public void movePanda () throws Exception
    {
        new AISimple("Ma").movePanda();

        Assert.assertEquals(p01, Panda.getInstance().getCoords());
    }

    @Test
    public void moveGardener () throws Exception
    {
        new AISimple("Ma").moveGardener();

        Assert.assertEquals(p10, Gardener.getInstance().getCoords());
    }
}