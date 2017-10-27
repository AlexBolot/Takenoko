package Oka.model;


import java.awt.*;

public class Cell
{
    private Point   coords;

    public Cell (Point coords)
    {
        this.coords = coords;
    }

    public Point getCoords ()
    {
        return coords;
    }

    public void setCoords (Point coords)
    {
        this.coords = coords;
    }

    public String toString ()
    {
        return getClass().getSimpleName() + "[x=" + getCoords().x + ",y=" + getCoords().y + "]";
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj == null || !(obj instanceof Cell)) return false;

        Cell c = (Cell) obj;

        return this.getCoords().getX() == c.getCoords().getX() && this.getCoords().getY() == c.getCoords().getY();
    }
}
