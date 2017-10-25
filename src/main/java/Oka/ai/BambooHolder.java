package Oka.ai;

import Oka.model.Bamboo;

import java.awt.*;
import Oka.model.Enums;
import java.util.ArrayList;

public class BambooHolder extends ArrayList {


    public int countBamboo(Enums.Color color) {
        int countBamboo = 0;
        for(Object o : this){
            Bamboo bamboo = (Bamboo) o;
            if(bamboo.getColor().equals(color)){
                countBamboo++;
            }
        }
        return countBamboo;
    }

    public void removeByColor(Enums.Color color, int n){
        int tabIndex[] = new int[n];
        for(int i=0; i<this.size() && tabIndex.length < n; i++) {
            Bamboo bamboo = (Bamboo) this.get(i);
            if(bamboo.getColor().equals(color))
                tabIndex[tabIndex.length] = i;
        }
    }

    public void addBamboo(Enums.Color color){
        this.add(new Bamboo((color)));
    }

}
