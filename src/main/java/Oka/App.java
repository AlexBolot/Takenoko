package Oka;

import Oka.ai.AISimple;
import Oka.controler.GameBoard;
import Oka.controler.GameController;
import Oka.entities.Gardener;
import Oka.entities.Panda;

public class App
{
    public static void main (String[] args)
    {
        GameController gc = new GameController();
        gc.initGame();
        gc.startGame();
    }
}
