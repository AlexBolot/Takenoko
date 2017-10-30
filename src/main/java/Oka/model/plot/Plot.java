package Oka.model.plot;

import Oka.model.Bamboo;
import Oka.model.Cell;
import Oka.model.Enums;
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

    public Plot (Color color)
    {
        super(new Point());
        this.color = color;
        this.state = new NeutralState();
    }

    public Plot (Color color, NeutralState state)
    {
        super(new Point());
        this.color = color;
        this.state = state;
    }

    public Plot (Point coords, Color color)
    {
        super(coords);
        this.color = color;
        state = new NeutralState();
        addBamboo();
    }
    //endregion

    //region==========GETTER/SETTER========
    public ArrayList<Bamboo> getBamboo ()
    {
        return bamboo;
    }

    public void setBamboo (ArrayList<Bamboo> bamboo)
    {
        this.bamboo = bamboo;
    }

    public void setIsIrrigated (boolean bool)
    {
        this.isIrrigated = bool;
    }

    public Color getColor ()
    {
        return color;
    }

    public NeutralState getState() {
        return state;
    }

    public boolean isIrrigated() {
        return isIrrigated || state.getIsIrrigated();
    }

    public void setState(NeutralState state){
        this.state=state;
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

        //fixme Vraiment trop compliqué pour ce dont on a besoin :D

        /*boolean equality;
        equality = plot.bamboo.equals(this.bamboo);
        equality = equality && plot.getCoords().equals(this.getCoords());

        if (this.getColor() != null && plot.getColor() != null)
        {

            equality = equality && plot.getColor().equals(this.getColor());
        }
        else
        {
            equality = equality && this.getColor() == plot.getColor();
        }

        return equality;*/
    }

    @Override
    public String toString ()
    {
        return super.toString() + " " + color;
    }
    //endregion
}