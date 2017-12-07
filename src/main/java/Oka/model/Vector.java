package Oka.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Oka.model.Enums.Axis;
import static Oka.model.Enums.Axis.*;

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

    //region==========GETTER/SETTER========
    public int length ()
    {
        return length;
    }
    //endregion

    //region==========METHODS==============

    /**
     <hr>
     <h3>Finds the manathan vector between two points.
     </h3>
     <hr>

     @param origin Origin point
     @param dest   Destination point
     @return A new Vector(axis, length) with the distance and the axis
     */
    public static Vector findVector (Point origin, Point dest)
    {
        int point1z = -(origin.x + origin.y);
        int point2z = -(dest.x + dest.y);

        int absx = Math.abs(origin.x - dest.x);
        int absy = Math.abs(origin.y - dest.y);
        int absz = Math.abs(point1z - point2z);
        int maxAbs = Math.max(absx, Math.max(absy, absz));

        Axis axis = (absx > absz) ? x : ((absy > absz) ? y : z);

        return new Vector(axis, maxAbs);
    }

    /**
     <hr>
     <h3>Checks if the origin and the destination are ligned, according to the axis defined in Enums.Axis
     </h3>
     <hr>

     @param origin Origin point
     @param dest   Destination point
     @return True if the 2 points are aligned, false otherwise
     */
    public static boolean areAligned (Point origin, Point dest) {

            return findStraightVector(origin, dest) != null;}

    /**
     <hr>
     <h3>Tries to find a vector connecting the 2 points, if they are in a straight line.
     </h3>
     <hr>

     @param origin Origin point
     @param dest   Destination point
     @return A new Vector(axis, length) if found, null otherwise
     */
    public static Vector findStraightVector (Point origin, Point dest) {
        if (origin == null || dest == null) throw new IllegalArgumentException("Parameter is null !");

        if (origin.getX() != dest.getX() && origin.getY() == dest.getY())
        {
            return new Vector(x, (int) Math.abs(dest.getX() - origin.getX()));
        }
        if (origin.getX() == dest.getX() && origin.getY() != dest.getY())
        {
            return new Vector(y, (int) Math.abs(dest.getY() - origin.getY()));
        }
        if (origin.getX() - dest.getX() == -(origin.getY() - dest.getY()))
        {
            return new Vector(z, (int) Math.abs(dest.getX() - origin.getX()));
        }
        return null;
    }

    public Axis axis ()
    {
        return axis;
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

    public void rotateClockwize ()
    {
        List<Axis> axis = new ArrayList<>(Arrays.asList(y, x, z));
        int i = axis.indexOf(this.axis) + 1;
        this.axis = axis.get(i % 3);
        if (i == 3) this.length = -this.length;
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
    public int hashCode ()
    {
        int result = length;
        result = 31 * result + axis.hashCode();
        return result;
    }

    @Override
    public Vector clone ()
    {
        return new Vector(this.axis, length);
    }

    @Override
    public String toString ()
    {
        return axis + " " + String.valueOf(length);
    }
    //endregion

}
