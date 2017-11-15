package Oka.utils;

/*..................................................................................................
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

import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.controler.GameController;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Pond;

public class Cleaner
{
    //region==========METHODS==============

    /**
     <hr>
     <h3>Resets the coordinates of the Panda to the Pond's location</h3>
     */
    public static void cleanPanda ()
    {
        Panda.getInstance().setCoords(new Pond().getCoords());
    }

    /**
     <hr>
     <h3>Reset the coordinates of the Gardener to the Pond's location</h3>
     */
    public static void cleanGardener ()
    {
        Gardener.getInstance().setCoords(new Pond().getCoords());
    }

    /**
     <hr>
     <h3>Calls the reset method of the GameBoard.<br>
     See {@link GameBoard#resetGameBoard()}
     </h3>
     */
    public static void cleanGameBoard ()
    {
        GameBoard.resetGameBoard();
    }

    /**
     <hr>
     <h3>Puts the currentPlayer of the GameController as null</h3>
     */
    public static void cleanGameController ()
    {
        GameController.getInstance().setCurrentPlayer(null);
    }

    /**
     <hr>
     <h3>Calls the reset method of the GameBoard.<br>
     See {@link DrawStack#resetDrawStack()}
     </h3>
     */
    public static void cleanDrawStack ()
    {
        DrawStack.resetDrawStack();
    }

    /**
     <hr>
     <h3>Calls all the other clean methods of the Cleaner class</h3>
     */
    public static void clearAll ()
    {
        cleanPanda();
        cleanGardener();
        cleanGameBoard();
        cleanGameController();
        cleanDrawStack();
    }
    //endregion
}
