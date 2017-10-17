package Oka.controler;

import Oka.model.Cell;
import Oka.model.Pond;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.ArrayList;

public class GameBoard
{
    private static GameBoard ourInstance = new GameBoard();

    private ArrayList<Cell> grid = new ArrayList<Cell>();

    private GameBoard ()
    {
        grid.add(new Pond());
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
            if (cell.getCoords() == point)
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
    public Plot givePlot()
    {
        return new Plot();
    }

    /**
     * should return all the possible slots where a tile may be layed
     * todo : implement
     *
     * @return ArrayList
     */
    public ArrayList<Point> getAvailableSlots() {

        return null;
    }
}
