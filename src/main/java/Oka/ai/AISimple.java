package Oka.ai;

import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.entities.Entity;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.model.plot.Plot;
import Oka.utils.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AISimple extends AI
{

    public AISimple (String name) {
        super(name);
    }

    public boolean moveGardener ()
    {
        Gardener gardener = Gardener.getInstance();
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();

        int maxBamboo = 4;

        for (int bambooSize = 0; bambooSize < maxBamboo; bambooSize++)
        {
            //noinspection Duplicates TODO will be fixed when adding logs
            if (moveEntity(gardener, bambooSize))
            {
                Cell cell = grid.get(gardener.getCoords());

                if (cell instanceof Plot)
                {
                    //Temporary, will be used for logs (see OKA-56)
                    Plot plot = (Plot) cell;
                }

                return true;
            }

        }

        return false;
    }

    public boolean movePanda ()
    {
        Panda panda = Panda.getInstance();
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();

        int maxBamboo = 4;

        for (int bambooSize = maxBamboo; bambooSize > 0; bambooSize--)
        {
            //noinspection Duplicates TODO will be fixed when adding logs
            if (moveEntity(panda, bambooSize))
            {
                Logger.printLine("moved gardener : " + panda.getCoords());

                Cell cell = grid.get(panda.getCoords());

                if (cell instanceof Plot)
                {
                    //TODO Temporary, will be used for logs (see OKA-56)
                    Plot plot = (Plot) cell;

                    //TODO Temporary, will become useless when Inventory will be set
                    //But it makes the whole thing work for now so...
                    getBamboos().add(panda.gatherBamboo());
                }

                return true;
            }

        }

        return false;
    }

    /**
     place a plot tile
     */
    public void placePlot ()
    {
        GameBoard board = GameBoard.getInstance();
        Random rand = new Random();
        DrawStack drawStack = new DrawStack();
        ArrayList<Plot> draw = null;

        // On pioche trois parcelles si possible
        try{
            draw = drawStack.giveTreePlot();
            //tant qu'on nous renvois les même trois case
            int randInt = rand.nextInt(3);
            Plot plot = draw.get(randInt);

            // Toujours penser remettre les cartes dans la pioche après avoir pioché ;)
            draw.remove(randInt);
            drawStack.giveBackPlot(draw);

            //todo: add a available slot function
            ArrayList<Point> free = board.getAvailableSlots();
            plot.setCoords(free.get(0));
            board.addCell(plot);
        }catch (NullPointerException e){
            System.out.print("IL N'Y A PLUS DE CARTE DANS LA PIOCHE");
        }
    }

    public void ValidateObjective ()
    {
        //Todo
    }

    private boolean moveEntity (Entity entity, int bambooSize)
    {
        GameBoard gameBoard = GameBoard.getInstance();
        HashMap<Point, Cell> grid = gameBoard.getGrid();
        Point currentPoint = entity.getCoords();

        for (Point point : grid.keySet())
        {
            if (point.equals(currentPoint) || !(grid.get(point) instanceof Plot)) continue;

            Plot plot = (Plot) grid.get(point);

            if (plot.getBamboo().size() == bambooSize && gameBoard.moveEntity(entity, point)) return true;
        }

        return false;
    }
}
