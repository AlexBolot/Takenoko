package Oka.controler;

import Oka.model.Cell;
import Oka.model.Pond;
import Oka.model.Vector;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static Oka.model.Vector.Axis.*;

public class GameBoard
{
    private static GameBoard ourInstance = new GameBoard();

    private HashMap<Point, Cell> grid           = new HashMap<>();
    private ArrayList<Point>     availableSlots = new ArrayList<>();


    private GameBoard ()
    {
        Pond pond = new Pond();

        grid.put(pond.getCoords(), pond);

        Point point1 = new Vector(x, 1).applyVector(pond.getCoords());
        Point point2 = new Vector(x, -1).applyVector(pond.getCoords());
        Point point3 = new Vector(y, 1).applyVector(pond.getCoords());
        Point point4 = new Vector(y, -1).applyVector(pond.getCoords());
        Point point5 = new Vector(z, 1).applyVector(pond.getCoords());
        Point point6 = new Vector(z, -1).applyVector(pond.getCoords());

        availableSlots.addAll(Arrays.asList(point1, point2, point3, point4, point5, point6));


    }

    public static GameBoard getInstance ()
    {
        return ourInstance;
    }

    public HashMap<Point, Cell> getGrid ()
    {
        return grid;
    }

    public void setGrid (HashMap<Point, Cell> grid)
    {
        this.grid = grid;
    }

    public void addCell (Cell cell)
    {
        assertNotOnGrid(cell.getCoords());
        assertIsAvailable(cell.getCoords());

        grid.put(cell.getCoords(), cell);
        availableSlots.remove(cell.getCoords());

        refreshAvailableSlots(cell);
    }

    //TODO upgrade this on next release
    public Goal giveGoal ()
    {
        Color tabColor[] = {Color.GREEN, Color.BLUE, Color.YELLOW};
        return new BambooGoal(1, 1, tabColor[(int)Math.random()*2]);
    }

    //TODO upgrade this on next release
    public ArrayList<Plot> givePlot ()
    {
        Random rand = new Random();
        Color tabColor[] = {Color.GREEN, Color.BLUE, Color.YELLOW};

        Plot p1 = new Plot(tabColor[rand.nextInt(3)]);
        Plot p2 = new Plot(tabColor[rand.nextInt(3)]);
        Plot p3 = new Plot(tabColor[rand.nextInt(3)]);

        ArrayList<Plot> listP =  new ArrayList<>();
        listP.add(p1);
        listP.add(p2);
        listP.add(p3);

        return listP;
    }

    /**
     should return all the possible slots where a tile may be layed
     todo : implement

     @return ArrayList
     */
    public ArrayList<Point> getAvailableSlots ()
    {
        return availableSlots;
    }

    public ArrayList<Point> getEveryNeihboors (Point point)
    {
        Vector[] vectors = new Vector[6];
        vectors[0] = new Vector(Vector.Axis.x, 1);
        vectors[1] = new Vector(Vector.Axis.x, -1);
        vectors[2] = new Vector(Vector.Axis.y, 1);
        vectors[3] = new Vector(Vector.Axis.y, -1);
        vectors[4] = new Vector(Vector.Axis.z, 1);
        vectors[5] = new Vector(Vector.Axis.z, -1);

        ArrayList<Point> neightbours = new ArrayList<>();

        for (Vector v : vectors)
        {
            neightbours.add(v.applyVector(point));
        }

        return neightbours;
    }
    public ArrayList<Cell> getExistingNeihboors (Point point)
    {
        Vector[] vectors = new Vector[6];
        vectors[0] = new Vector(Vector.Axis.x, 1);
        vectors[1] = new Vector(Vector.Axis.x, -1);
        vectors[2] = new Vector(Vector.Axis.y, 1);
        vectors[3] = new Vector(Vector.Axis.y, -1);
        vectors[4] = new Vector(Vector.Axis.z, 1);
        vectors[5] = new Vector(Vector.Axis.z, -1);

        ArrayList<Cell> neightbours = new ArrayList<>();

        for (Vector v : vectors)
        {
            if(grid.containsKey(v.applyVector(point)))
                neightbours.add(grid.get(v.applyVector(point)));
        }

        return neightbours;
    }

    private void assertNotOnGrid (Point point)
    {
        if (point == null) throw new IllegalArgumentException("Point is null");
        if (grid.containsKey(point)) throw new IllegalArgumentException("The cell is already on the grid");
    }

    private void assertIsAvailable (Point point) throws IllegalArgumentException
    {
        if (point == null) throw new IllegalArgumentException("Point is null");
        if (!availableSlots.contains(point)) throw new IllegalArgumentException("The slot is not available");
    }

    private void refreshAvailableSlots (Cell cell)
    {
        for (Point point : getEveryNeihboors(cell.getCoords()))
        {
            if (!grid.containsKey(point) && !availableSlots.contains(point)) availableSlots.add(point);
        }
    }
}
