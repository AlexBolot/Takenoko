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
        //this.stream().filter(b -> b.getColor().equals(color)).forEach(b -> this.remove(b));
        int tabIndex[] = new int[this.size()];
        for (int i = 0; i < this.size(); i++)
        {
            Bamboo bamboo = this.get(i);
            if (bamboo.getColor().equals(color)) tabIndex[i] = 1;
            else tabIndex[i] = 0;
        }
        int compt = 0;
        for(int index = 0; index < tabIndex.length; index++){
            if(tabIndex[index] == 1 && compt < n) {
                compt++;
                this.remove(index);
            }
        }
    }

    public void addBamboo (Color color)
    {
        this.add(new Bamboo((color)));
    }
    //endregion
}
