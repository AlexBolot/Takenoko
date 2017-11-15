package Oka;

import Oka.ai.AISimple;
import Oka.controler.GameController;
import Oka.utils.Cleaner;
import Oka.utils.Logger;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
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

        Logger.setLoggerMode(answer);

        if (answer == 1)
        {

            GameController gc = GameController.getInstance();

            AISimple AM = new AISimple("A.M Pinna");
            AISimple IL = new AISimple("I.Litovksy");

            ArrayList<AISimple> Playable = new ArrayList<>(Arrays.asList(AM, IL));

            gc.play(Playable);
        }

        else
        {

            for (int i = 0; i < answer; i++)
            {
                Cleaner.clearAll();

                GameController gc = GameController.getInstance();

                AISimple AM = new AISimple("A.M Pinna");
                AISimple IL = new AISimple("I.Litovksy");

                ArrayList<AISimple> Playable = new ArrayList<>(Arrays.asList(AM, IL));

                gc.play(Playable);
                if(i%100==0) System.out.println("Tour:" + i);
            }

            for (Map.Entry<String, Integer> entry : Logger.getStats().entrySet())
            {
                System.out.printf("\n%s : %.2f %% de victoire", entry.getKey(), (float) (entry.getValue() * 100) / (float) answer);
            }

            System.out.println();
        }
    }
}
