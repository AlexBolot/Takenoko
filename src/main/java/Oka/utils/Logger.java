package Oka.utils;

import java.io.PrintStream;
import java.util.HashMap;

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
     more than 1 = simple stats at the end.<br></h3>
     */
    private static int loggerMode = 0;

    /**
     <hr>
     <h3>Hashmap used to store all the stats.<br>
     Used for multiple-game runs, to accumulate the stats.</h3>
     */
    private static HashMap<String, Integer> stats = new HashMap<>();
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

    public static HashMap<String, Integer> getStats ()
    {
        return stats;
    }
    //endregion

    //region==========METHODS==============

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
        if (loggerMode != 1)
        {
            if (stats.containsKey(playerName)) stats.replace(playerName, stats.get(playerName) + 1);
            else stats.put(playerName, 1);
        }
        else
        {
            // printStream.println("\n" + playerName + " WINS !!!");
            printStream.println("\n" + playerName + " A GAGNÉ !!!");
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
    //endregion
}













