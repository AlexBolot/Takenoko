package Oka.ai.neuralnetwork;

/*..................................................................................................
 . Copyright (c)
 .
 . The NeuralNetwork	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 12/11/17 22:41
 .................................................................................................*/

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class NeuralNetwork
{
    final static int inputSize  = 188;
    final static int outputSize = 5;

    private final static String weightPath = "./src/main/resources/NNWeights.txt";
    private final static String biasPath   = "./src/main/resources/NNBias.txt";

    private int[] layers = {inputSize, 16, 16, outputSize};

    private double[][]   output           = new double[layers.length][];
    private double[][][] weights          = new double[layers.length][][];
    private double[][]   bias             = new double[layers.length][];
    private double[][]   errorSignals     = new double[layers.length][];
    private double[][]   outputDerivative = new double[layers.length][];

    public NeuralNetwork ()
    {
        double min = -0.5;
        double max = 0.5;

        for (int layer = 0; layer < layers.length; layer++)
        {
            int neuronAmount = neuronAmount(layer);

            setOutput(layer, new double[neuronAmount]);
            setErrorSignals(layer, new double[neuronAmount]);
            setOutputDerivative(layer, new double[neuronAmount]);
            setBias(layer, createRandomArray(neuronAmount, min, max));

            if (layer > 0)
            {
                int prevLayer = layer - 1;
                setWeights(layer, createRandomArray(neuronAmount(layer), neuronAmount(prevLayer), min, max));
            }
        }
    }

    public void loadFromFile ()
    {
        try
        {
            ObjectInputStream iis = new ObjectInputStream(new FileInputStream(new File(weightPath)));
            weights = (double[][][]) iis.readObject();

            iis = new ObjectInputStream(new FileInputStream(new File(biasPath)));
            bias = (double[][]) iis.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void saveToFile ()
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(weightPath)));
            oos.writeObject(weights);

            oos = new ObjectOutputStream(new FileOutputStream(new File(biasPath)));
            oos.writeObject(bias);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //region ========== getter setter Output ==============
    public double[] getOutput (int layer)
    {
        return output[layer];
    }

    public double getOutput (int layer, int neuron)
    {
        return output[layer][neuron];
    }

    public void setOutput (int layer, double[] value)
    {
        output[layer] = value;
    }

    public void setOutput (int layer, int neuron, double value)
    {
        output[layer][neuron] = value;
    }
    //endregion

    //region ========== getter setter Weights =============
    public double getWeights (int layer, int neuron, int prevNeuron)
    {
        return weights[layer][neuron][prevNeuron];
    }

    public void setWeights (int layer, double[][] value)
    {
        weights[layer] = value;
    }

    public void setWeights (int layer, int neuron, int prevNeuron, double value)
    {
        weights[layer][neuron][prevNeuron] = value;
    }
    //endregion

    //region ========== getter setter Bias ================
    public double getBias (int layer, int neuron)
    {
        return bias[layer][neuron];
    }

    public void setBias (int layer, double[] value)
    {
        bias[layer] = value;
    }

    public void setBias (int layer, int neuron, double value)
    {
        bias[layer][neuron] = value;
    }
    //endregion

    //region ========== getter setter ErrorSignals ========
    public double getErrorSignals (int layer, int neuron)
    {
        return errorSignals[layer][neuron];
    }

    public void setErrorSignals (int layer, double[] value)
    {
        errorSignals[layer] = value;
    }

    public void setErrorSignals (int layer, int neuron, double value)
    {
        errorSignals[layer][neuron] = value;
    }
    //endregion

    //region ========== getter setter OutputDerivative ====
    public void setOutputDerivative (int layer, double[] value)
    {
        outputDerivative[layer] = value;
    }

    public void setOutputDerivative (int layer, int neuron, double value)
    {
        outputDerivative[layer][neuron] = value;
    }
    //endregion

    //region ========== general getters ===================
    public int neuronAmount (int layer)
    {
        return layers[layer];
    }

    public int networkDepth ()
    {
        return layers.length;
    }
    //endregion

    public void train (double[] input, double[] target)
    {
        if (input.length != inputSize || target.length != outputSize) return;

        calculate(input);

        backPropError(target);

        updateWeights();
    }

    public double[] calculate (double... input)
    {
        if (input.length != inputSize) return null;

        setOutput(0, input);

        for (int layer = 1; layer < networkDepth(); layer++)
        {
            for (int neuron = 0; neuron < neuronAmount(layer); neuron++)
            {
                double sum = getBias(layer, neuron);

                int prevLayer = layer - 1;

                for (int prevNeuron = 0; prevNeuron < neuronAmount(prevLayer); prevNeuron++)
                {
                    sum += getOutput(prevLayer, prevNeuron) * getWeights(layer, neuron, prevNeuron);
                }

                setOutput(layer, neuron, sigmoid(sum));

                double newDerivative = getOutput(layer, neuron) * (1 - getOutput(layer, neuron));
                setOutputDerivative(layer, neuron, newDerivative);
            }
        }

        return getOutput(networkDepth() - 1);
    }

    private void backPropError (double[] target)
    {
        for (int neuron = 0; neuron < neuronAmount(networkDepth() - 1); neuron++)
        {
            int outputLayer = networkDepth() - 1;
            double outputValue = getOutput(outputLayer, neuron) - target[neuron];

            setErrorSignals(outputLayer, neuron, outputValue);
        }

        for (int layer = networkDepth() - 2; layer > 0; layer--)
        {
            for (int neuron = 0; neuron < neuronAmount(layer); neuron++)
            {
                double sum = 0;

                for (int nextNeuron = 0; nextNeuron < neuronAmount(layer + 1); nextNeuron++)
                {
                    double weight = getWeights(layer + 1, nextNeuron, neuron);
                    double erroSignal = getErrorSignals(layer + 1, nextNeuron);

                    sum += weight * erroSignal;
                }

                double newValue = sum * getOutput(layer, neuron);
                setErrorSignals(layer, neuron, newValue);
            }
        }
    }

    private void updateWeights ()
    {
        for (int layer = 1; layer < networkDepth(); layer++)
        {
            for (int neuron = 0; neuron < neuronAmount(layer); neuron++)
            {
                for (int prevNeuron = 0; prevNeuron < neuronAmount(layer - 1); prevNeuron++)
                {
                    double output = getOutput(layer - 1, prevNeuron);
                    double errorSignal = getErrorSignals(layer, neuron);

                    double delta = -0.3 * output * errorSignal;

                    setWeights(layer, neuron, prevNeuron, delta);
                }

                double delta = -0.3 * getErrorSignals(layer, neuron);

                setBias(layer, neuron, delta);
            }
        }
    }

    //region ========== Utils Methods =====================
    private double sigmoid (double x)
    {
        return 1d / (1 + Math.exp(-x));
    }

    private double[] createRandomArray (int size, double min, double max)
    {
        return new Random().doubles(size, min, max).toArray();
    }

    private double[][] createRandomArray (int sizeX, int sizeY, double min, double max)
    {
        return IntStream.range(0, sizeX).mapToObj(i -> createRandomArray(sizeY, min, max)).toArray(double[][]::new);
    }
    //endregion

    @Override
    public String toString ()
    {
        return Arrays.deepToString(weights) + "\n" + Arrays.deepToString(bias);
    }
}
