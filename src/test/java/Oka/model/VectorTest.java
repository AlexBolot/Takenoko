package Oka.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static Oka.model.Enums.Axis;
import static org.junit.Assert.*;

public class VectorTest
{
    private Point o;
    private Point x;
    private Point y;
    private Point z;
    private Point nx;
    private Point ny;
    private Point nz;

    @Before
    public void setup ()
    {
        o = new Point();
        x = new Point(1, 0);
        y = new Point(0, 1);
        z = new Point(1, -1);
        nx = new Point(-1, 0);
        ny = new Point(0, -1);
        nz = new Point(-1, 1);
    }

    @Test
    public void testApplyVector_Right ()
    {
        //region -> Apply Vector of length 1 to origin
        Vector xV = new Vector(Axis.x, 1);
        Vector yV = new Vector(Axis.y, 1);
        Vector zV = new Vector(Axis.z, 1);

        assertEquals(x, xV.applyVector(o));
        assertEquals(y, yV.applyVector(o));
        assertEquals(z, zV.applyVector(o));
        //endregion

        //region -> Apply Vector of different length to origin
        Vector xV2 = new Vector(Axis.x, 2);
        Vector yV5 = new Vector(Axis.y, 5);
        Vector zVn4 = new Vector(Axis.z, -4);

        assertEquals(new Point(2, 0), xV2.applyVector(o));
        assertEquals(new Point(0, 5), yV5.applyVector(o));
        assertEquals(new Point(-4, 4), zVn4.applyVector(o));
        //endregion

        //region -> Apply Vector of different length to different points
        Vector xVn3 = new Vector(Axis.x, -3);
        Vector yV2 = new Vector(Axis.y, 2);
        Vector zV5 = new Vector(Axis.z, 5);

        assertEquals(new Point(-2, -1), xVn3.applyVector(z));
        assertEquals(new Point(1, 2), yV2.applyVector(x));
        assertEquals(new Point(5, -4), zV5.applyVector(y));
        //endregion
    }

    @Test (expected = IllegalArgumentException.class)
    public void testApplyVector_Null ()
    {
        Vector xV = new Vector(Axis.x, 1);

        xV.applyVector(null);
    }

    @Test
    public void testAreAligned_Right ()
    {
        assertTrue(Vector.areAligned(o, o));

        assertTrue(Vector.areAligned(o, x));
        assertTrue(Vector.areAligned(o, y));
        assertTrue(Vector.areAligned(o, z));

        assertTrue(Vector.areAligned(o, nx));
        assertTrue(Vector.areAligned(o, ny));
        assertTrue(Vector.areAligned(o, nz));

        assertTrue(Vector.areAligned(x, nx));
        assertTrue(Vector.areAligned(y, ny));
        assertTrue(Vector.areAligned(z, nz));

        assertTrue(Vector.areAligned(x, y));
        assertTrue(Vector.areAligned(x, z));

        assertFalse(Vector.areAligned(y, z));
        assertFalse(Vector.areAligned(x, nz));
        assertFalse(Vector.areAligned(y, nx));
    }

    @Test
    public void testAreAligned_Null ()
    {
        int[] crashCount = {0, 0, 0};

        //region Crash Test 1
        try
        {
            Vector.areAligned(null, o);
        }
        catch (IllegalArgumentException iae)
        {
            crashCount[0] = 1;
        }
        //endregion

        //region Crash Test 2
        try
        {
            Vector.areAligned(o, null);
        }
        catch (IllegalArgumentException iae)
        {
            crashCount[1] = 1;
        }
        //endregion

        //region Crash Test 3
        try
        {
            Vector.areAligned(null, null);
        }
        catch (IllegalArgumentException iae)
        {
            crashCount[2] = 1;
        }
        //endregion

        for (int i : crashCount)
        {
            String message = "crash test n° " + (i + 1) + " failed";

            assertEquals(message, 1, crashCount[i]);
        }
    }

    @Test
    public void testFindStraightVector_Right ()
    {
        Vector xV = new Vector(Axis.x, 1);
        Vector yV = new Vector(Axis.y, 1);
        Vector zV = new Vector(Axis.z, 1);

        assertEquals(xV, Vector.findStraightVector(o, x));
        assertEquals(yV, Vector.findStraightVector(o, y));
        assertEquals(zV, Vector.findStraightVector(o, z));

        assertNull(Vector.findStraightVector(z, y));
    }

    @Test
    public void testFindStraightVector_Null ()
    {
        int[] crashCount = {0, 0, 0};

        //region Crash Test 1
        try
        {
            Vector.findStraightVector(null, o);
        }
        catch (IllegalArgumentException iae)
        {
            crashCount[0] = 1;
        }
        //endregion

        //region Crash Test 2
        try
        {
            Vector.findStraightVector(o, null);
        }
        catch (IllegalArgumentException iae)
        {
            crashCount[1] = 1;
        }
        //endregion

        //region Crash Test 3
        try
        {
            Vector.findStraightVector(null, null);
        }
        catch (IllegalArgumentException iae)
        {
            crashCount[2] = 1;
        }
        //endregion

        for (int i : crashCount)
        {
            String message = "crash test n° " + (i + 1) + " failed";

            assertEquals(message, 1, crashCount[i]);
        }
    }

}