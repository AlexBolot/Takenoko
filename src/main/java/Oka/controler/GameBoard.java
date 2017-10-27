package Oka.controler;

import Oka.entities.Entity;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Pond;
import Oka.model.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class GameBoard
{
    private static GameBoard ourInstance = new GameBoard();

    private HashMap<Point, Cell> grid           = new HashMap<>();
    private ArrayList<Point>     availableSlots = new ArrayList<>();

    private GameBoard ()
    {
        availableSlots.add(new Point());

        addCell(new Pond());
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

    public void setAvailableSlots (ArrayList<Point> availableSlots)
    {
        this.availableSlots = availableSlots;
    }

    public void addCell (Cell cell)
    {
        assertNotOnGrid(cell.getCoords());
        assertIsAvailable(cell.getCoords());

        grid.put(cell.getCoords(), cell);
        availableSlots.remove(cell.getCoords());

        refreshAvailableSlots(cell);
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
        vectors[0] = new Vector(Enums.Axis.x, 1);
        vectors[1] = new Vector(Enums.Axis.x, -1);
        vectors[2] = new Vector(Enums.Axis.y, 1);
        vectors[3] = new Vector(Enums.Axis.y, -1);
        vectors[4] = new Vector(Enums.Axis.z, 1);
        vectors[5] = new Vector(Enums.Axis.z, -1);

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
        vectors[0] = new Vector(Enums.Axis.x, 1);
        vectors[1] = new Vector(Enums.Axis.x, -1);
        vectors[2] = new Vector(Enums.Axis.y, 1);
        vectors[3] = new Vector(Enums.Axis.y, -1);
        vectors[4] = new Vector(Enums.Axis.z, 1);
        vectors[5] = new Vector(Enums.Axis.z, -1);

        ArrayList<Cell> neightbours = new ArrayList<>();

        for (Vector v : vectors)
        {
            if (grid.containsKey(v.applyVector(point))) neightbours.add(grid.get(v.applyVector(point)));
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

    public boolean moveEntity (Entity entity, Point point)
    {
        if (entity == null) throw new IllegalArgumentException("Entity is null");
        if (point == null) throw new IllegalArgumentException("Point is null");
        if (!grid.containsKey(point)) throw new IllegalArgumentException("The cell is not on the grid");

        return Vector.areAligned(entity.getCoords(), point) && entity.move(point);
    }
}
