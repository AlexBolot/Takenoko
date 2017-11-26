package Oka.ai;

import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.entities.Entity;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Irrigation;
import Oka.model.Vector;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;
import Oka.utils.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AIRandom extends AI {
    private int compteur = 0;
    
    public AIRandom(String name){
        super(name);
    }
    public AIRandom(){}

    @Override
    public void play() {
        Logger.printSeparator(getName());
        Logger.printLine(getName() + " - goal = " + getInventory().validatedGoals(false).toString());
        Random rand = new Random();

        getInventory().resetActionHolder();
        if (compteur++ != 0) Dice.rollDice(this);

        int turn = 0;
        while (getInventory().getActionHolder().hasActionsLeft())
        {
            if (turn++ > 30) break;

            //Pick a new goal if has no more unvalidated goals or only has PlotGoals
            if (rand.nextInt(1)==1)
            {
                /* For now we pick a random goal
                Action is consumed only if pickGoal could be achieved*/
                if (pickGoal()) continue;
            }

            //Plays its plotstates if has some
            if (getInventory().plotStates().size() > 0)
            {
                //Action is consumed only if plotState could be placed
                placePlotState();
            }
            //We Randomly chose to either move the gardener, the panda, or place an irrigation or placePlot1
            switch (new Random().nextInt(4))
            {
                case 0:
                    moveGardener();
                    continue;
                case 1:
                    movePanda();
                    continue;
                case 2:
                    if (drawChannel())
                    {
                        this.placeChannel();
                    }
                    continue;
                case 3:
                    placePlot();
            }
        }

        Logger.printLine(String.format("%s - bamboos : {GREEN :%d} {YELLOW :%d} {PINK :%d}",
                getName(),
                getInventory().bambooHolder().countBamboo(Enums.Color.GREEN),
                getInventory().bambooHolder().countBamboo(Enums.Color.YELLOW),
                getInventory().bambooHolder().countBamboo(Enums.Color.PINK)));

        getInventory().addTurnWithoutPickGoal();

    }

    private boolean placePlotState() {
        List<Plot> plots = GameBoard.getInstance().getPlots();

        getInventory().plotStates().get(0).getState();

        if(plots.size()>0){
            int randInt = new Random().nextInt(plots.size());
            plots.get(randInt).setState(getInventory().plotStates().get(0));

            //Logger.printLine(getName() + " upgraded : " + plot);
            Logger.printLine(getName() + " a placé l'aménagement sur : " + plots.get(randInt));

            getInventory().plotStates().remove(0);
            return true;
        }
        return false;
    }

    private boolean pickGoal() {
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.drawGoal)) return false;

        Enums.GoalType goalType = Enums.GoalType.values()[new Random().nextInt(3)];

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
        Optional<Goal> optionalGoal = DrawStack.getInstance().drawGoal(goalType);

        if (optionalGoal.isPresent())
        {
            getInventory().addGoal(optionalGoal.get());
            getInventory().resetTurnWithoutPickGoal();
            return true;
        }

        return false;
    }

    protected boolean moveGardener() {
        // TODO: optimise based on proximity to completion
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.moveGardener)) return false;

        int maxBamboo = 4;
        Gardener gardener = Gardener.getInstance();

        for (int bambooSize = 0; bambooSize < maxBamboo; bambooSize++)
        {
            if (moveEntity(gardener, bambooSize, Enums.Color.NONE))
            {
                Logger.printLine(getName() + " a déplacé le jardinier en : " + gardener.getCoords());
                getInventory().getActionHolder().consumeAction(Enums.Action.moveGardener);
                return true;
            }
        }

        return false;
    }

    protected boolean movePanda() {
        // TODO: optimise based on proximity to completion
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.movePanda)) return false;

        //Else, we go and find an interesting cell of the good color
        int maxBamboo = 4;
        Panda panda = Panda.getInstance();

        for (int bambooSize = maxBamboo; bambooSize >= 0; bambooSize--)
        {
            if (moveEntity(panda, bambooSize, Enums.Color.NONE))
            {
                Logger.printLine(getName() + " a déplacé le panda en : " + panda.getCoords());
                getInventory().getActionHolder().consumeAction(Enums.Action.movePanda);
                return true;
            }
        }

        return false;
    }

    protected boolean placePlot() {
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.placePlot)) return false;
        GameBoard board = GameBoard.getInstance();
        Random rand = new Random();
        ArrayList<Plot> draw;

        // On pioche trois parcelles si possible

        draw = DrawStack.getInstance().giveTreePlot();
        if (draw == null) return false;

        //On choisit un carte aléatoire parmis les trois car ou moins envoyé par la pioche plot
        int randInt = rand.nextInt(draw.size());
        Plot plot = draw.remove(randInt);

        // Toujours penser remettre les cartes dans la pioche après avoir pioché ;)
        DrawStack.getInstance().giveBackPlot(draw);

        ArrayList<Point> free = board.getAvailableSlots();
        plot.setCoords(free.get(/*rand.nextInt(free.size())*/0));
        board.addCell(plot);
        //Logger.printLine(getName() + " placed : " + plot);
        Logger.printLine(getName() + " a placé : " + plot);
        getInventory().getActionHolder().consumeAction(Enums.Action.placePlot);
        return true;
    }

    protected boolean drawChannel() {
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.drawChannel)) return false;

        Optional<Irrigation> irrigation = DrawStack.getInstance().drawChannel();

        if (!irrigation.isPresent()) return false;

        getInventory().addChannel();
        getInventory().getActionHolder().consumeAction(Enums.Action.drawChannel);
        return true;
    }

    protected boolean placeChannel() {
        if (!getInventory().hasChannel()) return false;

        GameBoard board = GameBoard.getInstance();
        Set<Irrigation> irrigations = board.getAvailableChannelSlots();
        if (irrigations.size() == 0) return false;

        Irrigation irg = (Irrigation) irrigations.toArray()[0/*new Random().nextInt(irrigations.size())*/];
        if (board.addIrrigation(irg.getPlot1().getCoords(), irg.getPlot2().getCoords()))
        {
            Logger.printLine(getName() + " à placé une irrigation entre les deux plots suivants : " + irg.getPlot1()
                    .getCoords()
                    .toString() + ' ' + irg.getPlot2()
                    .getCoords()
                    .toString());
            getInventory().removeChannel();

            return true;
        }
        return false;
    }

    protected boolean moveEntity(Entity entity, int bambooSize, Enums.Color color) {

        GameBoard gameBoard = GameBoard.getInstance();
        HashMap<Point, Cell> grid = gameBoard.getGrid();
        Point currentPoint = entity.getCoords();
        grid.keySet().removeIf(point -> !(point.equals(currentPoint) || !(grid.get(point) instanceof Plot))
                                            && Vector.areAligned(entity.getCoords(), point)
                                            &&  ((Plot)grid.get(point)).getBamboo().size() == bambooSize);

        if(grid.size()>0){
            int randInt = new Random().nextInt(grid.size());
            Cell cell = grid.get(grid.keySet().toArray()[randInt]);
            return entity.move(cell.getCoords());
        }
        return false;
    }

    /**
     *  Print all the objectives completed by this AI.
     */
    public void printObjectives ()
    {
        Logger.printTitle(this.getName() + " Objectifs validés :" + this.getInventory().validatedGoals(true));
    }

    @Override
    protected void placeBambooOnPlot() {
    }

    @Override
    public boolean choosePlotState() {
        return false;
    }
}
