package Oka.utils;

import java.io.PrintStream;
import java.util.HashMap;

public class Logger
{
    //region==========ATTRIBUTES===========
    private static PrintStream printStream = System.out;
    private static int         indent      = 1;

    //1 = detailed loggs, superieur = simple stats
    private static int loggerMode = 0;

    private static HashMap<String, Integer> stats = new HashMap<>();
    //endregion

    //region==========GETTER/SETTER========
    public static void setPrintStream (PrintStream printStream)
    {
        Logger.printStream = printStream;
    }

    public static HashMap<String, Integer> getStats ()
    {
        return stats;
    }
    //endregion

    //region==========METHODS==============
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

    public static void printTitle (String string)
    {
        if (loggerMode != 1) return;

        printStream.println(string);
    }

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
            String s = "-";
            str.append(s);
        }

        printStream.println(str.toString());
    }

    public static void printWin (String playerName)
    {
        if (loggerMode != 1)
        {
            if (stats.containsKey(playerName)) stats.replace(playerName, stats.get(playerName) + 1);
            else stats.put(playerName, 1);
        }
        else
        {
            printStream.println("\n" + playerName + " WINS !!!");
        }
    }

    public static void setLoggerMode (int i)
    {
        loggerMode = i;
    }
    //endregion
}













