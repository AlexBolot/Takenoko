package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Vector;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
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
    }

    public PlotGoal (int value, Enums.Color color, HashMap<Vector, PlotGoal> linkedGoals)
    {
        this(value, color);
        this.linkedGoals = linkedGoals;
    }

    @SuppressWarnings ("OptionalUsedAsFieldOrParameterType")
    public boolean validate (Optional<Point> coords)
    {
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();

        if (coords.isPresent())
        {
            Cell cell = grid.get(coords.get());

            return !(cell == null) && !cell.getCoords().equals(new Point()) && ((Plot) cell).getColor().equals(this.color) && ((Plot) cell).isIrrigated() && linkedGoals.entrySet()
                                                                                                                                                                        .stream()
                                                                                                                                                                        .allMatch(
                                                                                                                                                                                entry -> entry.getValue()
                                                                                                                                                                                              .validate(Optional.of(entry.getKey().applyVector(coords.get()))));
        }

        boolean b = grid.keySet().stream().anyMatch(p -> this.validate(Optional.of(p)));
        if (!b && !this.isSymetric)
            b = this.symmetrics().stream().anyMatch(plotGoal -> plotGoal.validate(Optional.empty()));
        setValidated(b);
        return b;

    }

    public ArrayList<PlotGoal> symmetrics() {
        ArrayList<PlotGoal> symetrics = new ArrayList<>();
        PlotGoal rotated = this.rotated();
        while (!equals(rotated)) {
            rotated.setSymetric(true);
            symetrics.add(rotated);
            rotated = rotated.rotated();
        }
        return symetrics;
    }


    public PlotGoal rotated() {
        HashMap<Vector, PlotGoal> rotatedSubGoals = new HashMap<>();
        this.linkedGoals.entrySet().forEach(entry -> {
            Vector v = entry.getKey().clone();
            v.rotateClockwize();
            rotatedSubGoals.put(v, entry.getValue().rotated());
        });
        PlotGoal rotated = new PlotGoal(this.getValue(), this.color, rotatedSubGoals);
        return rotated;
    }



    public boolean isSymetric ()
    {
        return isSymetric;
    }

    public void setSymetric (boolean is)
    {
        this.isSymetric = is;
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
        StringBuilder b = new StringBuilder(getClass().getSimpleName() + " ->value : " + this.getValue());

        /*if (this.linkedGoals.size() == 0) b.append(color);
        else
        {
            b.append(color);
            b.append(" ");
            linkedGoals.forEach((key, value) -> b.append(" ").append(key).append(" ").append(value));
        }*/
        return b.toString();
    }
}
