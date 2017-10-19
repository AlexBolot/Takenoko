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

import static Oka.model.Vector.Axis.*;

public class GameBoard
{
    private static GameBoard ourInstance = new GameBoard();

    private ArrayList<Cell> grid           = new ArrayList<Cell>();
    private ArrayList<Cell> availableSlots = new ArrayList<>();

    private GameBoard ()
    {
        Pond pond = new Pond();

        grid.add(pond);

        Point point1 = new Vector(x, 1).applyVector(pond.getCoords());
        Point point2 = new Vector(x, -1).applyVector(pond.getCoords());
        Point point3 = new Vector(y, 1).applyVector(pond.getCoords());
        Point point4 = new Vector(y, -1).applyVector(pond.getCoords());
        Point point5 = new Vector(z, 1).applyVector(pond.getCoords());
        Point point6 = new Vector(z, -1).applyVector(pond.getCoords());

        availableSlots.addAll(Arrays.asList(new Plot(point1),
                                            new Plot(point2),
                                            new Plot(point3),
                                            new Plot(point4),
                                            new Plot(point5),
                                            new Plot(point6)));


    }

    public static GameBoard getInstance ()
    {
        return ourInstance;
    }

    public ArrayList<Cell> getGrid ()
    {
        return grid;
    }

    public void setGrid (ArrayList<Cell> grid)
    {
        this.grid = grid;
    }

    public Cell getCell (Point point)
    {
        for (Cell cell : grid)
        {
            if (cell.getCoords().equals(point))
            {
                return cell;
            }
        }
        return null;
    }

    public void addCell (Plot plot)
    {
        grid.add(plot);
    }

    //TODO upgrade this on next release
    public Goal giveGoal ()
    {
        //Very easy objective for now : get 1 bamboo to validate.
        //Will be changed in another release.
        return new BambooGoal(1, 1);
    }

    //TODO upgrade this on next release
    public Plot givePlot ()
    {
        return new Plot();
    }

    /**
     should return all the possible slots where a tile may be layed
     todo : implement

     @return ArrayList
     */
    public ArrayList<Point> getAvailableSlots ()
    {
        ArrayList<Point> arrayList = new ArrayList<>();

        //noinspection unchecked
        for (Cell cell : grid)
        {
            if (cell instanceof Plot)
            {
                availableSlots.remove(cell);
            }
        }

        for (Cell cell : availableSlots)
        {
            arrayList.add(cell.getCoords());
        }

        return arrayList;
    }
}
