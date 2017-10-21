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

import java.util.ArrayList;

public class GameController
{
    private ArrayList<AISimple> listPlayer = new ArrayList<AISimple>();
    private AISimple currentPlayer;

    public void initGame(){
        AISimple ai1 = new AISimple();
        ai1.addGoal(GameBoard.getInstance().giveGoal());
        ai1.addGoal(GameBoard.getInstance().giveGoal());

        AISimple ai2 = new AISimple();
        ai2.addGoal(GameBoard.getInstance().giveGoal());
        ai2.addGoal(GameBoard.getInstance().giveGoal());

        listPlayer.add(ai1);
        listPlayer.add(ai2);
    }
    public void startGame(){
        int turn = 0;
        while(true){
            turn++;
            for(int i=0; i<listPlayer.size(); i++){
                AISimple ai = listPlayer.get(i);
                currentPlayer = ai;
                int index = i+1;

                ai.placePlot();
                System.out.println("p"+index+" - gameBoard : " + GameBoard.getInstance().getGrid());

                //noinspection Duplicates
                if (turn % 2 == 1)
                {
                    ai.moveGardener();
                    System.out.println("p"+index+" - gardener : " + Gardener.getInstance().getCoords());
                }
                else
                {
                    ai.movePanda();
                    System.out.println("p"+index+" - panda : " + Panda.getInstance().getCoords());
                }

                System.out.println("p"+index+" - bamboos = " + ai.getBamboos().size());

                int checkGoal = ai.checkGoal();

                if (checkGoal > 0)
                {
                    System.out.println("ai "+index+" WINS !!!");
                    return;
                }
                System.out.println("===========");
            }
        }
    }
}
