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
     <hr>
     <h3>Checks if the origin and the destination are ligned, according to the axis defined in Enums.Axis
     </h3>
     <hr>

     @param origin Origin point
     @param dest   Destination point
     @return True if the 2 points are aligned, false otherwise
     */
    public static boolean areAligned (Point origin, Point dest)
    {
        return findStraightVector(origin, dest) != null;
    }

    /**
     <hr>
     <h3>Tries to find a vector connecting the 2 points, if they are in a straight line.
     </h3>
     <hr>

     @param origin Origin point
     @param dest   Destination point
     @return A new Vector(axis, length) if found, null otherwise
     */
    public static Vector findStraightVector (Point origin, Point dest)
    {
        if (origin == null || dest == null) throw new IllegalArgumentException("Parameter is null !");

        if (origin.getX() != dest.getX() && origin.getY() == dest.getY())
        {
            return new Vector(Axis.x, (int) Math.abs(dest.getX() - origin.getX()));
        }
        if (origin.getX() == dest.getX() && origin.getY() != dest.getY())
        {
            return new Vector(Axis.y, (int) Math.abs(dest.getY() - origin.getY()));
        }
        if (origin.getX() - dest.getX() == -(origin.getY() - dest.getY()))
        {
            return new Vector(Axis.z, (int) Math.abs(dest.getX() - origin.getX()));
        }
        return null;
    }

    /**
     <hr>
     <h3>Applies this vector on the point given as parameter.
     </h3>
     <hr>

     @param point Origin point to apply the vector on
     @return A new Point, which coord's are the parameter's one, translated with this vector
     */
    public Point applyVector (Point point)
    {
        if (point == null) throw new IllegalArgumentException("Param is null !");

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

        return axis.equals(v.axis) && length == v.length;
    }

    @Override
    public int hashCode() {
        int result = length;
        result = 31 * result + axis.hashCode();
        return result;
    }

    public int length() {
        return length;
    }

    public Axis axis() {
        return axis;
    }

    @Override
    public String toString ()
    {
        return axis + " " + String.valueOf(length);
    }
    //endregion

}
