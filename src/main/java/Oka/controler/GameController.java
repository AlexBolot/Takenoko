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
        int turn = 0,
            nbGoal;
        if(playable.size()==2){
            nbGoal=9;
        }else if(playable.size()==3){
            nbGoal=8;
        }else{
            nbGoal=7;
        }

        while (true)
        {
            Logger.printTitle("\n========== Turn " + ++turn + " ==========\n");

            for (int i = 0; i < playable.size(); i++)
            {
                AISimple ai = playable.get(i);
                currentPlayer = ai;
                ai.play();

                int checkGoal = ai.getInventory().checkGoals().size();

                if (checkGoal >= nbGoal)
                {
                    // on rajoute l'objectif empereur
                    ai.getInventory().addGoal(new Goal(2, true));
                    lastTurn(playable, playable.get(i));

                    ArrayList<AISimple> ListAIWin = getAIWins(playable);
                    if(ListAIWin.size() == 1){
                        Logger.printWin(ListAIWin.get(0).getName());
                    }else{
                        Logger.printDraw();
                    }
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

    public ArrayList<AISimple> getAIWins(ArrayList<AISimple> playable) {
        int max = 0;
        ArrayList<AISimple> listAIWinnger = new ArrayList<>();

        for (AISimple ai : playable) {
            ai.PrintObjectives(ai);
            if (ai.getInventory().getValueOfGoalHolder() > max) {
                max = ai.getInventory().getValueOfGoalHolder();
                listAIWinnger.clear();
                listAIWinnger.add(ai);
            }else if(ai.getInventory().getValueOfGoalHolder() == max){
                listAIWinnger.add(ai);
            }
        }
        if(listAIWinnger.size()>1){
            max = 0;
            for (AISimple ai : playable) {
                if (ai.getInventory().getValueOfBambooGoalHolder() > max) {
                    max = ai.getInventory().getValueOfGoalHolder();
                    listAIWinnger.clear();
                    listAIWinnger.add(ai);
                }else if(ai.getInventory().getValueOfGoalHolder() == max){
                    listAIWinnger.add(ai);
                }
            }
        }
        return listAIWinnger;
    }

}