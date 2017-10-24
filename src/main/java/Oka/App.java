package Oka;

import Oka.ai.AISimple;
import Oka.controler.GameBoard;
import Oka.controler.GameController;
import Oka.entities.Gardener;
import Oka.entities.Panda;

import java.util.ArrayList;
import java.util.Arrays;

public class App
{
    public static void main (String[] args)
    {
        GameController gc = GameController.getInstance();
        AISimple Al = new AISimple("Al");
        AISimple Ma = new AISimple("Ma");
        ArrayList<AISimple> Playable = new ArrayList(Arrays.asList(Al,Ma));
        gc.play(Playable);
    }
}
