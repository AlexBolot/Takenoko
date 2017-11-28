package Oka.ai.neuralnetwork;

/*..................................................................................................
 . Copyright (c)
 .
 . The NNTranslator	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 11/11/17 21:16
 .................................................................................................*/

import Oka.ai.AISimple;
import Oka.ai.Playable;
import Oka.ai.inventory.*;
import Oka.entities.Entity;
import Oka.model.Cell;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.GardenerGoal;
import Oka.model.goal.Goal;
import Oka.model.goal.PlotGoal;
import Oka.model.plot.Plot;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static Oka.model.Enums.Action.*;
import static Oka.model.Enums.Color.*;
import static Oka.model.Enums.State.*;

/*
interesting k value for sigmoid :

  k       delta
0.50 = [ -6;  6]
0.15 = [-20; 20]
0.10 = [-30; 30]
0.08 = [-40; 40]

*/

public class NNTranslator
{
    private static final String   trainingsMainPath    = "./src/main/resources/trainings/";
    private static final String   lastCaseIndexPath    = "./src/main/resources/LastCaseIndex.txt";
    public               double[] inputValues          = new double[NeuralNetwork.inputSize];
    public               double[] expectedOutputValues = new double[NeuralNetwork.outputSize];
    private              int      lastIndex            = 0;

