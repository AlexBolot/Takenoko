package Oka.entities;

import junit.framework.TestCase;

import java.awt.*;

public class PandaTest extends TestCase
{
    private Panda panda = Panda.getInstance();

    public void testGetAndSetCoords () throws Exception
    {
        Point p1 = panda.getCoords();
        Point p2 = new Point(p1.x + 1, p1.y + 1);

        panda.setCoords(p2);

        assertEquals(p2, panda.getCoords());
    }

}