package Oka.model.plot;

import Oka.model.Bamboo;
import Oka.model.Pond;
import junit.framework.TestCase;
import org.junit.Test;
import sun.swing.BakedArrayList;

import java.awt.*;
import java.util.ArrayList;

public class PlotTest extends TestCase {
    @Test
    public void testEquals() throws Exception {
        Plot p1 = new Plot();
        p1.setCoords(new Point(1, 0));
        Plot p1B = new Plot(new Point(1, 0));
        Plot p2 = new Plot(new Point(0, 1));
        Plot p3 = new Plot(new Point(1, 0));
        ArrayList<Bamboo> bamboo = new ArrayList<Bamboo>();
        bamboo.add(new Bamboo(Color.BLUE));
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
        Plot plot = new Plot(new Point(0, 1));
        Bamboo b = new Bamboo(Color.BLUE);
        plot.addBamboo(b);
        Pond pond = new Pond();
        assertEquals(b, plot.giveBamboo());
        assertEquals(null, plot.giveBamboo());


    }


}