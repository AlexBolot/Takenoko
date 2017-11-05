package Oka.model.plot;

import Oka.model.Bamboo;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static Oka.model.Enums.Color.*;
import static org.junit.Assert.*;

public class PlotTest
{
    private Plot plot1;
    private Plot plot2;
    private Plot plot3;

    @Before
    public void setup ()
    {
        plot1 = new Plot(new Point(1, 0), PINK);
        plot2 = new Plot(new Point(0, 1), YELLOW);
        plot3 = new Plot(new Point(1, -1), GREEN);
    }

    @Test
    public void testEquals () throws Exception
    {
        Plot plot1Bis = new Plot(new Point(1, 0), PINK);
        Plot plot2Presque = new Plot(new Point(0, 1), GREEN);
        Plot plot3Presque = new Plot(new Point(1, 0), PINK);

        //Mêmes couleurs et coordonnées
        assertEquals(plot1, plot1Bis);

        //Couleurs et coordonnées différentes
        assertNotEquals(plot1, plot2);

        //Mêmes coordonnées mais couleurs différentes
        assertNotEquals(plot2, plot2Presque);

        //Mêmes couleurs mais coordonnées différentes
        assertNotEquals(plot3, plot3Presque);
    }

    @Test
    public void testAddBamboo () throws Exception
    {
        //Ajouter 1 bamboo
        plot1.addBamboo();
        plot2.addBamboo();

        //Ajouter 5 bamboos
        plot3.addBamboo();
        plot3.addBamboo();
        plot3.addBamboo();
        plot3.addBamboo();
        plot3.addBamboo();

        assertEquals(1, plot1.getBamboo().size());

        //On ne peut pas avoir plus que 4 bamboos
        assertEquals(4, plot3.getBamboo().size());

        //La couleur du bamboo est bien celle de la parcelle
        assertEquals(plot1.getColor(), plot1.getBamboo().get(0).getColor());
        assertEquals(plot2.getColor(), plot2.getBamboo().get(0).getColor());
        assertEquals(plot3.getColor(), plot3.getBamboo().get(0).getColor());
    }

    @Test
    public void testGiveBamboo () throws Exception
    {
        plot1.addBamboo();
        plot1.addBamboo();

        assertEquals(new Bamboo(PINK), plot1.giveBamboo());
        assertNotEquals(new Bamboo(GREEN), plot1.giveBamboo());
        assertNull(plot1.giveBamboo());
    }


}