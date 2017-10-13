package Oka.entities;

import java.awt.*;

public class Gardener
{
    private static Gardener ourInstance = new Gardener();
    private        Point    coords      = new Point();

    private Gardener ()
    {

    }

    public static Gardener getInstance ()
    {
        return ourInstance;
    }

    public Point getCoords ()
    {
        return coords;
    }

    public void setCoords (Point coords)
    {
        this.coords = coords;
    }
}
