package Oka.board.plot;

import Oka.board.Cell;

import java.awt.*;

public class Plot extends Cell {
    String color = "Blue";// Provisoirement fixer a une couleur pour la release 1
    boolean isIrrigated;
    PlotState state;
    public Plot(Point coords) {
        super(coords);
        state = new NeutralState();
    }

}
