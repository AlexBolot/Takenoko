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

import java.util.ArrayList;
import java.util.HashMap;

public class Cleaner
{
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
        GameBoard.getInstance().setGrid(new HashMap<>());
        GameBoard.getInstance().setAvailableSlots(new ArrayList<>());
    }

    public static void cleanGameController ()
    {
        GameController.getInstance().setCurrentPlayer(null);
        GameController.getInstance().setListPlayer(new ArrayList<>());
    }

    public static void clearAll ()
    {
        cleanPanda();
        cleanGardener();
        cleanGameBoard();
        cleanGameController();
    }
}
