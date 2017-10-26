package Oka.controler;

/*..................................................................................................
 . Copyright (c)
 .
 . The GameController	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 18/10/17 17:04
 .................................................................................................*/

import Oka.ai.AISimple;
import Oka.utils.Logger;

import java.util.ArrayList;

public class GameController
{
    private static GameController gameController = new GameController();
    private ArrayList<AISimple> listPlayer = new ArrayList<AISimple>();
    private AISimple currentPlayer;

    public static GameController getInstance() {
        return gameController;
    }

    public void play (ArrayList<AISimple> playable){
        int turn =0;
        while(turn<30){
            Logger.printTitle("\n========== Turn "+ ++turn + " ==========\n");
        for ( AISimple ai : playable) {
            currentPlayer=ai;
            ai.play();

            int checkGoal = ai.checkGoal();
            if (checkGoal > 0) {
                Logger.printWin( ai.getName() + " WINS !!!");
                return;
            }
        }

    }
    }

    public AISimple getCurrentPlayer ()
    {
        return currentPlayer;
    }

    public void setListPlayer (ArrayList<AISimple> listPlayer)
    {
        this.listPlayer = listPlayer;
    }

    public void setCurrentPlayer (AISimple currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }
}
