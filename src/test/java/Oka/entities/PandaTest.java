package Oka.entities;

import Oka.ai.AIRandom;
import Oka.ai.AISimple;
import Oka.controler.GameBoard;
import Oka.controler.GameController;
import Oka.model.plot.Plot;
import Oka.utils.Cleaner;
import org.junit.After;
import org.junit.Test;

import java.awt.*;

import static Oka.model.Enums.Color.*;
import static org.junit.Assert.*;

public class PandaTest
{
    private Panda panda = Panda.getInstance();

    @After
    public void tearDown ()
    {
        //Placing panda back on the pond.
        Cleaner.cleanPanda();

        //Reset the grid and available cells.
        Cleaner.cleanGameBoard();
    }

    @Test
    public void move() {
        GameBoard board = GameBoard.getInstance();
        GameController gameController = GameController.getInstance();

        AISimple aiSimple = new AISimple("testAI");
        gameController.setCurrentPlayer(aiSimple);

        Point point1 = new Point(0, 1);
        Point point2 = new Point(1, 0);
        Point point3 = new Point(1, -1);

        Plot plot1 = new Plot(point1, PINK);
        Plot plot2 = new Plot(point2, PINK);
        Plot plot3 = new Plot(point3, GREEN);

        board.addCell(plot1);
        board.addCell(plot2);
        board.addCell(plot3);

        //Plots come with 1 bamboo when they are placed near the plot.
        assertEquals(1, plot1.getBamboo().size());
        assertEquals(1, plot2.getBamboo().size());
        assertEquals(1, plot3.getBamboo().size());

        //There is no bamboo yet in aiSimple's inventory (current Player)
        assertEquals(aiSimple.getInventory().bambooHolder().size(), 0);

        panda.move(point1);

        //The panda harvested 1 bamboo from Plot1.
        //The other plots didn't change because ther is no side
        //effct, no matter the color.
        assertEquals(0, plot1.getBamboo().size());
        assertEquals(1, plot2.getBamboo().size());
        assertEquals(1, plot3.getBamboo().size());

        //There is now a bamboo in aiSimple's inventory and it has the same color
        //as the plot it was harvested on.
        assertEquals(aiSimple.getInventory().bambooHolder().size(), 1);
        assertEquals(aiSimple.getInventory().bambooHolder().countBamboo(PINK), 1);
        assertEquals(aiSimple.getInventory().bambooHolder().countBamboo(YELLOW), 0);
        assertEquals(aiSimple.getInventory().bambooHolder().countBamboo(GREEN), 0);
    }

    @Test
    public void testGetAndSetCoords() {
        Point p1 = new Point(5, 1);
        Point p2 = new Point(3, 4);

        panda.setCoords(p2);

        assertEquals(p2, panda.getCoords());
        assertNotEquals(p1, panda.getCoords());
    }

    @Test
    public void contiguousMove() {
        Cleaner.clearAll();
        GameController.getInstance().setCurrentPlayer(new AIRandom("ai"));
        GameBoard board = GameBoard.getInstance();

        board.addCell(new Plot(new Point(1, 0), GREEN));
        board.getAvailableSlots().add(new Point(3, 0));
        board.addCell(new Plot(new Point(3, 0), GREEN));

        panda.move(new Point(1, 0));
        assertFalse(board.canMoveEntity(panda,new Point(3,0)));
        //test x1=x2
        Cleaner.clearAll();
        GameController.getInstance().setCurrentPlayer(new AIRandom("ai"));
        board= GameBoard.getInstance();
        board.addCell(new Plot(new Point(0,1), GREEN));
        board.getAvailableSlots().add(new Point(0,5));
        board.addCell(new Plot(new Point(0,5),GREEN));
        panda.move(new Point(0,1));
        assertFalse((board.canMoveEntity(panda, new Point(0,5))));

    }

}