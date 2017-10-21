package Oka.ai;

import Oka.controler.GameBoard;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.model.Vector;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class AISimple extends AI
{

    public AISimple (String name) {
        super(name);
    }

    public void moveGardener ()
    {
        Point coordsGardener = Gardener.getInstance().getCoords();
        GameBoard gameboard = GameBoard.getInstance();
        for (Cell cell : gameboard.getGrid())
        {
            //Si la case est celle du Gardener, on ne la considère pas.
            if (cell.getCoords().equals(coordsGardener)) continue;

            Vector v = Vector.findStraightVector(coordsGardener, cell.getCoords());

            if (v != null && cell instanceof Plot && ((Plot) cell).getBamboo().size() == 0)
            {
                Point newCoords = v.applyVector(coordsGardener);
                Gardener.getInstance().move(cell.getCoords());
                return;
            }
        }
    }

    public void movePanda ()
    {
        //todo: move code to GAMEBOARD, for responsability coherence and panda move in Panda.move(Point point)


        Point coordsPanda = Panda.getInstance().getCoords();
        GameBoard gameboard = GameBoard.getInstance();
        for (Cell cell : gameboard.getGrid())
        {
            //Si la case est celle du Panda, on ne la considère pas.
            if (cell.getCoords().equals(coordsPanda)) continue;

            Vector v = Vector.findStraightVector(coordsPanda, cell.getCoords());

            if (v != null && cell instanceof Plot && ((Plot) cell).getBamboo().size() > 0)
            {

                Point newCoords = v.applyVector(coordsPanda);
                try {
                    this.addBamboo(Panda.getInstance().move(cell.getCoords()));
                } catch (IllegalArgumentException e) {
                    //todo: find correct behavior for wrong move behavior
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    /**
     place a plot tile
     */
    public void placePlot ()
    {
        GameBoard board = GameBoard.getInstance();

        //todo: make giveplot return an arraylist from a card stack
        ArrayList<Plot> draw = new ArrayList<>(Collections.singletonList(board.givePlot()));

        //tant qu'on nous renvois les même trois case
        Plot plot = draw.get(0);

        //todo: add a available slot function
        ArrayList<Point> free = board.getAvailableSlots();
        plot.setCoords(free.get(0));
        board.addCell(plot);
    }

    public void ValidateObjective ()
    {
        //Todo
    }
}
