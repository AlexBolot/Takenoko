package Oka.model;

import Oka.entities.Gardener;
import Oka.entities.Panda;
import org.junit.Before;

import java.awt.*;

public class CellTest
{
    private Cell center;
    private Cell side;

    @Before
    public void setUp ()
    {
        Panda panda = Panda.getInstance();
        panda.setCoords(new Point(0, 0));
        Gardener gardener = Gardener.getInstance();
        gardener.setCoords(new Point(0, 0));

        // center = new Pond(new Point(0, 0));
        //side = new Cell(new Point(1, 1));
    }


}