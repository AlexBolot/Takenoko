package Oka.model.plot;

import Oka.model.Bamboo;
import Oka.model.Cell;

import java.awt.*;
import java.util.ArrayList;

public class Plot extends Cell {
    private ArrayList<Bamboo> bamboo = new ArrayList<Bamboo>();
    private Color color;
    private boolean isIrrigated;
    private PlotState state;

    public Plot(Color color) {
        super(new Point());
        this.color = color;
    }

    public ArrayList<Bamboo> getBamboo () {
        return bamboo;
    }

    public Plot(Point coords) {
        super(coords);
        state = new NeutralState();
    }

    public Plot(Point coords, Color color) {
        super(coords);
        addBamboo();
        this.color = color;
        state = new NeutralState();
    }

    public void addBamboo ()
    {
        if (this.bamboo.size() < 4) this.bamboo.add(new Bamboo(this.color));
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Plot)) return false;

        Plot plot = (Plot) obj;
        boolean equality;
        equality = plot.bamboo.equals(this.bamboo);
        equality = equality && plot.getCoords().equals(this.getCoords());
        equality = equality && plot.getColor().equals(this.getColor());

        return equality;
    }

    @Override
    public String toString() {
        String stringColor = "";
        if(color.equals(Color.YELLOW)){
            stringColor = "Yellow";
        }else if (color.equals(Color.pink)){
            stringColor = "Blue";
        }else if (color.equals(Color.GREEN)){
            stringColor = "Green";
        }
        return super.toString()+ " " + stringColor;
    }
}