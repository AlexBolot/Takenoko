package Oka.model.plot;

import Oka.model.Bamboo;
import Oka.model.Cell;
import Oka.model.Enums.Color;
import Oka.model.plot.state.NeutralState;

import java.awt.*;
import java.util.ArrayList;

public class Plot extends Cell
{
    //region==========ATTRIBUTES===========
    private ArrayList<Bamboo> bamboo = new ArrayList<>();
    private Color        color;
    private boolean      isIrrigated;
    private NeutralState state;
    //endregion

    //region==========CONSTRUCTORS=========
    public Plot (Point coords)
    {
        super(coords);
        state = new NeutralState();
    }

    public Plot ()
    {
        super(new Point());
        this.state = new NeutralState();
    }

    public Plot (Color color, NeutralState state)
    {
        super(new Point());
        this.color = color;
        this.state = state;

        if (isIrrigated()) addBamboo();
    }

    public Plot (Point coords, Color color)
    {
        super(coords);
        this.color = color;
        this.state = new NeutralState();
    }
    //endregion

    //region==========GETTER/SETTER========
    public ArrayList<Bamboo> getBamboo ()
    {
        return bamboo;
    }

    public void setIsIrrigated (boolean bool)
    {
        if (bool) this.addBamboo();
        this.isIrrigated = bool;
    }

    public Color getColor ()
    {
        return color;
    }

    public NeutralState getState ()
    {
        return state;
    }

    public void setState (NeutralState state)
    {
        this.state = state;
    }

    public boolean isIrrigated ()
    {
        return isIrrigated || state.getIsIrrigated();
    }
    //endregion

    //region==========METHODS==============

    public void addBamboo ()
    {
        for (int i = 0; i < state.getHowManyaddBambo(); i++)
        {
            if (this.bamboo.size() < 4) this.bamboo.add(new Bamboo(this.color));
        }
    }

    /**
     return the top bamboo from the plot reserve and removes it
     todo: throw and empty exception if empty really check the code  !!!!!!

     @return Bamboo
     */
    public Bamboo giveBamboo ()
    {
        if (bamboo.size() == 0 || !state.authorizationGetBamboo()) return null;
        return bamboo.remove(bamboo.size() - 1);
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    @Override
    public boolean equals (Object obj)
    {
        if (!(obj instanceof Plot)) return false;

        Plot plot = (Plot) obj;

        return this.color.equals(plot.color) && this.getCoords().equals(plot.getCoords());
    }

    @Override
    public String toString ()
    {
        return super.toString() + " ->" + color +'-'+state.toString();
    }
    //endregion
}