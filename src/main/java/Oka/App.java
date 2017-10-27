package Oka;

import Oka.ai.AISimple;
import Oka.controler.GameController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class App
{
    public static void main (String[] args) throws FileNotFoundException
    {
        //Logger.setPrintStream(new PrintStream(new File("/Users/alexandre/Desktop/tmp.txt")));

        GameController gc = GameController.getInstance();
        // AISimple Al = new AISimple("Alexandre");
        // AISimple Ma = new AISimple("Mathieu");
        AISimple Gr = new AISimple("Grégoire");
        AISimple Th = new AISimple("Theos");
        ArrayList<AISimple> Playable = new ArrayList(Arrays.asList(Gr, Th));
        gc.play(Playable);
    }
}
