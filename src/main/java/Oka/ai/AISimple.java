package Oka.ai;

import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.entities.Entity;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.plot.Plot;
import Oka.utils.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

public class AISimple extends AI
{


    public AISimple (String name)
    {
        super(name);
    }

    /**
     moves the gardener to a desired spot
     chooses the spot based on the current bamboo objective
     returns true if it managed to move
     false otherwise

     @return boolean
     */
    public boolean moveGardener ()
    {
        Gardener gardener = Gardener.getInstance();
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();
        ArrayList<BambooGoal> bambooGoals = this.getGoalValidated(false)
                                                .stream()
                                                .filter(c -> c instanceof BambooGoal)//we take only the bamboogoals
                                                .map(g -> (BambooGoal) g)//we cast them as such
                                                .collect(Collectors.toCollection(ArrayList::new));//we get back a collection of them
        // todo: optimise based on proximity to completion
        Enums.Color color = bambooGoals.get(0).bamboocolor();
        int maxBamboo = 4;

        for (int bambooSize = 0; bambooSize < maxBamboo; bambooSize++)
        {
            //noinspection Duplicates TODO will be fixed when adding logs
            if (moveEntity(gardener, bambooSize, color))
            {
                Cell cell = grid.get(gardener.getCoords());
                Logger.printLine(getName() + " moved gardener : " + gardener.getCoords());

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

    /**
     moves the panda to a tile of the desired color based on bamboogoal

     @return true if the tile was found, false otherwise
     */
    public boolean movePanda ()
    {
        Panda panda = Panda.getInstance();
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();

        int maxBamboo = 4;
        ArrayList<BambooGoal> bambooGoals = this.getGoalValidated(false)
                                                .stream()
                                                .filter(c -> c instanceof BambooGoal)//we take only the bamboogoals
                                                .map(g -> (BambooGoal) g)//we cast them as such
                                                .collect(Collectors.toCollection(ArrayList::new));//we get back a collection of them
        //todo: optimise based on proximity to completion
        Enums.Color color = bambooGoals.get(0).bamboocolor();

        for (int bambooSize = maxBamboo; bambooSize >= 0; bambooSize--)
        {
            //noinspection Duplicates TODO will be fixed when adding logs
            if (moveEntity(panda, bambooSize, color))
            {
                Logger.printLine(getName() + " moved panda : " + panda.getCoords());

                Cell cell = grid.get(panda.getCoords());

                if (cell instanceof Plot)
                {
                    //TODO Temporary, will be used for logs (see OKA-56)
                    Plot plot = (Plot) cell;
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
        try
        {
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

            Logger.printLine(getName() + " placed : " + plot);

        }
        catch (NullPointerException e)
        {
            System.out.print("IL N'Y A PLUS DE CARTE DANS LA PIOCHE");
        }
    }

    public void ValidateObjective ()
    {
        //Todo
    }

    /**
     moves a passed entity to a spot with the desired bamboo quantity.

     @param entity     Entity, Panda or gardener
     @param bambooSize int, the desired bamboo amount on the tile
     @param color      Color, desired color of the tile
     @return true if the asked tile could be found, false otherwise
     */
    private boolean moveEntity (Entity entity, int bambooSize, Enums.Color color)
    {
        GameBoard gameBoard = GameBoard.getInstance();
        HashMap<Point, Cell> grid = gameBoard.getGrid();
        Point currentPoint = entity.getCoords();

        for (Point point : grid.keySet())
        {
            if (point.equals(currentPoint) || !(grid.get(point) instanceof Plot)) continue;

            Plot plot = (Plot) grid.get(point);

            if (plot.getBamboo().size() == bambooSize && plot.getColor().equals(color) && gameBoard.moveEntity(entity, point))
            {
                return true;
            }
        }

        return false;
    }

    public void play ()
    {
        setActionsLeft(2);


        while (actionsLeft > 0)
        {
            if (getGoals().size() == 0)
            {
                addGoal(DrawStack.drawGoal(Enums.goalType.GardenerGoal));
                addGoal(DrawStack.drawGoal(Enums.goalType.BambooGoal));
            }
            Logger.printSeparator(getName());
            Logger.printLine(getName() + " - goal = " + getGoalValidated(false).toString());
            //  Logger.printLine(getName() + " - gameBoard : " + GameBoard.getInstance().getGrid())


            //noinspection Duplicates
            placePlot();
            actionsLeft--;
            if (new Random().nextBoolean())
            {
                moveGardener();
                actionsLeft--;
            }
            else
            {
                movePanda();
                actionsLeft--;

            }
            Logger.printLine(getName() + " - bamboos = " + getBamboos().size());

        }

    }
}




















    /* Release 5 dés switch (dés.draw()){
            case 1:setActionsLeft(3);
                break;
            case 2: .
                break;
            case 3: moveGardener();
                break;
            case 4: movePanda();
                break;
            case 5: DrawAmenagement();
                break;
            case 6:
                IA.Choosedés();
                break;} */