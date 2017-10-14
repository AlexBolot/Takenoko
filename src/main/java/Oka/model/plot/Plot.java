package Oka.model.plot;
import Oka.model.Cell;
import Oka.model.Bamboo;

import java.awt.*;
import java.util.ArrayList;

public class Plot extends Cell {
    private ArrayList<Bamboo> bamboo = new ArrayList<Bamboo>();
    public ArrayList<Bamboo> getBamboo () {
        return bamboo;
    }

    public void setBamboo (ArrayList<Bamboo> bamboo) {
        this.bamboo = bamboo;
    }
    String color = "Blue";// Provisoirement fixer a une couleur pour la release 1
    boolean isIrrigated;

    PlotState state;
    public Plot(Point coords) {
        super(coords);
        state = new NeutralState();
    }

}