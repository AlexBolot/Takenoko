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

public abstract class Entity
{
    //region==========ATTRIBUTES===========
    private Point coords;
    //endregion

    //region==========CONSTRUCTORS=========
    protected Entity ()
    {
        coords = new Point();
    }
    //endregion

    //region==========GETTER/SETTER========
    public Point getCoords ()
    {
        return coords;
    }

    protected void setCoords (Point coords)
    {
        this.coords = coords;
    }
    //endregion

    //region==========METHODS==============
    public boolean move (Point point)
    {
        if (point == null) throw new IllegalArgumentException("Point is null");

        if (this.getCoords().equals(point))
        {
            return false;
        }

        this.setCoords(point);

        return true;
    }
    //endregion
}
