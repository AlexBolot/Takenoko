package Oka;

import Oka.ai.AIGoal;
import Oka.ai.AIRandom;
import Oka.ai.AISimple;
import Oka.ai.Playable;
import Oka.controler.GameController;
import Oka.utils.Cleaner;
import Oka.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;

import static Oka.utils.Logger.printStat;

public class App {
    public static void main(String[] args) {
        Logger.setLoggerMode(0);
        runAppGoal();
        printStat(1000);
        Cleaner.cleanStats();

        runAppSimplevsGoal();
        printStat(1000);
    }
    public static void runAppRandom() {
        for (int i = 0; i < 1000; i++) {
            Cleaner.clearAll();

            GameController gc = GameController.getInstance();

            Playable AM = new AIRandom("AIRandom1");
            Playable IL = new AIRandom("AIRandom2");

            Logger.printPorgress(i, 1000);

            ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL));

            gc.play(Playable);
        }
    }
    public static void runAppSimple() {
        for (int i = 0; i < 1000; i++) {
            Cleaner.clearAll();

            GameController gc = GameController.getInstance();

            Playable AM = new AISimple("AISimple1");
            Playable IL = new AISimple("AISimple2");

            Logger.printPorgress(i, 1000);

            ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL));

            gc.play(Playable);
        }
    }
    public static void runAppGoal() {
        for (int i = 0; i < 1000; i++) {
            Cleaner.clearAll();

            GameController gc = GameController.getInstance();

            Playable AM = new AIGoal("AIGoal2");
            Playable IL = new AIGoal("AIGoal1");

            Logger.printPorgress(i, 1000);

            ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL));

            gc.play(Playable);
        }
    }

    public static void runAppRandomvsSimple() {
        for (int i = 0; i < 1000; i++) {
            Cleaner.clearAll();

            GameController gc = GameController.getInstance();

            Playable AM = new AIRandom("AIRandom");
            Playable IL = new AISimple("AISimple");

            Logger.printPorgress(i, 1000);

            ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL));

            gc.play(Playable);
        }
    }
    public static void runAppSimplevsGoal() {
        for (int i = 0; i < 1000; i++) {
            Cleaner.clearAll();

            GameController gc = GameController.getInstance();

            Playable AM = new AIGoal("AIGoal");
            Playable IL = new AISimple("AISimple");

            Logger.printPorgress(i, 1000);

            ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL));

            gc.play(Playable);
        }
    }
    public static void runAppRandomvsGoal() {
        for (int i = 0; i < 1000; i++) {
            Cleaner.clearAll();

            GameController gc = GameController.getInstance();

            Playable AM = new AIRandom("AIRandom");
            Playable IL = new AIGoal("AIGoal");

            Logger.printPorgress(i, 1000);

            ArrayList<Playable> Playable = new ArrayList<>(Arrays.asList(AM, IL));

            gc.play(Playable);
        }
    }

}
