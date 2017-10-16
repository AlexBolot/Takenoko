package Oka.entities.IA;

/*..................................................................................................
 . Copyright (c)
 .
 . The IASimpleTest	 Class was Coded by : Team_A
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
import Oka.model.Bamboo;
import Oka.model.plot.Plot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class IASimpleTest
{
    private Point p01 = new Point(0, 1);
    private Point p10 = new Point(1, 0);

    @Before
    public void before ()
    {
        Plot plot1 = new Plot(p01);
        plot1.addBamboo(new Bamboo(Color.BLUE));
        GameBoard.getInstance().addCell(plot1);

        Plot plot2 = new Plot(p10);
        GameBoard.getInstance().addCell(plot2);
    }

    @Test
    public void movePanda () throws Exception
    {
        new IASimple().movePanda();

        Assert.assertEquals(p01, Panda.getInstance().getCoords());
    }

    @Test
    public void moveGardener () throws Exception
    {
        new IASimple().moveGardener();

        Assert.assertEquals(p10, Gardener.getInstance().getCoords());
    }
}