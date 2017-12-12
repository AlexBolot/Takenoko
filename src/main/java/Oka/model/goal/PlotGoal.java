package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Pond;
import Oka.model.Vector;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
/*..................................................................................................
 . Copyright (c)
 .
 . The PlotStateHolder	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 16/11/2017
 .................................................................................................*/

public class PlotGoal extends Goal
{
    private Enums.Color color;
    private HashMap<Vector, PlotGoal> linkedGoals = new HashMap<>();
    private boolean                   isSymetric  = false;

    public PlotGoal (int value, Enums.Color color)
    {
        super(value);
        this.color = color;

        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>();
        Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("blabla", 5);
        Map.Entry<String, Integer> entry2 = new AbstractMap.SimpleEntry<>("blabla", 6);
        Map.Entry<String, Integer> entry3 = new AbstractMap.SimpleEntry<>("blabla5", 5);
        Map.Entry<String, Integer> entry4 = new AbstractMap.SimpleEntry<>("blabla", 9);

        list.add(entry);
        list.add(entry2);
        list.add(entry3);
        list.add(entry4);

        list.get(0);
    }

    public PlotGoal (int value, Enums.Color color, HashMap<Vector, PlotGoal> linkedGoals)
    {
        this(value, color);
        this.linkedGoals = linkedGoals;
    }

    private static HashMap<Enums.Color, HashSet<Point>> reduceColorPointMap (HashMap<Enums.Color, HashSet<Point>> map1,
                                                                             HashMap<Enums.Color, HashSet<Point>> map2)
    {
        for (Enums.Color color : map2.keySet())
        {
            HashSet<Point> points = map2.get(color);
            if (map1.keySet().contains(color)) points.addAll(map1.get(color));
            map1.put(color, points);
        }
        return map1;
    }

    /**
     @param coords take coords of plot in the grid.
     @return true if it succeeds to validate a plotgoal.
     */
    @SuppressWarnings ("OptionalUsedAsFieldOrParameterType")
    public boolean validate (Optional<Point> coords)
    {
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();

        if (coords.isPresent())
        {
            Cell cell = grid.get(coords.get());

            return !(cell == null) && !cell.getCoords().equals(new Point()) && ((Plot) cell).getColor()
                                                                                            .equals(this.color) && ((Plot) cell).isIrrigated() && linkedGoals
                    .entrySet()
                    .stream()
                    .allMatch(entry -> entry.getValue().validate(Optional.of(entry.getKey().applyVector(coords.get()))));
        }

        boolean b = grid.keySet().stream().anyMatch(p -> this.validate(Optional.of(p)));
        if (!b && !this.isSymetric) b = this.symmetrics().stream().anyMatch(plotGoal -> plotGoal.validate(Optional.empty()));
        setValidated(b);
        return b;

    }

    public ArrayList<PlotGoal> symmetrics ()
    {
        ArrayList<PlotGoal> symetrics = new ArrayList<>();
        PlotGoal rotated = this.rotated();
        while (!equals(rotated))
        {
            rotated.setSymetric(true);
            symetrics.add(rotated);
            rotated = rotated.rotated();
        }
        return symetrics;
    }

    public PlotGoal rotated ()
    {
        HashMap<Vector, PlotGoal> rotatedSubGoals = new HashMap<>();
        this.linkedGoals.entrySet().forEach(entry -> {
            Vector v = entry.getKey().clone();
            v.rotateClockwize();
            rotatedSubGoals.put(v, entry.getValue().rotated());
        });
        PlotGoal rotated = new PlotGoal(this.getValue(), this.color, rotatedSubGoals);
        return rotated;
    }

    /**
     return the goals proximity to completion on a specific tile

     @param point Point from witch to analyze the completion rate
     @return completion measure
     */
    public int completion (Point point)
    {
        int completion = 0;
        // if coords a specified we should simply count from here
        GameBoard board = GameBoard.getInstance();

        //if we are on the pond we cannot validate
        if (point.equals(new Point())) return -1;

        List<Integer> subCompletions = this.linkedGoals.entrySet()
                                                       .stream()
                                                       .map(entry -> entry.getValue()
                                                                          .completion(entry.getKey()
                                                                                           .applyVector(point)))
                                                       .collect(Collectors.toList());

        // if the point is on the board we
        if (board.getGrid().containsKey(point))
        {
            Plot plot = (Plot) board.getGrid().get(point);
            if (plot.getColor().equals(this.color))
            {
                completion++;

                if (subCompletions.stream().anyMatch(value -> value == -1)) completion = -1;
                else completion += subCompletions.stream().reduce(0, Integer::sum);
            }
            else completion = -1;
        }
        else completion = subCompletions.stream().reduce(0, Integer::sum);

        return completion;
    }

