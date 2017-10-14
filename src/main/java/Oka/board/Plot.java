package Oka.board;

import java.awt.*;

public class Plot extends Cell {
    String color = "Blue";// Provisoirement fixer a une couleur pour la release 1
    boolean isIrrigated;

    public Plot(Point coords) {

        super(coords);
    }
}
