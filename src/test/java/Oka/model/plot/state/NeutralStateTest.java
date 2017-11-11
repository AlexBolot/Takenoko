package Oka.model.plot.state;

import Oka.model.plot.Plot;
import org.junit.Before;
import org.junit.Test;

import static Oka.model.Enums.Color.GREEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NeutralStateTest
{


    private Plot plot1;

    @Before
    public void init ()
    {
        plot1 = new Plot(GREEN, new NeutralState());
    }

    @Test
    public void testState ()
    {
        plot1.addBamboo();

        assertEquals(1, plot1.getBamboo().size());

        plot1.giveBamboo();

        assertEquals(0, plot1.getBamboo().size());
    }

    @Test
    public void equals() throws Exception {
        assertFalse(new NeutralState().equals(new FertilizerState()));
        assertTrue(new NeutralState().equals(new NeutralState()));
        assertTrue(new FertilizerState().equals(new FertilizerState()));
        assertTrue(new PondState().equals(new PondState()));


    }
}