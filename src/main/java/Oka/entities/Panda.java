package Oka.entities;

import Oka.controler.GameBoard;
import Oka.controler.GameController;
import Oka.model.Bamboo;
import Oka.model.Cell;
import Oka.model.plot.Plot;

import java.awt.*;

public class Panda extends Entity
{
    //region==========ATTRIBUTES===========
    private static Panda ourInstance = new Panda();
    //endregion//

    //region==========GETTER/SETTER========
    public static Panda getInstance ()
    {
        return ourInstance;
    }

    @Override
    public void setCoords (Point point)
    {
        if (point == null) throw new IllegalArgumentException("Parameter is null");

        super.setCoords(point);

        Cell arrivalCell = GameBoard.getInstance().getGrid().get(point);

        if (arrivalCell instanceof Plot)
        {
            Plot arrivalPlot = (Plot) arrivalCell;

            Bamboo bamboo = arrivalPlot.giveBamboo();

            if (bamboo == null) return;

            GameController.getInstance().getCurrentPlayer().addBamboo(bamboo.getColor());
        }
    }
    //endregion
}
