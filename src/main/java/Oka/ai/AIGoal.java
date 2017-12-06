package Oka.ai;

import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.entities.Entity;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Enums;
import Oka.model.Enums.Action;
import Oka.model.Enums.Color;
import Oka.model.Enums.GoalType;
import Oka.model.Enums.State;
import Oka.model.Irrigation;
import Oka.model.Pond;
import Oka.model.Vector;
import Oka.model.goal.*;
import Oka.model.plot.Plot;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.NeutralState;
import Oka.model.plot.state.PondState;
import Oka.utils.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static Oka.model.Enums.Action.*;
import static Oka.model.Enums.State.*;

/*..................................................................................................
 . Copyright (c)
 .
 . The AIGoal	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 23/11/17 09:47
 .................................................................................................*/

@SuppressWarnings ({"unused", "Duplicates", "ConstantConditions", "UnusedReturnValue", "SameParameterValue"})
public class AIGoal extends AI
{
    private int turn = 0;
    private Goal currentGoal;

    //region ============ Constructors ==========
    public AIGoal (String name)
    {
        super(name);
    }

    public AIGoal () {}
    //endregion

    //region ============ Implements ============

    @Override
    public void play ()
    {
        Logger.printLine(getName() + " - goal = " + getInventory().validatedGoals(false).toString());

        // 1 - Picks a goal if has to
        if (hasToPickGoal())
        {
            pickGoal();
        }

        // 2 - Copy goals and sort by ratio
        ArrayList<Goal> goals = new ArrayList<>(getInventory().goalHolder());
        goals.sort(Comparator.comparing(Goal::getRatio));

        Collections.reverse(goals);

        // 3 - Show not-validated goals
        // 4 - RollDice if not first turn
        if (turn++ != 0)
        {
            Dice.rollDice(this);
        }

        // 5 - For each goal (ordered by ratio) and while we didn't go over 30 attempts to play
        // 6 - Pick a new goal if has no more unvalidated goals or only has PlotGoals
        // 7 - Applies strategy according to current goal
        for (Goal goal : goals)
        {
            currentGoal = goal;

            if (currentGoal instanceof BambooGoal && bambooGoalStrategy()) continue;
            if (currentGoal instanceof GardenerGoal && gardenerGoalStrategy()) continue;
            if (currentGoal instanceof PlotGoal) plotGoalStrategy((PlotGoal) currentGoal);
        }

        placePlot();

        Logger.printLine(String.format("%s - bamboos : {GREEN :%d} {YELLOW :%d} {PINK :%d}",
                                       getName(),
                                       getInventory().bambooHolder().countBamboo(Color.GREEN),
                                       getInventory().bambooHolder().countBamboo(Color.YELLOW),
                                       getInventory().bambooHolder().countBamboo(Color.PINK)));

        getInventory().addTurnWithoutPickGoal();
        getInventory().resetActionHolder();
    }

    /**
     moves the panda to a tile of the desired color based on bamboogoal

     @return true if the action succeded, false otherwise
     */
    @Override
    public boolean movePanda ()
    {
        if (!getInventory().getActionHolder().hasActionsLeft(movePanda)) return false;

        List<Plot> plots = new ArrayList<>(GameBoard.getInstance().getPlots());
        Panda panda = Panda.getInstance();

        // 1 - Sort goals by ratio
        ArrayList<Goal> goals = new ArrayList<>(getInventory().goalHolder());
        goals.sort(Comparator.comparing(Goal::getRatio));

        // 2 - Try only for bambooGoals
        for (Goal goal : goals)
        {
            if (goal instanceof BambooGoal)
            {
                BambooGoal bambooGoal = (BambooGoal) goal;
                Color lookedForColor = bambooGoal.getColor(getInventory().bambooHolder());

                // 3 - Remove if wrong color
                // 4 - Remove if Panda can't access
                plots.removeIf(plot -> !plot.getColor().equals(lookedForColor));
                plots.removeIf(plot -> !GameBoard.getInstance().canMoveEntity(panda, plot.getCoords()));

                // 5 - Try to send panda on the plots if they have more than 0 bamboos
                for (Plot plot : plots)
                {
                    if (plot.getBamboo().size() > 0) return moveEntity(panda, plot.getCoords());
                }
            }
        }

        return false;
    }

