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
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.utils.Logger;

import java.util.ArrayList;

public class GameController
{
    private ArrayList<AISimple> listPlayer = new ArrayList<AISimple>();
    private AISimple currentPlayer;

    public void initGame(){
        AISimple ai1 = new AISimple("Al");
        ai1.addGoal(GameBoard.getInstance().giveGoal());
        ai1.addGoal(GameBoard.getInstance().giveGoal());
        // TODO Remplacer les lignes du dessus par les lignes en commentaires, quand l'inventaire sera crée pour stock les bamboos
        //ai1.addGoal(DrawStack.draw(Enums.goalType.BambooGoal));
        //ai1.addGoal(DrawStack.draw(Enums.goalType.BambooGoal));

        AISimple ai2 = new AISimple("Ma");
        ai2.addGoal(GameBoard.getInstance().giveGoal());
        ai2.addGoal(GameBoard.getInstance().giveGoal());
        //ai2.addGoal(DrawStack.draw(Enums.goalType.BambooGoal));
        //ai2.addGoal(DrawStack.draw(Enums.goalType.BambooGoal));

        listPlayer.add(ai1);
        listPlayer.add(ai2);
    }
    public void startGame(){
        int turn = 0;
        while(true){
            Logger.printTitle("\n========== Turn " + turn++ + " ==========\n");
            for(int i=0; i<listPlayer.size(); i++){
                AISimple ai = listPlayer.get(i);
                currentPlayer = ai;
                ai.placePlot();
                Logger.printSeparator(ai.getName());
               // Logger.printLine(ai.getName() + " - gameBoard : " + GameBoard.getInstance().getGrid());

                //noinspection Duplicates
                if (turn % 2 == 1)
                {
                    ai.moveGardener();
                    Logger.printLine(ai.getName() + " - gardener : " + Gardener.getInstance().getCoords());
                }
                else
                {
                    ai.movePanda();
                    Logger.printLine(ai.getName() +" - panda : " + Panda.getInstance().getCoords());
                }

                Logger.printLine(ai.getName() +" - bamboos = " + ai.getBamboos().size());

                int checkGoal = ai.checkGoal();

                if (checkGoal > 0)
                {
                    System.out.println(ai.getName() +" WINS !!!");
                    return;
                }

            }
        }
    }
}
