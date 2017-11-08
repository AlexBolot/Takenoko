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

public abstract class Goal
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
    //endregion

    //region==========METHODS==============
    public boolean isValidated ()
    {
        return validated;
    }
    //endregion

    //region==========EQUALS/TOSTRING======
    public String toString ()
    {
        return getClass().getSimpleName();
    }
    //endregion
}
