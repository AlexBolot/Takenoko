package Oka.board;

import Oka.entities.Gardener;
import Oka.entities.Panda;

import java.awt.*;

public class Cell {
    public boolean pandaIsHere;
    public boolean gardenerIsHere;
    private Point coords;

    public Cell(Point coords) {
        this.coords = coords;
        this.gardenerIsHere = Gardener.getInstance().getCoords().equals(coords);
        this.pandaIsHere = Panda.getInstance().getCoords().equals(coords);
    }

}
