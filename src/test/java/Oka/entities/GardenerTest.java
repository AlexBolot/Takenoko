package Oka.entities;

import junit.framework.TestCase;

import java.awt.*;

public class GardenerTest extends TestCase
{
    private Gardener gardener = Gardener.getInstance();

    public void testGetAndSetCoords ()
    {
        Point p1 = gardener.getCoords();
        Point p2 = new Point(p1.x + 1, p1.y + 1);

        gardener.setCoords(p2);

        assertEquals(p2, gardener.getCoords());
    }

}