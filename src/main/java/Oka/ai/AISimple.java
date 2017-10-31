package Oka.ai;

import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.entities.Entity;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Enums.Color;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.PondState;
import Oka.utils.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

public class AISimple extends AI
{
    //region==========CONSTRUCTORS=========
    public AISimple (String name)
    {
        super(name);

        Enums.State[] values = Enums.State.values();

        switch (values[2])
        {
            case Pond:
                this.addPlotState(new PondState());
                break;

            case Enclosure:
                this.addPlotState(new EnclosureState());
                break;

            case Fertilizer:
                this.addPlotState(new FertilizerState());
                break;
        }
    }
    //endregion

    //region==========METHODS==============

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
        Color color = bambooGoals.get(0).getColor();  //.bambooColor();
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
        Color color = bambooGoals.get(0).getColor();     //.bambooColor();

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
    public boolean placePlot ()
    {
        GameBoard board = GameBoard.getInstance();
        Random rand = new Random();
        ArrayList<Plot> draw;

        // On pioche trois parcelles si possible

        draw = DrawStack.giveTreePlot();
        if(draw == null)
            return false;

        //On choisit un carte aléatoire parmis les trois car ou moins envoyé par la pioche plot
        int randInt;
        if(draw.size()>=3)
            randInt = rand.nextInt(3);
        else
            randInt = rand.nextInt(draw.size());
        Plot plot = draw.get(randInt);

        // Toujours penser remettre les cartes dans la pioche après avoir pioché ;)
        draw.remove(randInt);
        DrawStack.giveBackPlot(draw);

        //todo: add a available slot function
        ArrayList<Point> free = board.getAvailableSlots();
        plot.setCoords(free.get(0));
        board.addCell(plot);
        Logger.printLine(getName() + " placed : " + plot);
        return true;
    }

    /**
     moves a passed entity to a spot with the desired bamboo quantity.

     @param entity     Entity, Panda or gardener
     @param bambooSize int, the desired bamboo amount on the tile
     @param color      Color, desired color of the tile
     @return true if the asked tile could be found, false otherwise
     */
    protected boolean moveEntity (Entity entity, int bambooSize, Color color)
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

    boolean placePlotState ()
    {
        Color color = null;
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();

        switch (getInventory().plotStates().get(0).getState())
        {
            case Pond:
                ArrayList<BambooGoal> bambooGoals = this.getGoalValidated(false)
                                                        .stream()
                                                        .filter(c -> c instanceof BambooGoal)//we take only the bamboogoals
                                                        .map(g -> (BambooGoal) g)//we cast them as such
                                                        .collect(Collectors.toCollection(ArrayList::new));//we get back a collection of them

                //todo: optimise based on proximity to completion
                color = bambooGoals.get(0).getColor();
                break;

            case Enclosure:
                ArrayList<GardenerGoal> gardenerGoal = this.getGoalValidated(false)
                                                           .stream()
                                                           .filter(c -> c instanceof GardenerGoal)//we take only the bamboogoals
                                                           .map(g -> (GardenerGoal) g)//we cast them as such
                                                           .collect(Collectors.toCollection(ArrayList::new));//we get back a collection of them

                //todo: optimise based on proximity to completion
                color = gardenerGoal.get(0).getColor();
                break;

            case Fertilizer:
                Goal goal = this.getGoalValidated(false).get(0);

                if (goal instanceof BambooGoal) color = ((BambooGoal) goal).getColor();
                if (goal instanceof GardenerGoal) color = ((GardenerGoal) goal).getColor();
        }

        for (Cell cell : grid.values())
        {
            if (cell instanceof Plot)
            {
                Plot plot = (Plot) cell;

                if (plot.getColor().equals(color) && plot.getBamboo().size() == 0)
                {
                    plot.setState(getInventory().plotStates().get(0));

                    Logger.printLine(getName() + " upgraded : " + plot);

                    getInventory().plotStates().remove(0);
                    return true;
                }
            }
        }

        return (false);
    }

    public void play ()
    {
        resetActions();

        while (hasActionsLeft())
        {
            if (getInventory().goalHolder().size() == 0)
            {
                getInventory().addGoal(DrawStack.drawGoal(Enums.GoalType.GardenGoal));
                getInventory().addGoal(DrawStack.drawGoal(Enums.GoalType.BambooGoal));
            }
            if (getInventory().plotStates().size() > 0)
            {
                if (placePlotState())
                {
                    consumeAction();
                }
            }

            Logger.printSeparator(getName());
            Logger.printLine(getName() + " - goal = " + getGoalValidated(false).toString());

            if (placePlot()) consumeAction();

            if (new Random().nextBoolean())
            {
                moveGardener();
                consumeAction();
            }
            else
            {
                movePanda();
                consumeAction();
            }

            Logger.printLine(getName() + " - bamboos = " + getInventory().bambooHolder().size());
        }
    }
    //endregion
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