package Oka.ai.inventory;

import Oka.model.Bamboo;
import Oka.model.Enums;

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
    /**
     * @param color Take a color of bamboo as parameter ( green/yellow/pink )
     * @return the number of bamboo in the inventory.
     */
    public int countBamboo (Color color)
    {
        return (int) this.stream().filter(bamboo -> bamboo.getColor().equals(color)).count();
    }

    /**
     * @return la couleurs des bamboo qu'elle a le moins.
     */
    public Color getLessColorBamboo()
    {
        int min = 10;
        Color c = Color.NONE;
        for(Color color : Enums.Color.values()){
            if(countBamboo(color)<min){
                c = color;
                min = countBamboo(color);
            }
        }
        return c;
    }

    /**
     * @param color the color of the bamboo.
     * @param n the number of bamboo we want to remove.
     *  Remove from the inventory the number of bamboo with the color selected.
     */
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

    /**
     * @param color Color of bamboo added.
     * A method which addBamboo in the inventory.
     */
    public void addBamboo (Color color)
    {
        this.add(new Bamboo((color)));
    }
    //endregion
}
