package Oka.model.plot;

import Oka.model.Bamboo;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.plot.state.NeutralState;

import java.awt.*;
import java.util.ArrayList;

public class Plot extends Cell {
    private ArrayList<Bamboo> bamboo = new ArrayList<Bamboo>();
    private Enums.Color color;
    private boolean isIrrigated;
    private NeutralState state;

    public boolean isIrrigated() {
        return isIrrigated || state.getIsIrrigated();
    }

    public void setIsIrrigated(boolean bool){
        this.isIrrigated = bool;
    }
    public Plot(Enums.Color color) {
        super(new Point());
        this.color = color;
        this.state = new NeutralState();
    }
    public Plot(Enums.Color color, NeutralState state){
        super(new Point());
        this.color = color;
        this.state = state;
    }

    public ArrayList<Bamboo> getBamboo () {
        return bamboo;
    }

    public Plot(Point coords) {
        super(coords);
        state = new NeutralState();
    }

    public Plot(Point coords, Enums.Color color) {
        super(coords);
        this.color = color;
        state = new NeutralState();
        addBamboo();
    }

    public void addBamboo ()
    {
        for(int i=0; i<state.getHowManyaddBambo(); i++) {
            if (this.bamboo.size() < 4)
                this.bamboo.add(new Bamboo(this.color));
        }
    }

    public void setBamboo(ArrayList<Bamboo> bamboo) {
        this.bamboo = bamboo;
    }

    public Enums.Color getColor() {
        return color;
    }

    /**
     * return the top bamboo from the plot reserve and removes it
     * todo: throw and empty exception if empty really check the code  !!!!!!
     *
     * @return Bamboo
     */
    public Bamboo giveBamboo() {
        if (bamboo.size() == 0 || !state.authorizationGetBamboo()) return null;
        return bamboo.remove(bamboo.size() - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Plot)) return false;

        Plot plot = (Plot) obj;
        boolean equality;
        equality = plot.bamboo.equals(this.bamboo);
        equality = equality && plot.getCoords().equals(this.getCoords());
        if (this.getColor() != null && plot.getColor() != null) {

            equality = equality && plot.getColor().equals(this.getColor());
        } else {
            equality = equality && this.getColor() == plot.getColor();
        }

        return equality;
    }

    @Override
    public String toString() {
        return super.toString()+ " " + color;
    }
}