package Oka;

import Oka.ai.AIGoal;
import Oka.ai.Playable;
import Oka.controler.GameController;
import Oka.utils.Cleaner;
import Oka.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static Oka.utils.Logger.printStat;

public class App
{
    public static void main (String[] args)
    {
        Logger.printTitle("Saisir le nombre de parties jouées (1 partie = logs précis, supérieur à 1 = logs statistiques");

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

            Playable AM = new AIGoal("AIGoal");
            Playable IL = new AIGoal("AIGoal2");

            ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL));

            gc.play(Playable);
        }
        else
        {
            for (int i = 0; i < answer; i++)
            {
                Cleaner.clearAll();

                GameController gc = GameController.getInstance();

                Playable AM = new AIGoal("AIGoal");
                Playable IL = new AIGoal("AIGoal2");

                Logger.printPorgress(i, answer);

                ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL));

                gc.play(Playable);
            }

            printStat(answer);
        }
    }
}
