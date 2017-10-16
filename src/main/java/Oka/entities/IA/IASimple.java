package Oka.entities.IA;

import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.controler.GameBoard;
import Oka.model.Vector;
import Oka.model.plot.Plot;

import java.awt.*;

public class IASimple extends IA {

    public void moveGardener ()
    {
        Point coordsGardener = Gardener.getInstance().getCoords();
        GameBoard gameboard = GameBoard.getInstance();
        for (Cell cell : gameboard.getGrid())
        {
            //Si la case est celle du Gardener, on ne la considère pas.
            if (cell.getCoords() == coordsGardener) continue;

            Vector v = Vector.isOnVector(coordsGardener, cell.getCoords());

            if (v != null && cell instanceof Plot && ((Plot) cell).getBamboo().size() == 0)
            {
                Point newCoords = v.applyVector(coordsGardener);
                Gardener.getInstance().setCoords(newCoords);
                return;
            }

        }
    }

    public void movePanda ()
    {
        Point coordsPanda = Panda.getInstance().getCoords();
        GameBoard gameboard = GameBoard.getInstance();
        for (Cell cell : gameboard.getGrid())
        {
            //Si la case est celle du Panda, on ne la considère pas.
            if (cell.getCoords() == coordsPanda) continue;

            Vector v = Vector.isOnVector(coordsPanda, cell.getCoords());

            if (v != null && cell instanceof Plot && ((Plot) cell).getBamboo().size() > 0)
            {
                Point newCoords = v.applyVector(coordsPanda);
                Panda.getInstance().setCoords(newCoords);
                return;
            }
        }
    }

    public void ValidateObjective ()
    {
        //Todo
    }
}
