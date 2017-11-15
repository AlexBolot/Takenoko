package Oka.ai;

/*..................................................................................................
 . Copyright (c)
 .
 . The AISimpleTest	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 16/10/17 14:26
 .................................................................................................*/

import Oka.controler.DrawStack;
import Oka.controler.GameBoard;
import Oka.controler.GameController;
import Oka.entities.Gardener;
import Oka.entities.Panda;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.goal.BambooGoal;
import Oka.model.goal.Goal;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AISimpleTest {


    private Point p01 = new Point(0, 1);
    private Point p10 = new Point(1, 0);

    @Before
    public void before() {
        Cleaner.clearAll();
        Plot plot1 = new Plot(p01, Enums.Color.PINK);
        plot1.addBamboo();
        GameBoard.getInstance().addCell(plot1);

        Plot plot2 = new Plot(p10, Enums.Color.GREEN);
        GameBoard.getInstance().addCell(plot2);
    }

    @Test
    public void movePanda() throws Exception {
        Panda.getInstance().setCoords(new Point());

        GameBoard board = GameBoard.getInstance();
        Cell plot10 = board.getGrid().remove(p10);

        AISimple AI = new AISimple("Momo");

        GameController.getInstance().setCurrentPlayer(AI);

        AI.getInventory().addGoal(new BambooGoal(3, 1, Enums.Color.PINK));
        AI.movePanda();
        assertEquals(p01, Panda.getInstance().getCoords());

        Panda.getInstance().setCoords(new Point());

        board.getGrid().remove(p01);
        board.getGrid().put(p10, plot10);

        AI = new AISimple("Momo");

        GameController.getInstance().setCurrentPlayer(AI);

        AI.getInventory().addGoal(new BambooGoal(3, 1, Enums.Color.GREEN));
        AI.movePanda();
        assertEquals(p10, Panda.getInstance().getCoords());
    }

    @Test
    public void moveGardener() throws Exception {

        GameBoard board = GameBoard.getInstance();

        Cell plot10 = board.getGrid().remove(p10);

        Panda.getInstance().setCoords(new Point());

        AISimple AI = new AISimple("Momo");

        AI.getInventory().addGoal(new BambooGoal(3, 1, Enums.Color.PINK));
        AI.moveGardener();
        assertEquals(p01, Gardener.getInstance().getCoords());
        Gardener.getInstance().setCoords(new Point());

        board.getGrid().remove(p01);
        board.getGrid().put(p10, plot10);
        AI = new AISimple("Momo");

        AI.getInventory().addGoal(new BambooGoal(3, 1, Enums.Color.GREEN));
        AI.moveGardener();
        assertEquals(p10, Gardener.getInstance().getCoords());

    }

    @Test
    public void drawChannelTest() {
        Cleaner.clearAll();

        AISimple ai = new AISimple("Momo");
        Assert.assertTrue(ai.drawChannel());
        assertTrue(ai.getInventory().hasChannel());

        for (int i = 0; i < 19; i++) {
            DrawStack.getInstance().drawChannel();
        }
        assertFalse(ai.drawChannel());
    }

    @Test
    public void placeChannelSmallGrid() {
        Cleaner.clearAll();
        Plot plot10G = new Plot(new Point(1, 0), Enums.Color.GREEN);
        Plot plot01P = new Plot(new Point(0, 1), Enums.Color.PINK);
        GameBoard board = GameBoard.getInstance();

        board.addCell(plot01P);
        board.addCell(plot10G);

        AISimple roger = new AISimple("Roger");
        roger.drawChannel();
        BambooGoal bg = new BambooGoal(3, 3, Enums.Color.GREEN);

        roger.getInventory().goalHolder().add(bg);

        assertTrue(roger.placeChannel());

        assertTrue(board.verifIrrigation(new Point(1, 0), new Point(0, 1)));

    }


    @Test
    public void placeChannelMediumGrid() throws Exception {
        Cleaner.clearAll();
        Plot plot10G = new Plot(new Point(1, 0), Enums.Color.GREEN);
        Plot plot01P = new Plot(new Point(0, 1), Enums.Color.PINK);
        Plot plot11Y = new Plot(new Point(1, 1), Enums.Color.YELLOW);
        Plot plot02P = new Plot(new Point(0, 2), Enums.Color.PINK);
        GameBoard board = GameBoard.getInstance();

        board.addCell(plot01P);
        board.addCell(plot10G);
        board.addCell(plot11Y);
        board.addCell(plot02P);

        AISimple roger = new AISimple("Roger");

        roger.drawChannel();
        roger.getInventory().resetActionHolder();
        roger.drawChannel();


        BambooGoal bg = new BambooGoal(3, 3, Enums.Color.YELLOW);
        board.addIrrigation(new Point(0, 1), new Point(1, 0));
        board.addIrrigation(new Point(1, 1), new Point(1, 0));
        roger.getInventory().goalHolder().add(bg);

        assertTrue(roger.placeChannel());
        assertTrue(board.verifIrrigation(new Point(1, 1), new Point(0, 1)));
        assertTrue(roger.placeChannel());
        assertTrue(board.verifIrrigation(new Point(1, 1), new Point(0, 2)));

    }


}