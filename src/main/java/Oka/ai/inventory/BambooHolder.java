package Oka.ai.inventory;

import Oka.model.Bamboo;

import java.util.ArrayList;

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
        int tabIndex[] = new int[n];
        for (int i = 0; i < this.size() && tabIndex.length < n; i++)
        {
            Bamboo bamboo = this.get(i);
            if (bamboo.getColor().equals(color)) tabIndex[tabIndex.length - 1] = i;
        }
    }

    public void addBamboo (Color color)
    {
        this.add(new Bamboo((color)));
    }
    //endregion
}
