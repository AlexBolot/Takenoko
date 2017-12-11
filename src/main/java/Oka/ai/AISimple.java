package Oka.ai;

import Oka.ai.inventory.BambooHolder;
import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.entities.Entity;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Enums.Color;
import Oka.model.Enums.GoalType;
import Oka.model.Irrigation;
import Oka.model.goal.*;
import Oka.model.plot.Plot;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.PondState;
import Oka.utils.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings ("Duplicates")
public class AISimple extends AI
{
    private int compteur = 0;

    //region==========CONSTRUCTORS=========
    public AISimple (String name)
    {
        super(name);
    }

    public AISimple () {}
    //endregion

    //region==========METHODS==============
    public void play ()
    {
        Logger.printLine(getName() + " - goal false = " + getInventory().validatedGoals(false).toString());
        Logger.printLine(getName() + " - goal true  = " + getInventory().validatedGoals(true).toString());

        getInventory().resetActionHolder();
        if (compteur++ != 0) Dice.rollDice(this);

        int turn = 0;
        while (getInventory().getActionHolder().hasActionsLeft())
        {
            if (turn++ > 30) break;

            //Pick a new goal if has no more unvalidated goals or only has PlotGoals
            if (hasToPickGoal())
            {
                /* For now we pick a random goal
                Action is consumed only if pickGoal could be achieved*/
                if (pickGoal()) continue;
            }

            //Plays its plotstates if has some
            if (getInventory().plotStates().size() > 0)
            {
                //Action is consumed only if plotState could be placed
                if (placePlotState()) continue;
            }
            //We Randomly chose to either move the gardener, the panda, or place an irrigation or placePlot1
            switch (new Random().nextInt(4))
            {
                case 0:
                    moveGardener();
                    break;
                case 1:
                    movePanda();
                    break;
                case 2:
                    if (drawIrrigation())
                    {
                        this.placeIrrigation();
                    }
                    continue;
                case 3:
                    placePlot();
                    break;
            }
        }

        Logger.printLine(String.format("%s - bamboos : {GREEN :%d} {YELLOW :%d} {PINK :%d}",
                                       getName(),
                                       getInventory().bambooHolder().countBamboo(Color.GREEN),
                                       getInventory().bambooHolder().countBamboo(Color.YELLOW),
                                       getInventory().bambooHolder().countBamboo(Color.PINK)));

        getInventory().addTurnWithoutPickGoal();
    }

    /**
     @return True : if the AI doesn't succeed to validate some goal or if it doesn't have any goal in his inventory.
     <br> False : if the AI has goal and isn't stuck.
     */
    protected boolean hasToPickGoal ()
    {
        ArrayList<Goal> invalidGoals = getInventory().validatedGoals(false);

        boolean hasNoGoalLeft = invalidGoals.size() == 0;
        boolean onlyHasPlotGoals = invalidGoals.stream().allMatch(PlotGoal.class::isInstance);
        boolean isKindaStuck = getInventory().getTurnsWithoutPickGoal() > 5;

        return (hasNoGoalLeft || onlyHasPlotGoals || isKindaStuck) && invalidGoals.size() < 5;
    }