    /**
     place a plot tile
     */
    @Override
    protected void placeBambooOnPlot ()
    {
        List<Plot> plots = new ArrayList<>(GameBoard.getInstance().getPlots());

        // 1 - Sort goals by ratio
        ArrayList<Goal> goals = new ArrayList<>(getInventory().goalHolder());
        goals.sort(Comparator.comparing(Goal::getRatio));

        for (Goal goal : goals)
        {
            //region if (goal instanceof BambooGoal)
            if (goal instanceof BambooGoal)
            {
                BambooGoal bambooGoal = (BambooGoal) goal;
                Color lookedForColor = bambooGoal.getColor(getInventory().bambooHolder());
                Panda panda = Panda.getInstance();

                // 1 - Remove if wrong color
                // 2 - Remove if not irrigated
                plots.removeIf(plot -> !plot.getColor().equals(lookedForColor));
                plots.removeIf(plot -> !plot.isIrrigated());

                // 2 - Try to addBamboo where panda has access
                for (Plot plot : plots)
                {
                    if (GameBoard.getInstance().canMoveEntity(panda, plot.getCoords()))
                    {
                        plot.addBamboo();
                        return;
                    }
                }

                plots.removeIf(plot -> !GameBoard.getInstance().canMoveEntity(panda, plot.getCoords()));

                // 3 - If couldn't place bamboo on plot accessible by panda, try another one
                if (!plots.isEmpty())
                {
                    plots.get(0).addBamboo();
                    Logger.printLine(getName() + " a placé un bamboo sur : " + plots.get(0));
                }
            }
            //endregion

            //region if (goal instanceof GardenerGoal)
            if (goal instanceof GardenerGoal)
            {
                GardenerGoal gardenerGoal = (GardenerGoal) goal;
                Color lookedForColor = gardenerGoal.getColor();

                // 1 - Remove if wrong color
                // 2 - Remove if not irrigated
                // 3 - Remove if already has enough or too many bamboos
                // 4 - Sort by interest level
                plots.removeIf(plot -> !plot.getColor().equals(lookedForColor));
                plots.removeIf(plot -> !plot.isIrrigated());
                plots.removeIf(plot -> {
                    int goalAmount = gardenerGoal.getBambooAmount();
                    int currentAmount = plot.getBamboo().size();

                    if (plot.getState() instanceof FertilizerState) return currentAmount <= goalAmount - 1;

                    return currentAmount <= goalAmount;
                });
                plots.sort((plot1, plot2) -> {
                    int val1 = plot1.getBamboo().size();
                    int val2 = plot2.getBamboo().size();

                    if (plot1.getState() instanceof FertilizerState) val1 += 2;
                    if (plot1.getState() instanceof EnclosureState) val1 += 1;

                    if (plot2.getState() instanceof FertilizerState) val2 += 2;
                    if (plot2.getState() instanceof EnclosureState) val2 += 1;

                    return Integer.compare(val1, val2);
                });

                // 5 - add bamboos to the plot first plot
                if (!plots.isEmpty())
                {
                    plots.get(0).addBamboo();
                    Logger.printLine(getName() + " a placé un bamboo sur : " + plots.get(0));
                }
            }
            //endregion
        }
    }

    @Override
    public boolean choosePlotState ()
    {
        ArrayList<Goal> goals = new ArrayList<>(getInventory().goalHolder());
        goals.sort(Comparator.comparing(Goal::getRatio));
        List<Plot> plots = new ArrayList<>(GameBoard.getInstance().getPlots());

        // 1 - GardenerGoal have priority over other goals
        for (Goal goal : goals.stream().filter(GardenerGoal.class::isInstance).map(GardenerGoal.class::cast).collect(Collectors.toList()))
        {
            GardenerGoal gardenerGoal = (GardenerGoal) goal;

            // 2 - If goal needs NeutralState, skip it
            if (gardenerGoal.getState().getState().equals(Neutral)) continue;

            // 3 - Clone the plots to avoid messing everything up...
            List<Plot> tmpPlots = new ArrayList<>(plots);

            // 4 - Remove if wrong color
            // 5 - Remove if has bamboo
            // 6 - Remove if has a PlotState
            tmpPlots.removeIf(plot -> !plot.getColor().equals(gardenerGoal.getColor()));
            tmpPlots.removeIf(plot -> !plot.getBamboo().isEmpty());
            tmpPlots.removeIf(plot -> !plot.getState().getState().equals(Neutral));

            if (tmpPlots.isEmpty())
            {
                if (pickPlotState(gardenerGoal.getState().getState()))
                {
                    return true;
                }
            }
        }

        // 7 - If no PlotState satisfies a GardenerGoal, pick either a fertilizer or a pond
        boolean coinFlip = new Random().nextBoolean();
        return coinFlip ? (pickPlotState(Pond) || pickPlotState(Fertilizer)) : (pickPlotState(Fertilizer) || pickPlotState(Pond));
    }

