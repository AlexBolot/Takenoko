package Oka.model;

import java.awt.*;

public class Vector {
    private int length;
    private Axis axis;

    public Vector (Axis axis, int length)
    {
        this.axis=axis;
        this.length=length;
    }
    public enum Axis{
        x,y,z;
    }
    public Point applyVector(Point point){
        Point newPoint = (Point) point.clone();

        switch (axis){
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
    public static Vector isOnVector(Point point,Point point1){
        if (point.getX()!=point1.getX() && point.getY()==point1.getY()){
            return new Vector(Axis.x,(int)Math.abs(point1.getX()-point.getX()));
        }
        if (point.getX()==point1.getX() && point.getY()!=point1.getY()){
            return new Vector(Axis.y,(int)Math.abs(point1.getY()-point.getY()));
        }
        if (point.getX()-point1.getX()==-(point.getY()-point1.getY())) {
            return new Vector(Axis.x,(int)Math.abs(point1.getX()-point.getX()));
        }
        return null;
    }
}
