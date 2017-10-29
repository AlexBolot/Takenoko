package Oka.ai;

import Oka.model.Bamboo;

import java.util.ArrayList;

import static Oka.model.Enums.Color;

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
