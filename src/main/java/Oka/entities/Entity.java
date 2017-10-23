package Oka.entities;

/*..................................................................................................
 . Copyright (c)
 .
 . The Entity	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 18/10/17 16:30
 .................................................................................................*/

import java.awt.*;

public class Entity
{
    private Point coords;

    protected Entity ()
    {
        coords = new Point();
    }

    public Point getCoords ()
    {
        return coords;
    }

    protected Object setCoords (Point coords)
    {
        this.coords = coords;
        return null;
    }

    public boolean move (Point point)
    {
        if (point == null) throw new IllegalArgumentException("Point is null");

        this.setCoords(point);

        return true;
    }
}