    /**
     @return True : if the AI succeeds to draw an irrigation
     <br> False : if the AI has no more action left or there is no irrigation left.
     */
    protected boolean drawIrrigation ()
    {
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.drawIrrigation)) return false;

        Optional<Irrigation> irrigation = DrawStack.getInstance().drawIrrigation();

        if (!irrigation.isPresent()) return false;

        getInventory().addIrrigation();
        getInventory().getActionHolder().consumeAction(Enums.Action.drawIrrigation);
        return true;
    }

    /**
     Place the irrigation in a random manner, mainly to the first available spots it detects

     @return true if an irrigation have been placed
     */
    protected boolean placeIrrigation ()
    {
        if (!getInventory().hasIrrigation()) return false;

        GameBoard board = GameBoard.getInstance();
        Set<Irrigation> irrigations = board.getAvailableIrrigationSlots();
        if (irrigations.size() == 0) return false;

        Optional<Color> color = findInterestingColor(this.getInventory().goalHolder());

        if (color.isPresent())
        {
            Color c = color.get();
            Set<Irrigation> interestingIrg = irrigations.stream()
                                                        .filter(irrigation -> (irrigation.getPlot1()
                                                                                         .getColor()
                                                                                         .equals(c) || irrigation.getPlot2()
                                                                                                                 .getColor()
                                                                                                                 .equals(c)))
                                                        .collect(Collectors.toSet());
            if (interestingIrg.size() != 0)
            {
                Irrigation irg = (Irrigation) interestingIrg.toArray()[0];

                if (board.addIrrigation(irg.getPlot1().getCoords(), irg.getPlot2().getCoords()))
                {
                    getInventory().removeIrrigation();
                    Logger.printLine(getName() + " à placé une irrigation entre les deux plots suivants : " + irg.getPlot1()
                                                                                                                 .getCoords()
                                                                                                                 .toString() + ' ' + irg.getPlot2()
                                                                                                                                        .getCoords()
                                                                                                                                        .toString());
                    return true;
                }
            }
        }
        Irrigation irg = (Irrigation) irrigations.toArray()[0];
        if (board.addIrrigation(irg.getPlot1().getCoords(), irg.getPlot2().getCoords()))
        {
            Logger.printLine(getName() + " à placé une irrigation entre les deux plots suivants : " + irg.getPlot1()
                                                                                                         .getCoords()
                                                                                                         .toString() + ' ' + irg.getPlot2()
                                                                                                                                .getCoords()
                                                                                                                                .toString());
            getInventory().removeIrrigation();

            return true;
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

        Optional<Color> lookedForColor = findInterestingColor(goals, this.getInventory().bambooHolder());

        int maxBamboo = 4;
        Gardener gardener = Gardener.getInstance();

        if (lookedForColor.isPresent())
        {
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
        if (!getInventory().getActionHolder().hasActionsLeft(Enums.Action.movePanda)) return false;
        List<Goal> goals = getInventory().validatedGoals(false)
                                         .stream()
                                         .filter(goal -> goal instanceof BambooGoal)
                                         .collect(Collectors.toList());

        Optional<Color> lookedForColor = findInterestingColor(goals, this.getInventory().bambooHolder());


        //Else, we go and find an interesting cell of the good color
        int maxBamboo = 4;
        Panda panda = Panda.getInstance();
        if (lookedForColor.isPresent())
        {
            for (int bambooSize = maxBamboo; bambooSize > 0; bambooSize--)
            {
                if (moveEntity(panda, bambooSize, lookedForColor.get()))
                {
                    Logger.printLine(getName() + " a déplacé le panda en : " + panda.getCoords());
                    getInventory().getActionHolder().consumeAction(Enums.Action.movePanda);
                    return true;
                }
            }
        }
        Color color = this.getInventory().bambooHolder().getLessColorBamboo();
        for (int bambooSize = maxBamboo; bambooSize > 0; bambooSize--)
        {
            if (moveEntity(panda, bambooSize, color))
            {
                Logger.printLine(getName() + " a déplacé le panda en : " + panda.getCoords());
                getInventory().getActionHolder().consumeAction(Enums.Action.movePanda);
                return true;
            }
        }

        return false;
    }

    /**
     place a bamboo on a plot
     */
    protected void placeBambooOnPlot ()
    {
        List<Goal> goals = getInventory().validatedGoals(false)
                                         .stream()
                                         .filter(goal -> goal instanceof BambooGoal || goal instanceof GardenerGoal)
                                         .collect(Collectors.toList());

        Optional<Color> lookedForColor = findInterestingColor(goals);
        List<Plot> plots = GameBoard.getInstance().getPlots();

        if (lookedForColor.isPresent())
        {
            for (Plot plot : plots)
            {
                if (plot.getColor().equals(lookedForColor.get()) && plot.getBamboo().size() == 0 && plot.isIrrigated())
                {
                    plot.addBamboo();
                    Logger.printLine(getName() + " a placé un bamboo sur : " + plot);
                    return;
                }
            }

        }
    }

    /**
     @return True if the AI place a plot.
     <br> False if there is no more plot available to draw & if there is no more action left.
     */
    public boolean placePlot ()
    {
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
        plot.setCoords(free.get(0));
        board.addCell(plot);

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

            if (entity instanceof Panda)
            {
                if ((plot.getState().equals(new EnclosureState()))) continue;
                if (this.getInventory().bambooHolder().countBamboo(plot.getColor()) > 10) continue;
            }

            if (plot.getBamboo().size() == bambooSize && checkColor)
            {
                if (gameBoard.moveEntity(entity, point)) return true;
            }
        }

        return false;
    }

    /**
     @return True if the AI place a plotstate.
     <br> False if there are no color matching with AI's goal.
     */
    protected boolean placePlotState ()
    {
        // TODO: optimise based on proximity to completion

        Optional<Color> lookedForColor;
        List<Plot> plots = GameBoard.getInstance().getPlots();

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

            //region default :
            default:
                lookedForColor = Optional.empty();
                //endregion
        }
        if (!lookedForColor.isPresent()) return false;

        //Else, we go and find an interesting cell of the good color
        for (Plot plot : plots)
        {
            if (plot.getColor().equals(lookedForColor.get()) && plot.getBamboo().size() == 0)
            {
                plot.setState(getInventory().plotStates().get(0));

                //Logger.printLine(getName() + " upgraded : " + plot);
                Logger.printLine(getName() + " a placé l'aménagement sur : " + plot);

                getInventory().plotStates().remove(0);
                return true;
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

        GoalType goalType = this.getInventory().getLessGoalType();

        getInventory().getActionHolder().consumeAction(Enums.Action.drawGoal);

        return pickGoal(goalType);
    }

    /**
     Picks a goal of [param: goalType] from DrawStackPlot

     @param goalType The desired GoalType
     @return true if everything went right (should always return true)
     */
    protected boolean pickGoal (GoalType goalType)
    {
        Optional<Goal> optionalGoal = DrawStack.getInstance().drawGoal(goalType);

        if (optionalGoal.isPresent())
        {
            getInventory().addGoal(optionalGoal.get());
            getInventory().resetTurnWithoutPickGoal();

            Logger.printLine(getName() + " a pioché un objectif : " + optionalGoal.get());
            return true;
        }

        return false;
    }

    /**
     <h2>Finds the most interesting color to look for in the list of goals</h2>
     <h3>— To test if a color was found : [result].isPresent() <br>
     — To get the color : [result].get()</h3>

     @param goals list of goals to be looked at
     @return The most interseting color if found, otherwise returns Optional.empty()
     */
    private Optional<Color> findInterestingColor (List<Goal> goals)
    {
        //We sort goals by completion to get the closest to completion first
        List<Goal> goalList = goals.stream().sorted(Comparator.comparingDouble(this::getCompletion)).collect(Collectors.toList());

        for (Goal goal : goalList)
        {
            /* If goal is Bamboo or Gadener type (= has a color)
            it becomes the color we look for, else keep searching */

            if (goal instanceof BambooGoal)
            {
                return Optional.of(((BambooGoal) goal).getColor());
            }

            if (goal instanceof GardenerGoal)
            {
                return Optional.of(((GardenerGoal) goal).getColor());
            }

        }

        return Optional.empty();
    }

    private Optional<Color> findInterestingColor (List<Goal> goals, BambooHolder bambooHolder)
    {
        //We sort goals by completion to get the closest to completion first
        List<Goal> goalList = goals.stream().sorted(Comparator.comparingDouble(this::getCompletion)).collect(Collectors.toList());

        for (Goal goal : goalList)
        {
            /* If goal is Bamboo or Gadener type (= has a color)
            it becomes the color we look for, else keep searching */

            if (goal instanceof BambooGoal)
            {
                Color c = ((BambooGoal) goal).getColor(bambooHolder);
                if (c == null) return Optional.empty();
                else return Optional.of(c);
            }

            if (goal instanceof GardenerGoal)
            {
                return Optional.of(((GardenerGoal) goal).getColor());
            }

        }

        return Optional.empty();
    }

    /**
     @param goal take goal of the AI
     @return the proportion of completion of this goal.
     */
    public double getCompletion (Goal goal)
    {
        if (goal instanceof BambooGoal) return getBambooGoalCompletion((BambooGoal) goal);

        if (goal instanceof GardenerGoalMultiPlot) return getGardenerGoalMultiPlotCompletion((GardenerGoalMultiPlot) goal);

        if (goal instanceof GardenerGoal) return getGardenerGoalCompletion((GardenerGoal) goal);

        if (goal instanceof PlotGoal) return getPlotGoalCompletion((PlotGoal) goal);

        return 0;
    }

    /**
     @param bambooGoal take BambooGoal of the AI.
     @return The proportion of completion of a bamboogoal.
     */
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

    /**
     @param goalMultiPlot take GoalMultiPlot of the AI.
     @return The proportion of completion of a GoalMultiPlot.
     */
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

    /**
     @param gardenerGoal take GardenerGoal of the AI.
     @return the proportion of completion of a GardernerGoal.
     */
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

    /**
     @param plotGoal take a PlotGoal of the AI.
     @return The proportion of completion of a plotGoal.
     */
    private double getPlotGoalCompletion (PlotGoal plotGoal)
    {
        //Todo : Implement -> no idea how to do it for now :/
        return 0;
    }

    /**
     Print all the objectives completed by this AI.
     */
    @Override
    public void printObjectives ()
    {
        Logger.printTitle(this.getName() + " Objectifs validés :" + this.getInventory().validatedGoals(true));
    }

    /**
     @return True if the AI succeeds to draw one of those plotstates ( pond/enclosure/fertilizer )
     <br> False if there is no more in the draw.
     */
    public boolean choosePlotState ()
    {
        Enums.State[] values = Enums.State.values().clone();
        ArrayList<Enums.State> valuesarray = new ArrayList<>(Arrays.asList(values));
        valuesarray.remove(Enums.State.Neutral);
        Collections.shuffle(valuesarray);
        for (Enums.State state : valuesarray)
        {
            switch (state)
            {
                case Pond:
                    Optional<PondState> optPond = DrawStack.getInstance().drawPondState();
                    optPond.ifPresent(pondState -> getInventory().addPlotState(pondState));
                    if (optPond.isPresent()) return true;
                    break;

                case Enclosure:
                    Optional<EnclosureState> optEnclosure = DrawStack.getInstance().drawEnclosureState();
                    optEnclosure.ifPresent(enclosureState -> getInventory().addPlotState(enclosureState));
                    if (optEnclosure.isPresent()) return true;
                    break;

                case Fertilizer:
                    Optional<FertilizerState> optFertilizer = DrawStack.getInstance().drawFertilizerState();
                    optFertilizer.ifPresent(fertilizerState -> getInventory().addPlotState(fertilizerState));
                    if (optFertilizer.isPresent()) return true;
                    break;
            }
        }
        return false;


    }
    //endregion
}