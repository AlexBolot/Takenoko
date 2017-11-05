package Oka.model;

import Oka.controler.GameBoard;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Irrigation {
    private Plot plot1;
    private Plot plot2;

    public Irrigation() {

    }

    public Irrigation(Plot p1, Plot p2) {
        this.plot1 = p1;
        this.plot2 = p2;
    }

    public Irrigation(Point point1, Point point2) {
        this((Plot) GameBoard.getInstance().getGrid().get(point1), (Plot) GameBoard.getInstance().getGrid().get(point2));
    }

    public Plot getPlot1() {
        return plot1;
    }

    public Plot getPlot2() {
        return plot2;
    }

    public void setPlot1(Plot plot1) {
        this.plot1 = plot1;
    }

    public void setPlot2(Plot plot2) {
        this.plot2 = plot2;
    }

    private Set<Plot> getPlots() {
        return new HashSet<Plot>(Arrays.asList(this.plot1, this.plot2));
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof Irrigation)) return false;

        Irrigation irr = (Irrigation) obj;

        return this.getPlots().equals(irr.getPlots());

    }

    @Override
    public int hashCode() {
        int hash1 = this.plot1.getCoords().hashCode();
        int hash2 = this.plot2.getCoords().hashCode();
        int hash;
        if (hash1 < hash2) hash = Objects.hash(hash1, hash2);
        else hash = Objects.hash(hash2, hash1);
        return hash;
    }
}