    /**
     Print all the objectives completed by this AI.
     */
    public void printObjectives ()
    {
        Logger.printTitle(this.getName() + " Objectifs validés :" + this.getInventory().validatedGoals(true));
    }

    //endregion

    //region ============ Strategies ============

    /**
     <hr>
     <h3>Tries to apply the adequate strategy when the most interesting
     goal is a BambooGoal, following these steps : (stops if one of the steps works)
     <ol>
     <li>Only consider plots of goal's color and accessible by the panda</li>
     <li>Try to send the panda on plots with bamboo</li>
     <li>Try to send the gardner, then the panda on plots without bamboo (if irrigated)</li>
     <li>Try to place an irrigation to irrigate the closest plot to the pond</li>
     </ol>
     </h3>
     <hr>

     @return True if an action could be done, false otherwise.
     */
    private boolean bambooGoalStrategy ()
    {
        if (!(currentGoal instanceof BambooGoal)) return false;

        BambooGoal bambooGoal = (BambooGoal) currentGoal;
        Color lookedForColor = bambooGoal.getColor(getInventory().bambooHolder());
        List<Plot> plots = new ArrayList<>(GameBoard.getInstance().getPlots());
        Gardener gardener = Gardener.getInstance();
        Panda panda = Panda.getInstance();

        // 1 - Remove if wrong color
        // 2 - Remove if Panda can't access
        plots.removeIf(plot -> !plot.getColor().equals(lookedForColor));
        plots.removeIf(plot -> !GameBoard.getInstance().canMoveEntity(panda, plot.getCoords()));

        // 3 - Try to send panda on the plots if they have more than 0 bamboos
        for (Plot plot : plots)
        {
            if (plot.getBamboo().size() > 0) return moveEntity(panda, plot.getCoords());
        }

        // 4 - Try to add bamboos to the plot
        for (Plot plot : plots)
        {
            // 4.1 - If it's irrigated, send Gardener, then Panda
            if (plot.isIrrigated() && canMoveEntity(gardener, plot.getCoords()) && canMoveEntity(panda, plot.getCoords()))
            {
                return moveEntity(gardener, plot.getCoords()) && moveEntity(panda, plot.getCoords());
            }
        }

        // 5 - Remove if plot is irrigated
        // They are useless if no previous
        // action could be made with them
        plots.removeIf(Plot::isIrrigated);
        if (plots.isEmpty()) return false;

        // 6 - We then try to irrigate the closest
        // interesting point to the Pond (easier to irrigate)
        if (getInventory().hasIrrigation() || drawIrrigation())
        {
            plots.sort(Comparator.comparing(plot -> Vector.findStraightVector(plot.getCoords(), new Pond().getCoords()).length()));

            for (Plot plot : plots)
            {
                if (placeIrrigation(plot)) return true;
            }
        }

        return false;
    }

