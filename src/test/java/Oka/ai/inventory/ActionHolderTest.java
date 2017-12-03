package Oka.ai.inventory;

import Oka.ai.AI;
import Oka.ai.AISimple;
import Oka.model.Enums;
import Oka.utils.Cleaner;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionHolderTest {
    private ActionHolder actionHolder;

    @Before
    public void init(){
        actionHolder = new ActionHolder();
    }

    @Test
    public void consumeAction() {

        assertTrue(actionHolder.hasActionsLeft(Enums.Action.values()[0]));

        //on consume une action et on vérifie qu'on peut plus la faire ensuite
        actionHolder.consumeAction(Enums.Action.values()[0]);
        assertFalse(actionHolder.hasActionsLeft(Enums.Action.values()[0]));

        //Mais qu'on peut quand meme faire une autre action apres
        assertTrue(actionHolder.hasActionsLeft(Enums.Action.values()[1]));
        actionHolder.consumeAction(Enums.Action.values()[1]);

        // Mais pas une troisième puisqu'on a le droit a uniquement 2 actions par tour
        assertFalse(actionHolder.hasActionsLeft(Enums.Action.values()[2]));

    }
    @Test
    public void limitedNumberOfActions() {
        Cleaner.clearAll();

        AI hal = new AISimple("Hal");
        hal.play();
        assertEquals(0, hal.getInventory().getActionHolder().getActionLeft());


    }

    @Test
    public void windWeather() {
        actionHolder.windWeather();

        // Si on tombe sur le vent (dé) on peut jouer plusieurs la même action
        actionHolder.consumeAction(Enums.Action.values()[0]);
        assertTrue(actionHolder.hasActionsLeft(Enums.Action.values()[0]));
        actionHolder.consumeAction(Enums.Action.values()[0]);
        assertFalse(actionHolder.hasActionsLeft(Enums.Action.values()[0]));

        assertFalse(actionHolder.hasActionsLeft(Enums.Action.values()[1]));


    }

}