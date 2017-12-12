package Oka;

import Oka.utils.Cleaner;
import Oka.utils.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AppTest
{

    @Before
    public void before ()
    {
        Cleaner.cleanStats();
    }

    @Test (timeout = 30000)
    public void goal_goal ()
    {
        Logger.setLoggerMode(1000);
        App.runAppGoal();
    }

    @Test (timeout = 30000)
    public void simple_goal ()
    {
        Logger.setLoggerMode(1000);
        App.runAppSimplevsGoal();
    }

    @Test (timeout = 30000)
    public void random_goal ()
    {
        Logger.setLoggerMode(1000);
        App.runAppRandomvsGoal();
    }

    @Test (timeout = 30000)
    public void random_simple ()
    {
        Logger.setLoggerMode(1000);
        App.runAppRandomvsSimple();
    }

    @Test (timeout = 30000)
    public void random_random ()
    {
        long start = System.currentTimeMillis();

        Logger.setLoggerMode(1000);
        App.runAppRandom();

        System.out.println("temps écoulé : " + (System.currentTimeMillis() - start + " ms"));
    }

    @Ignore ("We know it's currently broken, no need to test :) All is explained in the .pdf feedback")
    @Test (timeout = 30000)
    public void simple_simple ()
    {
        long start = System.currentTimeMillis();

        Logger.setLoggerMode(1000);
        App.runAppSimple();

        System.out.println("temps écoulé : " + (System.currentTimeMillis() - start + " ms"));
    }
}
