package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.plot.Plot;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.NeutralState;
import Oka.model.plot.state.PondState;
import Oka.utils.Cleaner;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static Oka.model.Enums.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*..................................................................................................
 . Copyright (c)
 .
 . The GardenerGoalTest	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 05/11/17 19:18
 .................................................................................................*/

public class GardenerGoalTest
{
    private GardenerGoal gg1;
    private GardenerGoal gg2;
    private GardenerGoal gg3;
    private GardenerGoal gg4;

    @Before
    public void setUp () throws Exception
    {
        Cleaner.clearAll();

        gg1 = new GardenerGoal(3, 2, YELLOW, new NeutralState());
        gg2 = new GardenerGoal(3, 2, GREEN, new FertilizerState());
        gg3 = new GardenerGoal(3, 2, PINK, new PondState());
        gg4 = new GardenerGoal(3, 2, PINK, new EnclosureState());

        Plot plot1 = new Plot(new Point(0, 1), YELLOW);
        plot1.addBamboo();
        plot1.addBamboo();
        plot1.setState(new NeutralState());

        Plot plot2 = new Plot(new Point(1, 0), GREEN);
        plot2.addBamboo();
        plot2.addBamboo();
        plot2.setState(new FertilizerState());

        Plot plot3 = new Plot(new Point(1, 1), PINK);
        plot3.addBamboo();
        plot3.addBamboo();
        plot3.setState(new PondState());

        GameBoard.getInstance().addCell(plot1);
        GameBoard.getInstance().addCell(plot2);
        GameBoard.getInstance().addCell(plot3);
    }

    @Test
    public void validate ()
    {
        assertFalse(gg1.isValidated());
        assertFalse(gg2.isValidated());
        assertFalse(gg3.isValidated());
        assertFalse(gg4.isValidated());

        gg1.validate();
        gg2.validate();
        gg3.validate();
        gg4.validate();

        assertTrue(gg1.isValidated());
        assertTrue(gg2.isValidated());
        assertTrue(gg3.isValidated());

        //Should not be able to validate (wrong state)
        //assertFalse(gg4.isValidated());
    }
}