    /**
     <hr>
     <h3>Tries to apply the adequate strategy when the most interesting
     goal is a BambooGoal, following these steps : (stops if one of the steps works)
     <ol>
     <li>Only consider plots of goal's color and accessible by the gardener</li>
     <li>Sort plots according to bamboo amount and PlotState</li>
     <li>Try to send the gardener the plots</li>
     <li>Try to place an irrigation to irrigate the closest plot to the pond</li>
     </ol>
     </h3>
     <hr>

     @return True if an action could be done, false otherwise.
     */
    private boolean gardenerGoalStrategy ()
    {
        if (!(currentGoal instanceof GardenerGoal)) return false;

        GardenerGoal gardenerGoal = (GardenerGoal) currentGoal;
        Color lookedForColor = gardenerGoal.getColor();
        List<Plot> plots = new ArrayList<>(GameBoard.getInstance().getPlots());
        Gardener gardener = Gardener.getInstance();

        // 1 - Remove if wrong color
        // 2 - Remove if gardener can't access
        // 3 - Remove if plots already have enough or too many bamboos
        // 4 - Sort plots by interest level
        plots.removeIf(plot -> !plot.getColor().equals(lookedForColor));
        plots.removeIf(plot -> !GameBoard.getInstance().canMoveEntity(gardener, plot.getCoords()));
        plots.removeIf(plot -> {
            int goalAmount = gardenerGoal.getBambooAmount();
            int currentAmount = plot.getBamboo().size();

            if (plot.getState() instanceof FertilizerState) return currentAmount <= goalAmount - 1;

            return currentAmount <= goalAmount;
        });
        plots.sort((plot1, plot2) -> {
            int val1 = plot1.getBamboo().size();
            int val2 = plot2.getBamboo().size();

            if (plot1.getState() instanceof FertilizerState) val1 += 2;
            if (plot1.getState() instanceof EnclosureState) val1 += 1;

            if (plot2.getState() instanceof FertilizerState) val2 += 2;
            if (plot2.getState() instanceof EnclosureState) val2 += 1;

            return Integer.compare(val1, val2);
        });

        // 5 - Try to add bamboos to the plot if it's irrigated
        for (Plot plot : plots)
        {
            if (plot.isIrrigated())
            {
                if (moveEntity(gardener, plot.getCoords()))
                {
                    return true;
                }
            }
        }

        // 5 - Remove if plot is irrigated
        // They are useless if no previous
        // action could be made with them
        plots.removeIf(Plot::isIrrigated);
        if (plots.isEmpty()) return false;

        // 6 - We then try to irrigate the closest
        // interesting point to the Pond (easier to irrigate)
        if (getInventory().hasIrrigation() || drawIrrigation())
        {
            plots.sort(Comparator.comparing(plot -> Vector.findStraightVector(plot.getCoords(), new Pond().getCoords()).length()));

            for (Plot plot : plots)
            {
                if (placeIrrigation(plot)) return true;
            }
        }

        return false;
    }

    private boolean plotGoalStrategy (PlotGoal plotGoal)
    {
        ArrayList<Map.Entry<Color, Point>> neededSpots = new ArrayList<>(plotGoal.neededSpots());

        ArrayList<Plot> drawnPlots = DrawStack.getInstance().giveTreePlot();
        if (drawnPlots == null) return false;

        Set<Color> drawnColors = drawnPlots.stream().map(Plot::getColor).collect(Collectors.toSet());

        for (Color color : Color.values())
        {
            neededSpots.removeIf(entity -> !drawnColors.contains(color) && entity.getKey().equals(color));
        }

        neededSpots.sort((o1, o2) -> {
            int value1 = plotGoal.completion(o1.getValue());
            int value2 = plotGoal.completion(o2.getValue());
            return Integer.compare(value1, value2);
        });

        for (Map.Entry<Color, Point> entry : neededSpots)
        {
            List<Plot> tmpPlots = drawnPlots.stream().filter(plot -> plot.getColor().equals(entry.getKey())).collect(Collectors.toList());
            sortByState(tmpPlots);

            for (Plot plot : tmpPlots)
            {
                if (placePlot(plot, entry.getValue()))
                {
                    drawnPlots.remove(plot);
                    DrawStack.getInstance().giveBackPlot(drawnPlots);
                    return true;
                }
            }
        }

        return false;
    }

    //endregion

    //region ============ Utils methods =========

