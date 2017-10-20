package Oka.model.plot;

import Oka.model.Bamboo;
import Oka.model.Cell;

import java.awt.*;
import java.util.ArrayList;

public class Plot extends Cell {
    private ArrayList<Bamboo> bamboo = new ArrayList<Bamboo>();
    private Color color = Color.GREEN;// Provisoirement fixer a une couleur pour la release 1
    private boolean isIrrigated;
    private PlotState state;

    public Plot() {
        super(null);
    }

    public ArrayList<Bamboo> getBamboo () {
        return bamboo;
    }


    public Plot(Point coords) {
        super(coords);
        addBamboo(new Bamboo(Color.GREEN));
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