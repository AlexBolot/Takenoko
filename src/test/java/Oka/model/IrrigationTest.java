package Oka.model;

import Oka.model.plot.Plot;
import javafx.scene.effect.BlurType;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class IrrigationTest {
    Irrigation i1;
    Irrigation i2;
    Irrigation i3;
    Plot plot1;
    Plot plot2;
    Plot plot3;

    @Before
    public void setup() {


        i1 = new Irrigation();
        i2 = new Irrigation();
        i3 = new Irrigation();
        plot1 = new Plot(new Point(1, 1), Enums.Color.GREEN);
        plot2 = new Plot(new Point(1, 0), Enums.Color.PINK);
        plot3 = new Plot(new Point(0, 1), Enums.Color.PINK);

    }


    @Test
    public void equals() {

        i1.setPlot1(plot1);
        i1.setPlot2(plot2);

        i2.setPlot1(plot2);
        i2.setPlot2(plot1);

        i3.setPlot1(plot1);
        i3.setPlot2(plot3);

        assertEquals(i1, i2);
        assertEquals(i2, i1);
        assertNotEquals(i3, i1);
        assertNotEquals(i3, i2);
    }

    @Test
    public void hashCodeSymetry() {
        i1.setPlot1(plot1);
        i1.setPlot2(plot2);

        i2.setPlot1(plot2);
        i2.setPlot2(plot1);

        assertEquals(i1.hashCode(), i2.hashCode());


    }


}