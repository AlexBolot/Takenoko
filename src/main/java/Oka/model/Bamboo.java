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
}
