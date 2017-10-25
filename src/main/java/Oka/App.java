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
        AISimple Al = new AISimple("Alexandre");
        AISimple Ma = new AISimple("Mathieu");
        AISimple Gr = new AISimple("Grégoire");
        AISimple Th = new AISimple("Theos");
        ArrayList<AISimple> Playable = new ArrayList(Arrays.asList(Al,Ma,Gr,Th));
        gc.play(Playable);
    }
}
