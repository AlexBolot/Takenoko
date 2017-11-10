package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.Vector;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlotGoal extends Goal {

    Enums.Color color;
    HashMap<Vector, PlotGoal> linkedGoals = new HashMap<>();


    public PlotGoal(int value, Enums.Color color) {
        super(value);
        this.color = color;
    }

    public PlotGoal(int value, Enums.Color color, HashMap<Vector, PlotGoal> linkedGoals) {
        this(value, color);
        this.linkedGoals = linkedGoals;
    }


    public boolean validate(Optional<Point> coords) {
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();
        if (coords.isPresent()) {
            Cell cell = grid.get(coords.get());

            return !(cell == null) && !cell.getCoords().equals(new Point()) &&
                    ((Plot) cell).getColor().equals(this.color) &&
                    linkedGoals.entrySet()
                            .stream()
                            .allMatch(
                                    entry -> entry.getValue()
                                            .validate(Optional.of(entry.getKey().applyVector(coords.get())))
                            );
        }
        return grid.keySet().stream().anyMatch(p -> this.validate(Optional.of(p)));

    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(super.toString() + "  ");

        if (this.linkedGoals.size() == 0) b.append(color);
        else {
            b.append(color);
            b.append(" ");
            linkedGoals.forEach((key, value) -> b.append(" ").append(key).append(" ").append(value));
        }
        return b.toString();
    }
}
