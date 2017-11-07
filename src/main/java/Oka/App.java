package Oka;

import Oka.ai.AISimple;
import Oka.controler.GameController;
import Oka.utils.Cleaner;
import Oka.utils.Logger;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class App
{
    public static void main (String[] args) throws FileNotFoundException
    {
        //Used for log testing with HexMap Project
        //Logger.setPrintStream(new PrintStream(new File("/Users/alexandre/Desktop/tmp.txt")));

        System.out.println("Saisir le nombre de parties jouées (1 partie = logs précis, supérieur à 1 = logs statistiques");

        int answer = 0;
        Scanner sc = new Scanner(System.in);

        do
        {
            String str = sc.nextLine();
            try
            {
                answer = Integer.parseInt(str);
            }
            catch (NumberFormatException ignored)
            {
            }

        } while (answer == 0);
        long pp = System.currentTimeMillis();

        Logger.setLoggerMode(answer);

        if (answer == 1)
        {

            GameController gc = GameController.getInstance();

            AISimple Al = new AISimple("Alexandre");
            AISimple Ma = new AISimple("Mathieu");

            ArrayList<AISimple> Playable = new ArrayList<>(Arrays.asList(Al, Ma));

            gc.play(Playable);
        }

        else
        {
            for (int i = 0; i < answer; i++)
            {
                Cleaner.clearAll();

                GameController gc = GameController.getInstance();

                AISimple Al = new AISimple("Alexandre");
                AISimple Ma = new AISimple("Mathieu");

                ArrayList<AISimple> Playable = new ArrayList<>(Arrays.asList(Al, Ma));

                gc.play(Playable);
            }

            HashMap<String, Integer> stats = Logger.getStats();

            int finalAnswer = answer;
            stats.forEach((key, value) -> System.out.println(key + " : " + value * 100 / finalAnswer + "% de victoire"));
            System.out.println(System.currentTimeMillis()-pp);
        }
    }
}
