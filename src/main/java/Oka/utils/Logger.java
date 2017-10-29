package Oka.utils;

import java.io.PrintStream;

public class Logger
{
    //region==========ATTRIBUTES===========
    private static PrintStream printStream = System.out;
    private static int         indent      = 1;
    //endregion

    //region==========GETTER/SETTER========
    public static void setPrintStream (PrintStream printStream)
    {
        Logger.printStream = printStream;
    }
    //endregion

    //region==========METHODS==============
    public static void printLine (String string)
    {
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
        printStream.println(string);
    }

    public static void printSeparator (String playerName)
    {
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
        StringBuilder str = new StringBuilder();

        str.append("\n");

        for (int i = 0; i < indent - 1; i++)
        {
            str.append(" ");
        }

        for (int i = 0; i < 12; i++)
        {
            str.append(" ");
        }

        str.append(" ").append(playerName).append(" ");

        printStream.println(str.toString());
    }
    //endregion
}













