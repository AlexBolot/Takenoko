package Oka;

import Oka.ai.AISimple;
import Oka.controler.GameBoard;
import Oka.entities.Gardener;
import Oka.entities.Panda;

public class App
{
    public static void main (String[] args)
    {
        AISimple ia1 = new AISimple();
        ia1.addGoal(GameBoard.getInstance().giveGoal());
        ia1.addGoal(GameBoard.getInstance().giveGoal());

        AISimple ia2 = new AISimple();
        ia2.addGoal(GameBoard.getInstance().giveGoal());
        ia2.addGoal(GameBoard.getInstance().giveGoal());

        int turn = 0;

        while (true)
        {
            turn++;

            ia1.placePlot();
            System.out.println("p1 - gameBoard : " + GameBoard.getInstance().getGrid());

            //noinspection Duplicates
            if (turn % 2 == 1)
            {
                ia1.moveGardener();
                System.out.println("p1 - gardener : " + Gardener.getInstance().getCoords());
            }
            else
            {
                ia1.movePanda();
                System.out.println("p1 - panda : " + Panda.getInstance().getCoords());
            }

            System.out.println("p1 - bamboos = " + ia1.getBamboos().size());

            int checkGoal = ia1.checkGoal();

            if (checkGoal > 0)
            {
                System.out.println("ai 1 WINS !!!");
                break;
            }


            System.out.println("===========");

            ia2.placePlot();
            System.out.println("p2 - gameBoard : " + GameBoard.getInstance().getGrid());

            //noinspection Duplicates
            if (turn % 2 == 1)
            {
                ia2.moveGardener();
                System.out.println("p2 - gardener : " + Gardener.getInstance().getCoords());
            }
            else
            {
                ia2.movePanda();
                System.out.println("p2 - panda : " + Panda.getInstance().getCoords());

            }

            System.out.println("p2 - bamboos = " + ia2.getBamboos().size());

            if (ia2.checkGoal() > 0)
            {
                System.out.println("ai 2 WINS !!!");
                break;
            }

            System.out.println("===========");

        }
    }
}
