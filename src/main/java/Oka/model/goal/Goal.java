package Oka.model.goal;

/*..................................................................................................
 . Copyright (c)
 .
 . The Goal	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 14/10/17 22:27
 .................................................................................................*/

public class Goal
{
    //region==========ATTRIBUTES===========
    private final int     value;
    private       boolean validated;
    //endregion

    //region==========CONSTRUCTORS=========
    public Goal (int value)
    {
        this.value = value;
    }

    public Goal (int value, boolean validated)
    {
        this.value = value;
        this.validated = validated;
    }
    //endregion

    //region==========GETTER/SETTER========
    public void setValidated (boolean validated)
    {
        this.validated = validated;
    }

    public int getValue ()
    {
        return value;
    }

    public double getRatio ()
    {
        return 0;
    }
    //endregion

    //region==========METHODS==============
    public boolean isValidated ()
    {
        return validated;
    }

    protected double sigmoid (double x)
    {
        return 1d / (1 + Math.exp(-x));
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    public String toString ()
    {
        return "EmperorGoal";
    }
    //endregion
}
