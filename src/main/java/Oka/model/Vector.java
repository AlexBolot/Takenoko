package Oka.model;

import java.awt.*;

import static Oka.model.Enums.Axis;

public class Vector
{
    //region==========ATTRIBUTES===========
    private int  length;
    private Axis axis;
    //endregion

    //region==========CONSTRUCTORS=========
    public Vector (Axis axis, int length)
    {
        this.axis = axis;
        this.length = length;
    }
    //endregion

    //region==========METHODS==============
    /**
     returns if the destination is on a straight line from the orgine

     @param origin Point origin point
     @param dest   Point destination point
     @return boolean true if the destination is aligned
     */
    public static boolean areAligned (Point origin, Point dest)
    {
        return findStraightVector(origin, dest) != null;
    }

    /**
     tries to find a vector linking the two points in a straight line
     return null if impossible

     @param point  Point origin of the vector
     @param point1 Point destination of the vector
     @return Vector | null
     */
    public static Vector findStraightVector (Point point, Point point1)
    {
        if (point.getX() != point1.getX() && point.getY() == point1.getY())
        {
            return new Vector(Axis.x, (int) Math.abs(point1.getX() - point.getX()));
        }
        if (point.getX() == point1.getX() && point.getY() != point1.getY())
        {
            return new Vector(Axis.y, (int) Math.abs(point1.getY() - point.getY()));
        }
        if (point.getX() - point1.getX() == -(point.getY() - point1.getY()))
        {
            return new Vector(Axis.z, (int) Math.abs(point1.getX() - point.getX()));
        }
        return null;
    }

    public Point applyVector (Point point)
    {
        Point newPoint = (Point) point.clone();

        switch (axis)
        {
            case x:
                newPoint.move(newPoint.x + length, newPoint.y);
                break;

            case y:
                newPoint.move(newPoint.x, newPoint.y + length);
                break;

            case z:
                newPoint.move(newPoint.x + length, newPoint.y - length);
                break;
        }
        return newPoint;
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    @Override
    public boolean equals (Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;

        Vector v = (Vector) obj;

        return v.axis.equals(this.axis) && v.length == this.length;
    }
    //endregion
}