    private boolean placePlot ()
    {
        // On vérifie si une case peut aider à faire avancer un GardenerGoal, puis un BambooGoal.
        // Dans ce cas, on vérifie également si on peut satisfaire un PlotGoal
        // sinon au plus près possible du Pond (plus facile à irriguer et plus facile d'accès

        // Dans le cas où les GardenerGoal et BambooGoal sont déjà sattisfait, choisir selon critère :
        // Couleur : Any
        // State : Pond > Fertilizer > Neutral > Enclorusre
        //
        // Dans ce cas, on vérifie également si on peut satisfaire un PlotGoal
        // sinon au plus près possible du Pond (plus facile à irriguer et plus facile d'accès

        if (!getInventory().getActionHolder().hasActionsLeft(placePlot)) return false;

        GameBoard gameBoard = GameBoard.getInstance();

        ArrayList<Plot> drawnPlots;
        ArrayList<Goal> goals = new ArrayList<>(getInventory().goalHolder());
        ArrayList<Plot> allPlots = new ArrayList<>(gameBoard.getPlots());
        ArrayList<PlotGoal> plotGoals = new ArrayList<>();
        ArrayList<BambooGoal> bambooGoals = new ArrayList<>();
        ArrayList<GardenerGoal> gardenerGoals = new ArrayList<>();
        goals.sort(Comparator.comparing(Goal::getRatio));
        Collections.reverse(goals);

        // 0 - Filtering goals
        goals.forEach(goal -> {
            if (goal instanceof BambooGoal) bambooGoals.add((BambooGoal) goal);
            if (goal instanceof GardenerGoal) gardenerGoals.add((GardenerGoal) goal);
            if (goal instanceof PlotGoal) plotGoals.add((PlotGoal) goal);
        });

        // 1 - Pick cards from DrawStack
        //region {...code...}

        drawnPlots = DrawStack.getInstance().giveTreePlot();
        if (drawnPlots == null) return false;
        sortByState(drawnPlots);

        //endregion

        // 2 - Get all neededSlots from all PlotGoals
        //region {...code...}
        HashSet<Map.Entry<Color, Point>> tmpNeededSlots = new HashSet<>();

        List<PlotGoal> list = new ArrayList<>();
        for (Goal goal1 : goals)
        {
            if (goal1 instanceof PlotGoal) list.add((PlotGoal) goal1);
        }

        list.stream().map(PlotGoal::neededSpots).forEach(tmpNeededSlots::addAll);

        ArrayList<Map.Entry<Color, Point>> neededSlots = new ArrayList<>(tmpNeededSlots);
        //endregion

        // 3 - Check if possible to please GardenerGoal
        //region {...code...}

        // --> 3.1 - For each GardenerGoal try to find best spot
        for (GardenerGoal gardenerGoal : gardenerGoals)
        {
            NeutralState neededState = gardenerGoal.getState();
            Color neededColor = gardenerGoal.getColor();

            for (Plot drawn : drawnPlots)
            {
                if (!drawn.getState().equals(neededState)) continue;
                if (!drawn.getColor().equals(neededColor)) continue;

                boolean foundState = allPlots.stream().anyMatch(plot -> plot.getState().equals(neededState));
                boolean foundColor = allPlots.stream().anyMatch(plot -> plot.getColor().equals(neededColor));
                if (foundState && foundColor) continue;

                List<Map.Entry<Color, Point>> localNeededSpots = new ArrayList<>();
                for (Map.Entry<Color, Point> neededSlot : neededSlots)
                {
                    if (neededSlot.getKey().equals(drawn.getColor())) localNeededSpots.add(neededSlot);
                }

                for (Map.Entry<Color, Point> entry : localNeededSpots)
                {
                    if (placePlot(drawn, entry.getValue()))
                    {
                        drawnPlots.remove(drawn);
                        DrawStack.getInstance().giveBackPlot(drawnPlots);
                        return true;
                    }
                }
            }
        }
        //endregion

        // 4 - Check if possible to please BambooGoal
        //region {...code...}

        // --> 4.1 - For each BambooGoal try to find best spot
        for (BambooGoal bambooGoal : bambooGoals)
        {
            Color neededColor = bambooGoal.getColor();

            for (Plot drawn : drawnPlots)
            {
                if (!drawn.getColor().equals(neededColor)) continue;
                if (allPlots.stream().anyMatch(plot -> plot.getColor().equals(neededColor))) continue;

                List<Map.Entry<Color, Point>> localNeededSpots = new ArrayList<>();
                for (Map.Entry<Color, Point> neededSlot : neededSlots)
                {
                    if (neededSlot.getKey().equals(drawn.getColor())) localNeededSpots.add(neededSlot);
                }

                for (Map.Entry<Color, Point> entry : localNeededSpots)
                {
                    if (placePlot(drawn, entry.getValue()))
                    {
                        drawnPlots.remove(drawn);
                        DrawStack.getInstance().giveBackPlot(drawnPlots);
                        return true;
                    }
                }
            }
        }
        //endregion

        // 5 - Check if possible to please PlotGoal
        //region {...code...}
        if (plotGoals.stream().anyMatch(this::plotGoalStrategy)) return true;
        //endregion

        // 6 - Go for random placement
        //region {...code...}
        ArrayList<Point> slots = GameBoard.getInstance().getAvailableSlots();

        Collections.shuffle(slots);
        Collections.shuffle(drawnPlots);

        for (Plot plot : drawnPlots)
        {
            for (Point point : slots)
            {
                if (placePlot(plot, point)) return true;
            }
        }

        //endregion
        return false;
    }