    //region ============ Save methods ===========
    public void saveLastCaseIndex (Integer newIndex)
    {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(lastCaseIndexPath))))
        {
            oos.writeObject(newIndex);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveInput (int fileIndex)
    {
        String trainingCasePath = trainingsMainPath + "/InputCase" + fileIndex + ".txt";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(trainingCasePath))))
        {
            oos.writeObject(inputValues);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveOutput (int fileIndex)
    {
        String trainingCasePath = trainingsMainPath + "/OutputCase" + fileIndex + ".txt";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(trainingCasePath))))
        {
            oos.writeObject(expectedOutputValues);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    //endregion

    //region ============ Load methods ===========
    public int loadLastCaseIndex ()
    {
        int index = 0;

        try (ObjectInputStream iis = new ObjectInputStream(new FileInputStream(new File(lastCaseIndexPath))))
        {
            index = (int) iis.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return index;
    }

    public void loadInput (int fileIndex)
    {
        String trainingCasePath = trainingsMainPath + "/InputCase" + fileIndex + ".txt";

        try (ObjectInputStream iis = new ObjectInputStream(new FileInputStream(new File(trainingCasePath))))
        {
            inputValues = (double[]) iis.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void loadOutput (int fileIndex)
    {
        String trainingCasePath = trainingsMainPath + "/OutputCase" + fileIndex + ".txt";

        try (ObjectInputStream iis = new ObjectInputStream(new FileInputStream(new File(trainingCasePath))))
        {
            expectedOutputValues = (double[]) iis.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void loadTraining (int fileIndex)
    {
        loadInput(fileIndex);
        loadOutput(fileIndex);
    }
    //endregion

    //region ============ Add methods ============
    public void addOutput (double... values)
    {
        if (expectedOutputValues.length == values.length) expectedOutputValues = Arrays.copyOf(values, values.length);
    }

    public void addValue (double value)
    {
        if (lastIndex == inputValues.length) throw new IllegalArgumentException("Out of capacity");

        inputValues[lastIndex++] = value;
    }

    public void addCells (ArrayList<Cell> cells)
    {
        for (int i = 1; i < 28; i++)
        {
            if (i < cells.size() && cells.get(i) != null)
            {
                Plot plot = (Plot) cells.get(i);

                //region -> color
                switch (plot.getColor())
                {
                    case GREEN:
                        addValue(0);
                        break;
                    case YELLOW:
                        addValue(0.5);
                        break;
                    case PINK:
                        addValue(1);
                        break;
                }
                //endregion

                //region -> coords x and y
                addValue(mySigmoid(plot.getCoords().getX(), 0.10));
                addValue(mySigmoid(plot.getCoords().getY(), 0.10));
                //endregion

                //region -> bambooSize
                addValue(plot.getBamboo().size() / 4f);
                //endregion

                //region -> plotState
                switch (plot.getState().getState())
                {
                    case Neutral:
                        addValue(0.25);
                        break;
                    case Pond:
                        addValue(0.5);
                        break;
                    case Fertilizer:
                        addValue(0.75);
                        break;
                    case Enclosure:
                        addValue(1);
                        break;
                }
                //{neutral=0.25 -- pond=0.5 -- fertil=0.75 -- enclos=0.1}
                //endregion

                //region -> irrigated
                addValue(plot.isIrrigated() ? 1 : 0);
                //endregion
            }
            else
            {
                addValue(0);
                addValue(0);
                addValue(0);
                addValue(0);
                addValue(0);
                addValue(0);
            }
        }
    }

    public void addEntity (Entity entity)
    {
        addValue(mySigmoid(entity.getCoords().getX(), 0.10));
        addValue(mySigmoid(entity.getCoords().getY(), 0.10));
    }

    public void addInventory (Playable playable)
    {
        Inventory inventory = playable.getInventory();

        addBambooHolder(inventory.bambooHolder());

        addPlotStateHolder(inventory.plotStates());

        addActionHolder(inventory.getActionHolder());

        addGoalHolder(inventory.goalHolder(), playable);

        addIrrigations(inventory.getIrrigationAmount());
    }

    private void addActionHolder (ActionHolder actionHolder)
    {
        addValue(.5d * actionHolder.get(moveGardener));
        addValue(.5d * actionHolder.get(movePanda));
        addValue(.5d * actionHolder.get(drawGoal));
        addValue(.5d * actionHolder.get(drawIrrigation));
        addValue(.5d * actionHolder.get(placePlot));
    }

    private void addBambooHolder (BambooHolder holder)
    {
        double greenAmount = holder.countBamboo(GREEN);
        double yellowAmount = holder.countBamboo(YELLOW);
        double pinkAmount = holder.countBamboo(PINK);

        addValue(mySigmoid(greenAmount, 0.50));
        addValue(mySigmoid(yellowAmount, 0.50));
        addValue(mySigmoid(pinkAmount, 0.50));
    }

    private void addPlotStateHolder (PlotStateHolder holder)
    {
        double pondAmount = holder.countByState(Pond);
        double enclosureAmount = holder.countByState(Enclosure);
        double fertilizerAmount = holder.countByState(Fertilizer);

        addValue(pondAmount / 3);
        addValue(enclosureAmount / 3);
        addValue(fertilizerAmount / 3);
    }

    private void addGoalHolder (GoalHolder holder, Playable playable)
    {
        for (int i = 0; i < 5; i++)
        {
            if (i < holder.size())
            {
                Goal goal = holder.get(i);

                addValue(getGoalTypeValue(goal));

                if (playable instanceof AISimple)
                {
                    AISimple aiSimple = (AISimple) playable;

                    addValue(mySigmoid(aiSimple.getCompletion(goal), 0.50));
                }
                else
                {
                    addValue(0);
                }
            }
            else
            {
                addValue(0);
                addValue(0);
            }
        }
    }

    private void addIrrigations (int amount)
    {
        addValue(mySigmoid(amount, 0.15));
    }
    //endregion

    private double getGoalTypeValue (Goal goal)
    {
        if (goal instanceof BambooGoal) return 0;
        if (goal instanceof GardenerGoal) return 0.5;
        if (goal instanceof PlotGoal) return 1;

        return 0;
    }

    private double mySigmoid (double x, double k)
    {
        return 1d / (1 + Math.exp(-k * x));
    }

    @Override
    public String toString ()
    {
        StringBuilder str = new StringBuilder();

        for (double d : inputValues)
        {
            str.append(d).append("\n");
        }

        return str.toString();
    }
}
