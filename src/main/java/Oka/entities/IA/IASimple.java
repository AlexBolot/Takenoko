package Oka.entities.IA;

import Oka.model.Cell;
import Oka.model.plot.Plot;
import Oka.controllers.GameBoard;
import Oka.entities.Gardener;
import Oka.model.Vector;

import java.awt.*;
import java.util.ArrayList;

public class IASimple extends IA {

    public void moveGardener () {
        Point coordsGardener = Gardener.getInstance().getCoords();
        GameBoard gameboard = GameBoard.getInstance();
        for (Cell cell : gameboard.getGrid()) {
            Vector v = Vector.isOnVector(coordsGardener, cell.getCoords());

            if (v != null && cell instanceof Plot &&((Plot) cell).getBamboo().size() == 0)
            {
                Point newCoords = v.applyVector(coordsGardener);
                Gardener.getInstance().setCoords(newCoords);
                return;
            }

        }
    }
    public void growBamboo () {
            //Todo
    }


    public void ValidateObjective () {
        //Todo
    }
}
