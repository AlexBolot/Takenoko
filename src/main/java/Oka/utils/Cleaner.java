package Oka.utils;

/*..................................................................................................
 . Copyright (c)
 .
 . The Cleaner	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 26/10/17 09:04
 .................................................................................................*/

import Oka.controler.GameBoard;
import Oka.controler.GameController;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Pond;

import java.awt.*;

public class Cleaner
{

    //region==========METHODS==============
    public static void cleanPanda ()
    {
        Panda.getInstance().setCoords(new Pond().getCoords());
    }

    public static void cleanGardener ()
    {
        Gardener.getInstance().setCoords(new Pond().getCoords());
    }

    public static void cleanGameBoard ()
    {
        GameBoard gameBoard = GameBoard.getInstance();

        //Clear the lists
        gameBoard.getGrid().clear();
        gameBoard.getAvailableSlots().clear();

        //Adding Pond's coords to the available slots.
        gameBoard.getAvailableSlots().add(new Point());

        //Adding the Pond to the grid.
        gameBoard.addCell(new Pond());
    }

    public static void cleanGameController ()
    {
        GameController.getInstance().setCurrentPlayer(null);
    }

    public static void clearAll ()
    {
        cleanPanda();
        cleanGardener();
        cleanGameBoard();
        cleanGameController();
    }
    //endregion
}
