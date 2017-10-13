package Oka.entities;

import java.awt.*;

public class Panda
{
    private static Panda ourInstance = new Panda();
    private        Point coords      = new Point();


    private Panda ()
    {

    }

    public static Panda getInstance ()
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
