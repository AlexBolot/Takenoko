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

import Oka.entities.IA.IA;

public abstract class Goal {
    private final int value;

    public Goal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean validate(IA ia){
        return false;
    }
}
