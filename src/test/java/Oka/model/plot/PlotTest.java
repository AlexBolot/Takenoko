package Oka.model.plot;

import Oka.model.Bamboo;
import junit.framework.TestCase;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

public class PlotTest extends TestCase {
    @Test
    public void testEquals() throws Exception {
        Plot p1 = new Plot(Color.pink);
        p1.setCoords(new Point(1, 0));
        Plot p1B = new Plot(new Point(1, 0),Color.pink);
        Plot p2 = new Plot(new Point(0, 1),Color.pink);
        Plot p3 = new Plot(new Point(1, 0),Color.pink);
        ArrayList<Bamboo> bamboo = new ArrayList<Bamboo>();
        bamboo.add(new Bamboo(Color.pink));
        p3.setBamboo(bamboo);

        assertTrue(p1.equals(p1B));
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
        assertFalse(p1.equals(p3));
        assertFalse(p3.equals(p1));

    }

    public void testAddBamboo() throws Exception {
    }

    public void testGiveBamboo() throws Exception {
        Plot plot = new Plot(new Point(0, 1),Color.pink);
        Bamboo b = new Bamboo(Color.pink);
        plot.addBamboo();
        assertEquals(b, plot.giveBamboo());
        assertEquals(null, plot.giveBamboo());


    }


}