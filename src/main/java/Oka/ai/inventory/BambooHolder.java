package Oka.ai.inventory;

import Oka.model.Bamboo;

import java.util.ArrayList;
import java.util.Iterator;

import static Oka.model.Enums.Color;

/*..................................................................................................
 . Copyright (c)
 .
 . The BambooHolder	 Class was Coded by : Team_A
 .
 . Members :
 . -> Alexandre Bolot
 . -> Mathieu Paillart
 . -> Grégoire Peltier
 . -> Théos Mariani
 .
 . Last Modified : 29/10/17 16:40
 .................................................................................................*/

public class BambooHolder extends ArrayList<Bamboo>
{
    //region==========METHODS==============
    public int countBamboo (Color color)
    {
        return (int) this.stream().filter(bamboo -> bamboo.getColor().equals(color)).count();
    }

    public void removeByColor (Color color, int n)
    {
        int compt = 0;
        for (Iterator<Bamboo> it = this.iterator();it.hasNext();)
        {
            Bamboo bamboo = it.next();
            if (bamboo.getColor().equals(color)){
                it.remove();
                compt++;
            }
            if(compt==n)
                break;
        }
    }

    public void addBamboo (Color color)
    {
        this.add(new Bamboo((color)));
    }
    //endregion
}