    private boolean placePlot (Plot plot)
    {
        ArrayList<Point> availableSlots = GameBoard.getInstance().getAvailableSlots();
        Collections.shuffle(availableSlots);

        return placePlot(plot, availableSlots.get(0));
    }

    private boolean placePlot (Plot plot, Point destination)
    {
        plot.setCoords(destination);
        GameBoard.getInstance().addCell(plot);

        Logger.printLine(getName() + " a placé : " + plot);
        getInventory().getActionHolder().consumeAction(Action.placePlot);

        return true;
    }

    private boolean hasToPickGoal ()
    {
        ArrayList<Goal> invalidGoals = getInventory().validatedGoals(false);

        boolean hasNoGoalLeft = invalidGoals.size() == 0;
        boolean onlyHasPlotGoals = invalidGoals.stream().allMatch(PlotGoal.class::isInstance);
        boolean isKindaStuck = getInventory().getTurnsWithoutPickGoal() > 10;

        return (hasNoGoalLeft || onlyHasPlotGoals || isKindaStuck) && invalidGoals.size() <= 5;
    }

    /**
     <hr>
     <h3>
     Place the irrigation on the plot parameter
     </h3>
     <hr>

     @param plot Plot to place an irrigation onto.
     @return true if an irrigation have been placed
     */
    private boolean placeIrrigation (Plot plot)
    {
        Objects.requireNonNull(plot);

        // 1 - If we don't have an irrigation to place, quit.
        if (!getInventory().hasIrrigation()) return false;

        // 2 - Find the closest irrigation slot available
        Irrigation irrig = GameBoard.getInstance().getClosestAvailableIrrigationSlot(plot);

        // 3 - Try to place the irrigation
        if (GameBoard.getInstance().addIrrigation(irrig.getPlot1().getCoords(), irrig.getPlot2().getCoords()))
        {
            getInventory().removeIrrigation();
            Logger.printLine(String.format("%s à placé une irrigation entre les deux plots suivants : %s %s",
                                           getName(),
                                           irrig.getPlot1().getCoords().toString(),
                                           irrig.getPlot2().getCoords().toString()));
            return true;
        }

        return false;
    }

    private boolean drawIrrigation ()
    {
        if (!getInventory().getActionHolder().hasActionsLeft(drawIrrigation)) return false;

        if (DrawStack.getInstance().drawIrrigation().isPresent())
        {
            getInventory().addIrrigation();
            getInventory().getActionHolder().consumeAction(drawIrrigation);
            return true;
        }

        return false;
    }

    /**
     <hr>
     <h3>
     1 - Tries to move the param entity to the param coords on the board<br>
     2 - Checks if still has action left to move param entity
     3 - Consumes the moving action of the param entity if it could be moved
     </h3>
     <hr>

     @param entity Panda or Gardener
     @param coords Coords where to move Entity
     @return True if the move succeeded (allowed by GameBoard)
     */
    private boolean moveEntity (Entity entity, Point coords)
    {
        Objects.requireNonNull(entity, "entity is null");
        Objects.requireNonNull(coords, "coords is null");

        Action action = null;
        if (entity instanceof Panda) action = movePanda;
        if (entity instanceof Gardener) action = moveGardener;

        if (!getInventory().getActionHolder().hasActionsLeft(action)) return false;

        if (GameBoard.getInstance().moveEntity(entity, coords))
        {
            Logger.printLine(getName() + " a déplacé le " + entity.getClass().getSimpleName() + " en : " + entity.getCoords());
            getInventory().getActionHolder().consumeAction(action);
            return true;
        }

        return false;
    }

    private boolean canMoveEntity (Entity entity, Point coords)
    {
        Objects.requireNonNull(entity, "entity is null");
        Objects.requireNonNull(coords, "coords is null");

        Action action = null;
        if (entity instanceof Panda) action = movePanda;
        if (entity instanceof Gardener) action = moveGardener;

        boolean hasActionLeft = getInventory().getActionHolder().hasActionsLeft(action);
        boolean canMove = GameBoard.getInstance().canMoveEntity(entity, coords);

        return hasActionLeft && canMove;
    }