    public boolean isSymetric ()
    {
        return isSymetric;
    }

    public void setSymetric (boolean is)
    {
        this.isSymetric = is;
    }

    /**
     @return
     */
    public int toCompletion ()
    {
        GameBoard board = GameBoard.getInstance();
        ArrayList<Point> points = new ArrayList<>(board.getGrid().keySet());

        points.addAll(board.getAvailableSlots());
        ArrayList<PlotGoal> goals = this.symmetrics();
        goals.add(this);

        int completion = points.stream()
                               .mapToInt(plot -> goals.stream().mapToInt(goal -> goal.completion(plot)).max().orElse(0))
                               .max()
                               .orElse(0);

        int toComplete = this.getSize() - completion;
        return toComplete <= 2 ? toComplete : 0;
    }

    public int getSize ()
    {
        return 1 + linkedGoals.values().stream().mapToInt(PlotGoal::getSize).sum();
    }

    public HashMap<Enums.Color, Integer> getColors ()
    {
        HashMap<Enums.Color, Integer> map = new HashMap<>();

        map.put(color, 1);

        for (PlotGoal plotGoal : linkedGoals.values())
        {
            Enums.Color color = plotGoal.color;

            if (map.containsKey(color)) map.replace(color, map.get(color) + 1);
            else map.put(color, 1);
        }

        return map;
    }

    public double getRatio ()
    {
        return sigmoid(getValue() / (getSize() * 2));
    }

    private HashSet<Map.Entry<Enums.Color, Point>> neededSpots (Point point)
    {
        if (point.equals(new Pond().getCoords())) return null;

        GameBoard board = GameBoard.getInstance();

        HashSet<Map.Entry<Enums.Color, Point>> childSpots = new HashSet<>();
        if (board.getGrid().containsKey(point) && !((Plot) board.getGrid().get(point)).getColor().equals(this.color))
            return null;
        if (!(this.getSize() - this.completion(point) <= 1))
            return null;
        for (Map.Entry<Vector, PlotGoal> entry : linkedGoals.entrySet())
        {
            HashSet<Map.Entry<Enums.Color, Point>> neededChilds = entry.getValue().neededSpots(entry.getKey().applyVector(point));
            if (neededChilds == null) return null;
            childSpots.addAll(neededChilds);
        }

        if (board.getAvailableSlots().contains(point))
        {
            childSpots.add(new AbstractMap.SimpleEntry<>(this.color, point));
        }
        return childSpots;

    }

    public HashSet<Map.Entry<Enums.Color, Point>> neededSpots ()
    {
        ArrayList<PlotGoal> goals = this.symmetrics();
        GameBoard board = GameBoard.getInstance();
        ArrayList<Point> slots = new ArrayList<>(board.getAvailableSlots());

        slots.addAll(board.getGrid().keySet());
        goals.add(this);

        try
        {
            return slots.stream().map(point -> goals.stream().map(plotGoal -> {

                boolean isPond = point.equals(new Pond().getCoords());
                HashSet<Map.Entry<Enums.Color, Point>> emptyList = new HashSet<>();
                HashSet<Map.Entry<Enums.Color, Point>> recurList = emptyList;

                if (!isPond) recurList = plotGoal.neededSpots(point);
                if (recurList == null) recurList = emptyList;

                return isPond ? emptyList : recurList;

            }).reduce((c1, c2) -> {
                c1.addAll(c2);
                return c1;
            })).reduce((c1, c2) -> {
                c1.orElse(new HashSet<>()).addAll(c2.orElse(new HashSet<>()));
                return c1;
            }).orElse(Optional.of(new HashSet<>())).orElse(new HashSet<>());
        }
        catch (IllegalArgumentException iae)
        {
            return new HashSet<>();
        }
    }

    @Override
    public boolean equals (Object obj)
    {
        if (!(obj instanceof PlotGoal)) return false;
        PlotGoal pg = ((PlotGoal) obj);
        boolean sameLinks = linkedGoals.keySet().stream().allMatch(vector -> pg.linkedGoals.keySet().contains(vector) && pg.linkedGoals.get(
                vector).equals(this.linkedGoals.get(vector)));
        return this.color.equals(pg.color) && this.getValue() == pg.getValue() && sameLinks;
    }

    @Override
    public String toString ()
    {
        StringBuilder b = new StringBuilder();

        b.append(getClass().getSimpleName());
        b.append("{").append(color.toString().substring(0, 2)).append("}");

        if (this.linkedGoals.size() > 0) linkedGoals.forEach((key, value) -> b.append(" ").append(key).append(" ").append(value));

        return b.toString();
    }
}
