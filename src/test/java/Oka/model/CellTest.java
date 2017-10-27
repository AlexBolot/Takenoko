package Oka.model;

import Oka.entities.Gardener;
import Oka.entities.Panda;
import junit.framework.TestCase;

import java.awt.*;

public class CellTest extends TestCase {
    Cell center;
    Cell side;

    public void setUp() throws Exception {
        Panda panda = Panda.getInstance();
        panda.setCoords(new Point(0, 0));
        Gardener gardener = Gardener.getInstance();
        gardener.setCoords(new Point(0, 0));

        center = new Cell(new Point(0, 0));
        side = new Cell(new Point(1, 1));
        super.setUp();
    }

}