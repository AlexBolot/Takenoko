package Oka.model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class VectorTest {

    private Point o;
    private Point x;
    private Point y;
    private Point t;
    private Point z;
    private Point v;
    private Point w;

    @Before
    public void init() {

        o = new Point();
        x = new Point(1, 0);
        y = new Point(0, 1);
        t = new Point(1, 1);
        z = new Point(-1, 1);
        v = new Point(-1, 0);
        w = new Point(0, -1);
    }

    @Test
    public void testApplyVector() throws Exception {
    }

    @Test
    public void testIsAligned() throws Exception {
        //Right Tests
        assertFalse(Vector.areAligned(o, t));
        assertFalse(Vector.areAligned(w, z));
        assertTrue(Vector.areAligned(x, y));
        assertTrue(Vector.areAligned(x, o));
        assertTrue(Vector.areAligned(y, o));
        assertTrue(Vector.areAligned(x, t));
        assertTrue(Vector.areAligned(t, y));
        assertTrue(Vector.areAligned(z, o));
        assertTrue(Vector.areAligned(v, o));
        assertTrue(Vector.areAligned(w, o));
    }

    @Test
    public void testFindStraightVector() throws Exception {
        Vector xV = new Vector(Enums.Axis.x, 1);
        Vector yV = new Vector(Enums.Axis.y, 1);
        Vector nxV = new Vector(Enums.Axis.z, 1);

        assertEquals(xV, Vector.findStraightVector(o, x));
        assertEquals(yV, Vector.findStraightVector(o, y));
        Vector straightVector = Vector.findStraightVector(o, z);
        assertEquals(nxV, straightVector);
        assertNull(Vector.findStraightVector(t, w));
    }

}