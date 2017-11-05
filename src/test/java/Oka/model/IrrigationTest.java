package Oka.model;

import Oka.controler.GameBoard;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static Oka.model.Enums.Color.GREEN;
import static Oka.model.Enums.Color.PINK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class IrrigationTest
{
    private Irrigation i1;
    private Irrigation i2;
    private Irrigation i3;

    private Plot plot1;
    private Plot plot2;
    private Plot plot3;

    @Before
    public void setup ()
    {
        i1 = new Irrigation();
        i2 = new Irrigation();
        i3 = new Irrigation();

        /* Attention à ajouter les points dans le bon ordre
        pour qu'ils puissent être ajoutés sur le board
        selon les règles */
        plot1 = new Plot(new Point(0, 1), PINK);
        plot2 = new Plot(new Point(1, 0), PINK);
        plot3 = new Plot(new Point(1, 1), GREEN);

        Cleaner.cleanGameBoard();

        GameBoard.getInstance().addCell(plot1);
        GameBoard.getInstance().addCell(plot2);
        GameBoard.getInstance().addCell(plot3);
    }

    @Test
    public void Irrigation ()
    {
        //region -> Using default Constructor
        i1 = new Irrigation();
        i1.setPlot1(plot1);
        i1.setPlot2(plot2);

        assertEquals(plot1, i1.getPlot1());
        assertEquals(plot2, i1.getPlot2());
        //endregion

        //region -> Using 'plot, plot' Constructor
        i2 = new Irrigation(plot1, plot2);

        assertEquals(plot1, i2.getPlot1());
        assertEquals(plot2, i2.getPlot2());
        //endregion
    }

    @Test
    public void equals ()
    {
        i1.setPlot1(plot1);
        i1.setPlot2(plot2);

        i2.setPlot1(plot2);
        i2.setPlot2(plot1);

        i3.setPlot1(plot1);
        i3.setPlot2(plot3);

        assertEquals(i1, i2);
        assertEquals(i2, i1);
        assertNotEquals(i3, i1);
        assertNotEquals(i3, i2);
    }

    @Test
    public void hashCodeSymetry ()
    {
        i1.setPlot1(plot1);
        i1.setPlot2(plot2);

        i2.setPlot1(plot2);
        i2.setPlot2(plot1);

        assertEquals(i1.hashCode(), i2.hashCode());
    }
}