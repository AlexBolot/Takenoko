package Oka.model.plot;

import Oka.model.Bamboo;
import Oka.model.Cell;

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

    public void addBamboo(Bamboo bamboo) {
        if (bamboo.getColor().equals(this.color)) {
            this.bamboo.add(bamboo);
        } else {
            //todo: decider si on code cette exception ou pas, parceque ce serait épique
            //throw new BadBambooColorException;
        }
    }

    public void setBamboo(ArrayList<Bamboo> bamboo) {
        this.bamboo = bamboo;
    }

    public String getColor() {
        return color;
    }
}