package Oka.model.plot;

import Oka.model.Bamboo;
import Oka.model.Pond;
import junit.framework.TestCase;

import java.awt.*;

public class PlotTest extends TestCase {
    public void testAddBamboo() throws Exception {
    }

    public void testGiveBamboo() throws Exception {
        Plot plot = new Plot(new Point(0, 1));
        Bamboo b = new Bamboo(Color.BLUE);
        plot.addBamboo(b);
        Pond pond = new Pond();
        assertEquals(b, plot.giveBamboo());
        assertEquals(null, plot.giveBamboo());


    }


}