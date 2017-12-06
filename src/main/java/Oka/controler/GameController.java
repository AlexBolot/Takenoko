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

import Oka.ai.AI;
import Oka.ai.Playable;
import Oka.model.goal.Goal;
import Oka.utils.Logger;
import Oka.utils.Stats;

import java.util.ArrayList;

public class GameController
{
    //region==========ATTRIBUTES===========
    private static GameController gameController = new GameController();

    private Playable  currentPlayer;
    private int turn;
    //endregion//

    //region==========GETTER/SETTER========
    public static GameController getInstance ()
    {
        return gameController;
    }

    public Playable getCurrentPlayer ()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer (AI currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }
    //endregion

    //region==========METHODS==============

    /**
     @param playable Take all the players.
     And make them play and having fun #BestMethod
     */
    public void play (ArrayList<Playable> playable)
    {
        int nbGoal;
        turn = 0;

        if (playable.size() == 2)
        {
            nbGoal = 9;
        }
        else if (playable.size() == 3)
        {
            nbGoal = 8;
        }
        else
        {
            nbGoal = 7;
        }

        while (true)
        {
            Logger.printTitle("\n========== Turn " + ++turn + " ==========\n");
            //  Logger.printLine("GameBoard :"+ GameBoard.getInstance().getGrid().size());
            Logger.printLine("Grid :"+ GameBoard.getInstance().toString());
            for (int i = 0; i < playable.size(); i++)
            {
                Playable ai = playable.get(i);
                currentPlayer = ai;

                Logger.printSeparator(ai.getName());

                ai.play();

                int checkGoal = ai.getInventory().checkGoals().size();

                if (checkGoal >= nbGoal)
                {
                    // on rajoute l'objectif empereur
                    ai.getInventory().addGoal(new Goal(2, true));
                    lastTurn(playable, playable.get(i));
                    ArrayList<Playable> ListAIWin = getAIWins(playable);

                    Stats.saveStatTurn(turn);
                    Stats.saveStatGoal(playable);

                    if (ListAIWin.size() == 1)
                    {
                        Stats.saveStatWinner(ListAIWin.get(0).getName());
                        Logger.printWin(ListAIWin.get(0).getName());
                    }
                    else
                    {
                        Stats.saveStatWinner("Draw");
                        Logger.printDraw();
                    }
                    return;
                }
            }
        }
    }

    /**
     @param playable Take all the players. ( or AI in our case )
     @param ai1      Take an AI ( the AI who completed the nine goal )
     And make all the others AI play their last TURN !!!
     */
    public void lastTurn (ArrayList<Playable> playable, Playable ai1)
    {
        for (Playable ai : playable)
        {
            if (ai != ai1)
            {
                currentPlayer = ai;
                ai.play();
                ai.getInventory().checkGoals();
            }
        }
    }

    /**
     <hr>
     <h3> this method save all the stats of the players and return the player(s) that has more points than the others.</h3>
     <hr>
     */
    public ArrayList<Playable> getAIWins (ArrayList<Playable> playable)
    {
        int max = 0, pointPLayer;
        ArrayList<Playable> listAIWinnger = new ArrayList<>();

        for (Playable ai : playable)
        {
            ai.printObjectives();
            pointPLayer = ai.getInventory().getValueOfGoalHolder();

            Stats.saveStatPoint(ai.getName(), pointPLayer);
            if (pointPLayer > max)
            {
                max = pointPLayer;
                listAIWinnger.clear();
                listAIWinnger.add(ai);
            }
            else if (pointPLayer == max)
            {
                listAIWinnger.add(ai);
            }
        }
        if (listAIWinnger.size() > 1)
        {
            max = 0;
            for (Playable ai : playable)
            {
                pointPLayer = ai.getInventory().getValueOfBambooGoalHolder();
                if (pointPLayer > max)
                {
                    max = pointPLayer;
                    listAIWinnger.clear();
                    listAIWinnger.add(ai);
                }
                else if (pointPLayer == max)
                {
                    listAIWinnger.add(ai);
                }
            }
        }
        return listAIWinnger;
    }
    //endregion

}