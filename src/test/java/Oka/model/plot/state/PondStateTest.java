package Oka.model.plot.state;

import Oka.model.plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static Oka.model.Enums.Color.GREEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PondStateTest
{
    private Plot plot1;

    @Before
    public void init ()
    {
        plot1 = new Plot(GREEN, new PondState());
        plot1.setIsIrrigated(false);
    }

    @Test
    public void testState ()
    {
        assertEquals(1, plot1.getBamboo().size());

        plot1.giveBamboo();

        assertEquals(0, plot1.getBamboo().size());

        assertTrue(plot1.isIrrigated());
    }
}