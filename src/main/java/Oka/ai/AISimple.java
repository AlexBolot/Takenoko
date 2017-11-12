package Oka.ai;

import Oka.ai.inventory.ActionHolder;
import Oka.ai.inventory.PlotStateHolder;
import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.entities.Entity;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Enums.Color;
import Oka.model.Irrigation;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.PondState;
import Oka.utils.Logger;
import com.sun.javaws.exceptions.InvalidArgumentException;
import sun.rmi.runtime.Log;

import javax.swing.text.html.Option;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AISimple extends AI
{
    //region==========CONSTRUCTORS=========
    public AISimple (String name)
    {
        super(name);

        Enums.State[] values = Enums.State.values();

    }
    //endregion

    //region==========METHODS==============
    public void play ()
    {
        getInventory().resetActionHolder();
        Logger.printSeparator(getName());
        Dice.rollDice(this);
        Logger.printLine(getName() + " - goal = " + getInventory().validatedGoals(false).toString());
        Logger.printLine(""+getInventory().getActionHolder().getActionLeft());
        while (getInventory().getActionHolder().hasActionsLeft())
        {
            //Pick a new goal if has no more unvalidated goals
            if (getInventory().validatedGoals(false).size() == 0)
            {
                /* For now we pick a random goal
                Action is consumed only if pickGoal could be achieved*/
                if(pickGoal()) continue;
            }

            //Plays its plotstates if has some
            if (getInventory().plotStates().size() > 0)
            {
                //Action is consumed only if plotState could be placed
                placePlotState();
            }
                //We Randomly chose to either move the gardener, the panda, or place an irrigation or placePlot1
                switch (new Random().nextInt(4)) {
                    case 0:
                        moveGardener();
                        continue;
                    case 1:
                        movePanda();
                        continue;
                    case 2:
                        if (drawChannel()) {
                            placeChannel();
                        }
                        continue;
                    case 3:
                        placePlot();

                }


        }
        Logger.printLine(getName() + " - bamboos : {GREEN :" + getInventory().bambooHolder().countBamboo(Color.GREEN) +"} " +
                 "{YELLOW :" + getInventory().bambooHolder().countBamboo(Color.YELLOW) +"} "
        + "{PINK :" + getInventory().bambooHolder().countBamboo(Color.PINK) + "}");

    }

    protected boolean drawChannel() {
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.drawChannel)) return false;
        Optional<Irrigation> irrigation = DrawStack.getInstance().drawChannel();
        if (!irrigation.isPresent()) return false;
        getInventory().addChannel();
        getInventory().getActionHolder().consumeAction(Enums.Action.drawChannel);
        return true;
    }

    /**
     * Place the channel in a random manner, mainly to the first available spots it detects
     *
     * @return true if a channel have been placed
     */
    protected boolean placeChannel() {
        if (!getInventory().hasChannel()) return false;

        GameBoard board = GameBoard.getInstance();
        Set<Irrigation> irrigations = board.getAvailableChannelSlots();
        if(irrigations.size()==0)
            return false;
        Optional<Color> color = findInterestingColor(this.getInventory().goalHolder());

        if (color.isPresent()) {
            Color c = color.get();
            Set<Irrigation> interestingIrg = irrigations.
                    stream()
                    .filter(irrigation ->
                            (irrigation.getPlot1().getColor().equals(c) || irrigation.getPlot2().getColor().equals(c)))
                    .collect(Collectors.toSet());
            if (interestingIrg.size() != 0) {
                Irrigation irg = (Irrigation) interestingIrg.toArray()[0];
                try {
                    board.addIrrigation(irg.getPlot1().getCoords(), irg.getPlot2().getCoords());
                    getInventory().removeChannel();
                    Logger.printLine(getName() + " à placé une irrigation entre les deux plots suivants : " +irg.getPlot1().getCoords().toString() +' '+ irg.getPlot2().getCoords().toString());
                    return true;
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        Irrigation irg = (Irrigation) irrigations.toArray()[0];
        try {
            board.addIrrigation(irg.getPlot1().getCoords(), irg.getPlot2().getCoords());
            Logger.printLine(getName() + " à placé une irrigation entre les deux plots suivants : "+irg.getPlot1().getCoords().toString() +' '+ irg.getPlot2().getCoords().toString());
            getInventory().removeChannel();
            return true;
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }


        return false;
    }

    /**
     moves the gardener to a desired spot
     chooses the spot based on the current bamboo objective

     @return true if it managed to move false otherwise
     */
    public boolean moveGardener ()
    {
        // TODO: optimise based on proximity to completion
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.moveGardener)) return false;
        List<Goal> goals = getInventory().validatedGoals(false)
                                         .stream()
                                         .filter(goal -> goal instanceof BambooGoal || goal instanceof GardenerGoal)
                                         .collect(Collectors.toList());

        Optional<Color> lookedForColor = findInterestingColor(goals);

        /* If we didn't find a color to look for,
        no point in moving the gardener */
        if (!lookedForColor.isPresent()) return false;

        //Else, we go and find an interesting cell of the good color
        int maxBamboo = 4;
        Gardener gardener = Gardener.getInstance();

        for (int bambooSize = 0; bambooSize < maxBamboo; bambooSize++)
        {
            if (moveEntity(gardener, bambooSize, lookedForColor.get()))
            {
                //Logger.printLine(getName() + " moved gardener : " + gardener.getCoords());
                Logger.printLine(getName() + " a déplacé le jardinier en : " + gardener.getCoords());
                getInventory().getActionHolder().consumeAction(Enums.Action.moveGardener);
                return true;
            }
        }

        for (int bambooSize = 0; bambooSize < maxBamboo; bambooSize++)
        {
            if (moveEntity(gardener, bambooSize, Color.NONE))
            {
                //Logger.printLine(getName() + " moved gardener : " + gardener.getCoords());
                Logger.printLine(getName() + " a déplacé le jardinier en : " + gardener.getCoords());
                getInventory().getActionHolder().consumeAction(Enums.Action.moveGardener);
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
        // TODO: optimise based on proximity to completion
        if  (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.movePanda)) return false;
        List<Goal> goals = getInventory().validatedGoals(false)
                                         .stream()
                                         .filter(goal -> goal instanceof BambooGoal)
                                         .collect(Collectors.toList());

        Optional<Color> lookedForColor = findInterestingColor(goals);

        /* If we didn't find a color to look for,
        no point in moving the gardener */
        if (!lookedForColor.isPresent()) return false;

        //Else, we go and find an interesting cell of the good color
        int maxBamboo = 4;
        Panda panda = Panda.getInstance();

        for (int bambooSize = maxBamboo; bambooSize >= 0; bambooSize--)
        {
            if (moveEntity(panda, bambooSize, lookedForColor.get()))
            {
              //  Logger.printLine(getName() + " moved panda : " + panda.getCoords());
                Logger.printLine(getName() + " a déplacé le panda en : " + panda.getCoords());
                getInventory().getActionHolder().consumeAction(Enums.Action.movePanda);
                return true;
            }
        }
        for (int bambooSize = maxBamboo; bambooSize >= 0; bambooSize--)
        {
            if (moveEntity(panda, bambooSize, Color.NONE))
            {
             //   Logger.printLine(getName() + " moved panda : " + panda.getCoords());
                Logger.printLine(getName() + " a déplacé le panda en : " + panda.getCoords());
                getInventory().getActionHolder().consumeAction(Enums.Action.movePanda);
                return true;
            }
        }

        return false;
    }

    /**
     place a plot tile
     */
     protected void placeBambooOnPlot()
    {
        List<Goal> goals = getInventory().validatedGoals(false)
                .stream()
                .filter(goal -> goal instanceof BambooGoal || goal instanceof GardenerGoal)
                .collect(Collectors.toList());

        Optional<Color> lookedForColor = findInterestingColor(goals);
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();

        if (lookedForColor.isPresent()){
            for (Cell cell : grid.values())
            {
                if (cell instanceof Plot)
                {
                    Plot plot = (Plot) cell;

                    if (plot.getColor().equals(lookedForColor.get()) && plot.getBamboo().size() == 0 && plot.isIrrigated())
                    {
                        plot.addBamboo();
                        Logger.printLine(getName() + " a placé un bamboo sur : " + plot);
                        return;
                    }
                }
            }

        }

    }

    public boolean placePlot ()
    {
        if  (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.placePlot)) return false;
        GameBoard board = GameBoard.getInstance();
        Random rand = new Random();
        ArrayList<Plot> draw;

        // On pioche trois parcelles si possible

        draw = DrawStack.getInstance().giveTreePlot();
        if (draw == null) return false;

        //On choisit un carte aléatoire parmis les trois car ou moins envoyé par la pioche plot
        int randInt;
        if (draw.size() >= 3) randInt = rand.nextInt(3);
        else randInt = rand.nextInt(draw.size());
        Plot plot = draw.get(randInt);

        // Toujours penser remettre les cartes dans la pioche après avoir pioché ;)
        draw.remove(randInt);
        DrawStack.getInstance().giveBackPlot(draw);

        //todo: add a available slot function
        ArrayList<Point> free = board.getAvailableSlots();
        plot.setCoords(free.get(0));
        board.addCell(plot);
        //Logger.printLine(getName() + " placed : " + plot);
        Logger.printLine(getName() + " a placé : " + plot);
        getInventory().getActionHolder().consumeAction(Enums.Action.placePlot);
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

            boolean checkColor = (color.equals(plot.getColor()) || color.equals(Color.NONE));

            if (plot.getBamboo().size() == bambooSize && checkColor && gameBoard.moveEntity(entity, point))
            {
                return true;
            }
        }

        return false;
    }

    protected boolean placePlotState ()
    {
        // TODO: optimise based on proximity to completion

        Optional<Color> lookedForColor;
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();

        List<Goal> goals;

        switch (getInventory().plotStates().get(0).getState())
        {
            //region case Pond :
            case Pond:
                goals = this.getInventory()
                            .validatedGoals(false)
                            .stream()
                            .filter(c -> c instanceof BambooGoal)//we take only the bamboogoals
                            .collect(Collectors.toList());//we get back a collection of them

                lookedForColor = findInterestingColor(goals);
                break;
            //endregion

            //region case Enclosure :
            case Enclosure:
                goals = this.getInventory()
                            .validatedGoals(false)
                            .stream()
                            .filter(c -> c instanceof GardenerGoal)//we take only the bamboogoals
                            .collect(Collectors.toList());//we get back a collection of them

                lookedForColor = findInterestingColor(goals);
                break;
            //endregion

            //region case Fertilizer :
            case Fertilizer:
                goals = this.getInventory().validatedGoals(false);

                lookedForColor = findInterestingColor(goals);
                break;
            //endregion

            //region defaul :
            default:
                lookedForColor = Optional.empty();
                //endregion
        }

        /* If we didn't find a color to look for,
        no point in moving the gardener */
        if (!lookedForColor.isPresent()) return false;

        //Else, we go and find an interesting cell of the good color
        for (Cell cell : grid.values())
        {
            if (cell instanceof Plot)
            {
                Plot plot = (Plot) cell;

                if (plot.getColor().equals(lookedForColor.get()) && plot.getBamboo().size() == 0)
                {
                    plot.setState(getInventory().plotStates().get(0));

                    //Logger.printLine(getName() + " upgraded : " + plot);
                    Logger.printLine(getName() + " a placé l'aménagement sur : " + plot);

                    getInventory().plotStates().remove(0);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     Picks a random goal from the DrawStackPlot

     @return true if everything went right (should always return true)
     */
    protected boolean pickGoal ()
    {
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.drawGoal)) return false;
        Enums.GoalType[] values = Enums.GoalType.values();
        Enums.GoalType goalType = values[new Random().nextInt(values.length)];
        getInventory().getActionHolder().consumeAction(Enums.Action.drawGoal);
        return pickGoal(goalType);
    }

    /**
     Picks a goal of [param: goalType] from DrawStackPlot

     @param goalType The desired GoalType
     @return true if everything went right (should always return true)
     */
    protected boolean pickGoal (Enums.GoalType goalType)
    {
        Goal goal = DrawStack.getInstance().drawGoal(goalType);

        getInventory().addGoal(goal);

        return true;
    }

    /**
     <h2>Finds the most interesting color to look for in the list of goals</h2>
     <h3>— To test if a color was found : [result].isPresent() <br>
     — To get the color : [result].get()</h3>

     @param goals list of goals to be looked at
     @return The most interseting color if found, otherwise returns Optional.empty()
     */
    private Optional<Color> findInterestingColor (List<Goal> goals) {
        for (Goal goal : goals) {
            /* If goal is Bamboo or Gadener type (= has a color)
            it becomes the color we look for, else keep searching */

            if (goal instanceof BambooGoal) return Optional.of(((BambooGoal) goal).getColor());

            if (goal instanceof GardenerGoal) return Optional.of(((GardenerGoal) goal).getColor());

        }

        return Optional.empty();
    }
    //endregion


}