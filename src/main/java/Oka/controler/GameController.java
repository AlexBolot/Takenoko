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
import Oka.model.goal.Goal;
import Oka.utils.Logger;

import java.util.ArrayList;

public class GameController
{
    //region==========ATTRIBUTES===========
    private static GameController gameController = new GameController();

    private AISimple currentPlayer;
    //endregion//

    //region==========GETTER/SETTER========
    public static GameController getInstance ()
    {
        return gameController;
    }

    public AISimple getCurrentPlayer ()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer (AISimple currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }
    //endregion

    //region==========METHODS==============
    public void play (ArrayList<AISimple> playable)
    {
        int turn = 0;
        while (true)
        {
            Logger.printTitle("\n========== Turn " + ++turn + " ==========\n");

            for (int i = 0; i < playable.size(); i++)
            {
                AISimple ai = playable.get(i);
                currentPlayer = ai;
                ai.play();

                int checkGoal = ai.getInventory().checkGoals().size();

                if (checkGoal >= 9)
                {
                    // on rajoute l'objectif empereur
                    ai.getInventory().addGoal(new Goal(2, true));
                    lastTurn(playable, playable.get(i));

                    AISimple AIWin = maxValuesObjectives(playable);
                    Logger.printWin(AIWin.getName());
                    return;
                }
            }
        }
    }
    //endregion

    public void lastTurn (ArrayList<AISimple> playable, AISimple ai1)
    {
        for (AISimple ai : playable)
        {
            if (ai != ai1)
            {
                ai.play();
                ai.getInventory().checkGoals();
            }
        }

    }

    public AISimple maxValuesObjectives(ArrayList<AISimple> playable) {
        int max = 0;
        AISimple AIWinner = new AISimple();
        for (AISimple ai : playable) {
            ai.PrintObjectives(ai);
            if (ai.getInventory().getValueOfGoalHolder() > max) {
                max = ai.getInventory().getValueOfGoalHolder();
                AIWinner = ai;
            }
        }
        return AIWinner;
    }

}