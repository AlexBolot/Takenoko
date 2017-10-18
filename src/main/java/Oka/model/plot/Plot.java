package Oka.model.plot;
import Oka.model.Cell;

import Oka.model.Bamboo;

import java.awt.*;
import java.util.ArrayList;

public class Plot extends Cell {
    private ArrayList<Bamboo> bamboo = new ArrayList<Bamboo>();

    public Plot() {
        super(null);
    }

    public ArrayList<Bamboo> getBamboo () {
        return bamboo;
    }

    Color color = Color.BLUE;// Provisoirement fixer a une couleur pour la release 1
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

    public Color getColor() {
        return color;
    }

    /**
     * return the top bamboo from the plot reserve and removes it
     * todo: throw and empty exception if empty really check the code  !!!!!!
     *
     * @return Bamboo
     */
    public Bamboo giveBamboo() {
        if (bamboo.size() == 0) return null;
        return bamboo.remove(bamboo.size() - 1);
    }
}