package Oka.utils;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.stream.IntStream;

public class Logger
{
    //region==========ATTRIBUTES===========
    /**
     <hr>
     <h3>Printer where the loggs will be printed.<br>
     Set to System.out by default.<br>
     <br>
     See {@link #setPrintStream(PrintStream)} to change that.</h3>
     */
    private static PrintStream printStream = System.out;

    /**
     <hr>
     <h3>Number of spaces to be put as 'tab' before lines (except #printTitle)</h3>
     */
    private static int indent = 1;

    /**
     <hr>
     <h3>1 = detailed loggs.<br>
     more than 1 = simple statAverage at the end.<br></h3>
     */
    private static int loggerMode = 1;

    //endregion

    //region==========GETTER/SETTER========

    /**
     <hr>
     <h3>Changing the PrintStream will change the writting destination of all the other methods of the Logger class.</h3>
     <hr>

     @param printStream New writting destination for the loggs
     */
    public static void setPrintStream (PrintStream printStream)
    {
        Logger.printStream = printStream;
    }

    //endregion

    //region==========METHODS==============

    public static void printPorgress (int actual, int total)
    {
        int scale = 50;
        int vPercent = ((actual + 1) * scale / total);
        int aPercent = ((actual + 1) * 100 / total);
        int space = 6;

        int sideLength = (scale - space) / 2;

        StringBuilder str = new StringBuilder();

        str.append("\r[");

        //Left Side
        int right = Math.min(vPercent, sideLength);
        IntStream.range(0, right).mapToObj(i1 -> "=").forEach(str::append);
        IntStream.range(0, sideLength - right).mapToObj(i -> " ").forEach(str::append);

        //Center
        IntStream.range(0, 4 - countDigits(aPercent)).mapToObj(i -> " ").forEach(str::append);
        str.append(aPercent).append("% ");

        //Right Side
        int left = Math.max(0, vPercent - sideLength - 6);
        IntStream.range(0, left).mapToObj(i -> "=").forEach(str::append);
        IntStream.range(0, sideLength - left).mapToObj(i -> " ").forEach(str::append);

        //End
        str.append("] ").append(actual + 1).append("/").append(total);

        System.out.print(str.toString());
    }

    /**
     <hr>
     <h3>The most basic method of Logger : prints the line with 1 indent.<br>
     <br>
     Exemple :
     <pre>"hello world"<br>becomes<br>" -> hello world"</pre>
     </h3>
     */
    public static void printLine (String string)
    {
        if (loggerMode != 1) return;

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < indent; i++)
        {
            str.append(" ");
        }

        str.append("-> ");

        str.append(string);

        printStream.println(str);
    }

    /**
     <hr>
     <h3>Prints the line with 0 indent (it will be the closest to the side)</h3>
     */
    public static void printTitle (String string)
    {
        if (loggerMode != 1) return;

        printStream.println(string);
    }

    /**
     <hr>
     <h3>Prints the playerName, preceeded by 1 indent and surrounded with '-'<br>
     It will make a good visual separation between player turns.<br>
     <br>
     Exemple :
     <pre>"Seb. Mosser"<br>becomes<br>" ------------ Seb. Mosser ------------"</pre>
     </h3>
     <br>
     <hr>

     @param playerName The player's name to be printed
     */
    public static void printSeparator (String playerName)
    {
        if (loggerMode != 1) return;

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < indent - 1; i++)
        {
            str.append(" ");
        }

        for (int i = 0; i < 12; i++)
        {
            str.append("-");
        }

        str.append(" ").append(playerName).append(" ");

        for (int i = 0; i < 12; i++)
        {
            str.append("-");
        }

        printStream.println(str.toString());
    }

    /**
     <hr>
     <h3>Prints the playerName, followed by 'WINS !!!"</h3>
     <hr>

     @param playerName The player's name to be printed
     */
    public static void printWin (String playerName)
    {
        if (loggerMode == 1)
        {
            // printStream.println("\n" + playerName + " WINS !!!");
            printStream.println("\n" + playerName + " A GAGNÉ !!!");
        }
    }

    /**
     <hr>
     <h3>Prints the draw"</h3>
     <hr>
     */
    public static void printDraw ()
    {
        if (loggerMode == 1)
        {
            // printStream.println("\n" + playerName + " WINS !!!");
            printStream.println("\n  EGALITÉ !!!");
        }
    }

    /**
     <hr>
     <h3>Changes the loggerMode value.<br>
     See {@link #loggerMode} javaDoc for more information</h3>
     <hr>

     @param i New loggerMode value
     */
    public static void setLoggerMode (int i)
    {
        loggerMode = i;
    }

    private static int countDigits (int i)
    {
        int count = 1;

        while (i / 10 != 0)
        {
            i /= 10;
            count++;
        }

        return count;
    }

    public static void printStat (int nbParti)
    {
        System.out.println(" ");
        System.out.println("Tour moyen par game : " + Stats.getNbTour() / nbParti);
        System.out.println("Tour max : " + Stats.getMaxTour());
        System.out.println("Tour min : " + Stats.getMinTour());

        HashMap<String, int[]> StatGoal = Stats.getStatsGoal();
        HashMap<String, int[]> WinRate = Stats.getStatAverage();

        int[] statsPlayer;
        for(String player :StatGoal.keySet()){

            statsPlayer = StatGoal.get(player);
            System.out.printf("\n%s : %.2f %% de victoire - " + (WinRate.get(player)[1]) / nbParti + " point moyen/partie "
                            + "-> BambooGoal : " + (float) statsPlayer[0] / (float) nbParti
                            + " GardenerGoal : " + (float) statsPlayer[1] / (float) nbParti
                            + " PlotGoal : " + (float) statsPlayer[2] / (float) nbParti
                            , player, (float) (WinRate.get(player)[0] * 100) / (float) nbParti);
        }
        if(WinRate.get("Draw") != null)
            System.out.printf("\n%.2f %% d'égalité", (float) (WinRate.get("Draw")[0] * 100) / (float) nbParti);
        System.out.println("\n \n");
    }
    //endregion
}













