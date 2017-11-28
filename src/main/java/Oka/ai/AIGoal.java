package Oka.ai;

import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.entities.Entity;
import Oka.entities.Gardener;
import Oka.entities.Panda;
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

@SuppressWarnings ("Duplicates")
public class AIGoal extends AI
{
    private int turn = 0;

    private Goal  currentGoal;
    private Color currentColor;

    //region ============ Constructors ==========
    public AIGoal (String name)
    {
        super(name);
    }

    public AIGoal () {}
    //endregion

    //region ============ Implements ============

    public void play ()
    {
        // 1 - Picks a goal if has to
        if (hasToPickGoal()) pickGoal();

        // 2 - Copy goals and sort by ratio
        ArrayList<Goal> goals = new ArrayList<>(getInventory().goalHolder());
        goals.sort(Comparator.comparing(Goal::getRatio));

        // 3 - Show not-validated goals
        // 4 - RollDice if not first turn
        printObjectives(false);
        if (turn++ != 0) Dice.rollDice(this);

        // 5 - For each goal (ordered by ratio) and while we didn't go over 30 attempts to play
        // 6 - Pick a new goal if has no more unvalidated goals or only has PlotGoals
        // 7 - Applies strategy according to current goal
        for (Goal goal : goals)
        {
            currentGoal = goal;

            if (currentGoal instanceof BambooGoal && bambooGoalStrategy()) continue;
            if (currentGoal instanceof GardenerGoal && gardenerGoalStrategy()) continue;
            if (currentGoal instanceof PlotGoal && plotGoalStrategy()) continue;
        }


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
    public boolean movePanda ()
    {
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
                plots.get(0).addBamboo();
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
                plots.get(0).addBamboo();

            }
            //endregion
        }
    }

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

    @Override
    public void printObjectives ()
    {

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
            if (plot.isIrrigated())
            {
                if (moveEntity(gardener, plot.getCoords()))
                {
                    if (moveEntity(panda, plot.getCoords()))
                    {
                        return true;
                    }
                }
            }
        }

        // 5 - Remove if plot is irrigated
        // They are useless if no previous
        // action could be made with them
        plots.removeIf(Plot::isIrrigated);

        // 6 - We then try to irrigate the closest
        // interesting point to the Pond (easier to irrigate)
        if (drawIrrigation())
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

        // 6 - We then try to irrigate the closest
        // interesting point to the Pond (easier to irrigate)
        if (drawIrrigation())
        {
            plots.sort(Comparator.comparing(plot -> Vector.findStraightVector(plot.getCoords(), new Pond().getCoords()).length()));

            for (Plot plot : plots)
            {
                if (placeIrrigation(plot)) return true;
            }
        }

        return false;
    }

    private boolean plotGoalStrategy ()
    {
        //TODO : Implement

        return false;
    }

    //endregion

    //region ============ Utils methods =========

    private boolean placePlot ()
    {
        if (!getInventory().getActionHolder().hasActionsLeft(placePlot)) return false;

        ArrayList<Goal> goals = new ArrayList<>(getInventory().goalHolder());
        goals.sort(Comparator.comparing(Goal::getRatio));
        ArrayList<Plot> draw = DrawStack.getInstance().giveTreePlot();

        if (draw == null) return false;

        HashMap<Color, Integer> colors = new HashMap<>();

        for (Goal goal : goals)
        {
            if (goal instanceof BambooGoal)
            {
                BambooGoal bbg = (BambooGoal) goal;
                bbg.getValues().keySet().forEach(color -> colors.merge(color, 1, (oldVal, newVal) -> oldVal + newVal));
                continue;
            }

            if (goal instanceof GardenerGoal)
            {
                GardenerGoal gg = (GardenerGoal) goal;
                colors.merge(gg.getColor(), 1, (oldVal, newVal) -> oldVal + newVal);
                continue;
            }

            if (goal instanceof PlotGoal)
            {
                PlotGoal pg = (PlotGoal) goal;
                pg.getColors().keySet().forEach(color -> colors.merge(color, 1, (oldVal, newVal) -> oldVal + newVal));
            }

        }

        for (Color color : colors.keySet())
        {
            if (draw.stream().noneMatch(plot -> plot.getColor().equals(color))) colors.replace(color, -1);
        }

        //noinspection ConstantConditions
        int max = colors.values().stream().max(Integer::compareTo).get();

        List<Color> selectedColors = colors.keySet().stream().filter(key -> colors.get(key) == max).collect(Collectors.toList());

        Optional<Plot> optPlot = draw.stream().filter(plot -> selectedColors.contains(plot.getColor())).findAny();

        //TODO : choisir quel plot placer

        return false;

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
     <h3>Tries to move the param entity to the param coords on the board
     </h3>
     <hr>

     @param entity Panda or Gardener
     @param coords Coords where to move Entity
     @return True if the move succeeded (allowed by GameBoard)
     */
    private boolean moveEntity (Entity entity, Point coords)
    {
        if (GameBoard.getInstance().moveEntity(entity, coords))
        {
            if (entity instanceof Panda) getInventory().getActionHolder().consumeAction(movePanda);
            if (entity instanceof Gardener) getInventory().getActionHolder().consumeAction(moveGardener);
            return true;
        }

        return false;
    }

    /**
     Picks a random goal from the DrawStackPlot

     @return true if a goal was found
     */
    private boolean pickGoal ()
    {
        if (!getInventory().getActionHolder().hasActionsLeft(drawGoal)) return false;

        GoalType[] values = GoalType.values();

        GoalType goalType = values[new Random().nextInt(values.length - 1)];

        getInventory().getActionHolder().consumeAction(drawGoal);

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

    private void printObjectives (boolean validated)
    {
        String text = validated ? "validés" : "non validés";

        Logger.printTitle(getName() + " Objectifs " + text + " :" + getInventory().validatedGoals(validated));
    }
    //endregion

    //region ============ Completion ============
    public double getCompletion (Goal goal)
    {
        if (goal instanceof BambooGoal) return getBambooGoalCompletion((BambooGoal) goal);

        if (goal instanceof GardenerGoalMultiPlot) return getGardenerGoalMultiPlotCompletion((GardenerGoalMultiPlot) goal);

        if (goal instanceof GardenerGoal) return getGardenerGoalCompletion((GardenerGoal) goal);

        if (goal instanceof PlotGoal) return getPlotGoalCompletion((PlotGoal) goal);

        return 0;
    }

    private double getBambooGoalCompletion (BambooGoal bambooGoal)
    {
        HashMap<Color, Integer> values = bambooGoal.getValues();

        //Ouais c'est pas fou je sais... #BlameBolot
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

    private double getPlotGoalCompletion (PlotGoal plotGoal)
    {
        //Todo : Implement -> no idea how to do it for now :/
        return 0;
    }
    //endregion
}