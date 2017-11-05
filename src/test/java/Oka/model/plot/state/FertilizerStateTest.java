package Oka.model.plot.state;

import Oka.model.plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static Oka.model.Enums.Color.GREEN;
import static org.junit.Assert.assertEquals;

public class FertilizerStateTest
{
    private Plot plot1;

    @Before
    public void init ()
    {
        plot1 = new Plot(GREEN, new FertilizerState());
    }

    @Test
    public void testState ()
    {
        plot1.addBamboo();

        assertEquals(2, plot1.getBamboo().size());

        plot1.giveBamboo();

        assertEquals(1, plot1.getBamboo().size());
    }
}