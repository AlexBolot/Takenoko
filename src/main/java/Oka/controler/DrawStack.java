package Oka.controler;

import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Oka.model.Enums.goalType.BambooGoal;

public class DrawStack {
    private static final int MaxBambooGoal = 10;


    public static Goal draw(Enums.goalType goalType) {
        switch (goalType) {

            case BambooGoal:
                Random random = new Random();
                BambooGoal bambooGoal = new BambooGoal(2, random.nextInt(3) + 2, Color.red);
                return bambooGoal;
            case PlotGoal:
                break;
            case GardenGoal:
                break;
        }
        return null;
    }


}
