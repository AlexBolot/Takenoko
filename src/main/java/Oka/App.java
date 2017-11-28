package Oka;

import Oka.ai.AI;
import Oka.ai.AIRandom;
import Oka.ai.AISimple;
import Oka.controler.GameController;
import Oka.utils.Cleaner;
import Oka.utils.Logger;
import Oka.utils.Stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static Oka.utils.Logger.printStat;

public class App
{
    public static void main (String[] args)
    {
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

        Logger.setLoggerMode(answer);


        if (answer == 1)
        {
            GameController gc = GameController.getInstance();

            AISimple AM = new AISimple("AISimple1");
           // AISimple IL = new AISimple("AISimple2");
            AIRandom IL = new AIRandom("AIRandom");

            ArrayList<AI> Playable = new ArrayList<>(Arrays.asList(AM, IL));

            gc.play(Playable);
        }
        else
        {
            for (int i = 0; i < answer; i++)
            {
                Cleaner.clearAll();

                GameController gc = GameController.getInstance();

                AISimple AM = new AISimple("AISimple1");
                //AISimple IL = new AISimple("AISimple2");
                AIRandom IL = new AIRandom("AIRandom");

                Logger.printPorgress(i, answer);

                ArrayList<AI> Playable = new ArrayList<>(Arrays.asList(AM, IL));

                gc.play(Playable);
            }

            for (Map.Entry<String, int[]> entry : Stats.getStatAverage().entrySet())
            {
                if(!entry.getKey().equals("Draw"))
                    System.out.printf("\n%s : %.2f %% de victoire avec une moyenne de point égale à "+ (entry.getValue()[1]) / answer,
                            entry.getKey(), (float) (entry.getValue()[0] * 100) / (float) answer);
                else
                    System.out.printf("\n%.2f %% d'égalité", (float) (entry.getValue()[0] * 100) / (float) answer);
            }
            printStat(answer);
        }
    }
}