    /**
     Picks a random goal from the DrawStackPlot

     @return true if everything went right (should always return true)
     */
    protected boolean pickGoal ()
    {
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.drawGoal)) return false;

        GoalType goalType = this.getInventory().getLessGoalType();

        getInventory().getActionHolder().consumeAction(Enums.Action.drawGoal);

        return pickGoal(goalType);
    }

    /**
     Picks a goal of [param: goalType] from DrawStackPlot

     @param goalType The desired GoalType
     @return true if everything went right (should always return true)
     */
    private boolean pickGoal (GoalType goalType)
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

    /**
     @return True if the AI succeeds to draw one of those plotstates ( pond/enclosure/fertilizer )
     <br> False if there is no more in the draw.
     */
    private boolean pickPlotState (State state)
    {
        Optional<NeutralState> optState = DrawStack.getInstance().drawState(state);

        if (optState.isPresent())
        {
            getInventory().addPlotState(optState.get());
            return true;
        }

        return false;
    }

    private void sortByState (List<Plot> list)
    {
        list.sort((p1, p2) -> {
            int p1Value = 2;
            int p2Value = 2;

            if (p1.getState() instanceof PondState) p1Value = 0;
            if (p2.getState() instanceof PondState) p2Value = 0;

            if (p1.getState() instanceof FertilizerState) p1Value = 1;
            if (p2.getState() instanceof FertilizerState) p2Value = 1;

            if (p1.getState() instanceof EnclosureState) p1Value = 3;
            if (p2.getState() instanceof EnclosureState) p2Value = 3;

            return Integer.compare(p1Value, p2Value);
        });
    }

    //endregion

    //region ============ Completion ============
    public double getCompletion (Goal goal)
    {
        if (goal instanceof BambooGoal) return getBambooGoalCompletion((BambooGoal) goal);

        if (goal instanceof GardenerGoalMultiPlot) return getGardenerGoalMultiPlotCompletion((GardenerGoalMultiPlot) goal);

        if (goal instanceof GardenerGoal) return getGardenerGoalCompletion((GardenerGoal) goal);

        if (goal instanceof PlotGoal) return ((PlotGoal) goal).toCompletion();

        return 0;
    }

    private double getBambooGoalCompletion (BambooGoal bambooGoal)
    {
        HashMap<Color, Integer> values = bambooGoal.getValues();

        float totalRequested = values.values().stream().reduce((i, i2) -> i + i2).orElse(0);
        float totalObtained = 0;

        for (Color color : values.keySet())
        {
            totalObtained += getInventory().bambooHolder().countBamboo(color) % values.get(color);
        }

        return totalObtained / totalRequested;
    }

    private double getGardenerGoalMultiPlotCompletion (GardenerGoalMultiPlot goalMultiPlot)
    {
        List<Plot> plots = GameBoard.getInstance().getPlots();
        List<Plot> tmpPlots = new ArrayList<>();

        for (int i = 0; i < goalMultiPlot.getPlotAmount(); i++)
        {
            float maxFound = 0;

            for (Plot plot : plots)
            {
                if (tmpPlots.contains(plot)) continue;
                if (!plot.getColor().equals(goalMultiPlot.getColor())) continue;
                if (plot.getBamboo().size() < maxFound) continue;

                maxFound = plot.getBamboo().size();
                tmpPlots.add(plot);
            }
        }

        float totalRequired = goalMultiPlot.getBambooAmount() * goalMultiPlot.getPlotAmount();
        float totalFound = tmpPlots.stream().mapToInt(plot -> plot.getBamboo().size()).sum();

        return totalFound / totalRequired;
    }

    private double getGardenerGoalCompletion (GardenerGoal gardenerGoal)
    {
        List<Plot> plots = GameBoard.getInstance().getPlots();

        float maxFound = 0;

        for (Plot plot : plots)
        {
            if (!plot.getColor().equals(gardenerGoal.getColor())) continue;
            if (plot.getBamboo().size() < maxFound) continue;

            maxFound = plot.getBamboo().size();
        }

        return maxFound / gardenerGoal.getBambooAmount();
    }
    //endregion
}