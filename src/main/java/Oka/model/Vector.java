package Oka.model;

import java.awt.*;

public class Vector {
    private int length;
    private Axis axis;
    Vector(Axis axis, int length){
        this.axis=axis;
        this.length=length;
    }
    public enum Axis{
        x,y,z;
    }
    public Point applyVector(Point point){
        switch (axis){
            case x : point.move(point.x+length,point.y);
                break;
            case y : point.move(point.x,point.y+length);
                break;
            case z : point.move(point.x+length,point.y-length);
                break;
        }
        return point;
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
