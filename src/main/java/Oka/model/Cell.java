package Oka.model;


import Oka.entities.Gardener;
import Oka.entities.Panda;

import java.awt.*;

public class Cell {
    private boolean pandaIsHere;
    private boolean gardenerIsHere;
    private Point coords;

    public Cell(Point coords) {
        this.coords = coords;
        this.gardenerIsHere = Gardener.getInstance().getCoords().equals(coords);
        this.pandaIsHere = Panda.getInstance().getCoords().equals(coords);
    }


    public Point getCoords () {
        return coords;
    }

    public void setCoords (Point coords) {
        this.coords = coords;
    }

    public boolean PandaIsHere() {
        return pandaIsHere;
    }

    public void setPandaIsHere(boolean pandaIsHere) {
        this.pandaIsHere = pandaIsHere;
    }

    public boolean GardenerIsHere() {
        return gardenerIsHere;
    }

    public void setGardenerIsHere(boolean gardenerIsHere) {
        this.gardenerIsHere = gardenerIsHere;
    }
}
