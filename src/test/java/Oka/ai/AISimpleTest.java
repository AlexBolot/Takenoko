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
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

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
    public void movePanda() {
        Panda.getInstance().setCoords(new Point());

        GameBoard board = GameBoard.getInstance();
        Cell plot10 = board.getGrid().remove(p10);

        AISimple AI = new AISimple("Momo");
        AI.getInventory().goalHolder().clear();
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
    public void moveGardener() {

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
    public void drawIrrigationTest() {
        Cleaner.clearAll();

        AISimple ai = new AISimple("Momo");
        Assert.assertTrue(ai.drawIrrigation());
        assertTrue(ai.getInventory().hasIrrigation());

        for (int i = 0; i < 19; i++) {
            DrawStack.getInstance().drawIrrigation();
        }
        assertFalse(ai.drawIrrigation());
    }

    @Test
    public void placeIrrigationSmallGrid() {
        Cleaner.clearAll();
        Plot plot10G = new Plot(new Point(1, 0), Enums.Color.GREEN);
        Plot plot01P = new Plot(new Point(0, 1), Enums.Color.PINK);
        GameBoard board = GameBoard.getInstance();

        board.addCell(plot01P);
        board.addCell(plot10G);

        AISimple roger = new AISimple("Roger");
        roger.drawIrrigation();
        BambooGoal bg = new BambooGoal(3, 3, Enums.Color.GREEN);

        roger.getInventory().goalHolder().add(bg);

        assertTrue(roger.placeIrrigation());

        assertTrue(board.verifIrrigation(new Point(1, 0), new Point(0, 1)));

    }


    @Test
    public void placeIrrigationMediumGrid() {
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

        roger.drawIrrigation();
        roger.getInventory().resetActionHolder();
        roger.drawIrrigation();


        BambooGoal bg = new BambooGoal(3, 3, Enums.Color.YELLOW);
        board.addIrrigation(new Point(0, 1), new Point(1, 0));
        board.addIrrigation(new Point(1, 1), new Point(1, 0));
        roger.getInventory().goalHolder().add(bg);

        assertTrue(roger.placeIrrigation());
        assertTrue(board.verifIrrigation(new Point(1, 1), new Point(0, 1)));
        assertTrue(roger.placeIrrigation());
        assertTrue(board.verifIrrigation(new Point(1, 1), new Point(0, 2)));

    }


}