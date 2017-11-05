package Oka.model;

import Oka.model.Enums.Color;

public class Bamboo
{
    //region==========ATTRIBUTES===========
    private Color color;
    //endregion

    //region==========CONSTRUCTORS=========
    public Bamboo (Color color)
    {
        this.color = color;
    }
    //endregion

    //region==========GETTER/SETTER========
    public Color getColor ()
    {
        return color;
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    @Override
    public boolean equals (Object obj)
    {
        if (obj == null || !(obj instanceof Bamboo)) return false;

        Bamboo comp = (Bamboo) obj;

        return this.color.equals(comp.color);
    }
    //endregion
}
