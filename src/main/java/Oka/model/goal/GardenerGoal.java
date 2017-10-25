package Oka.model.goal;

import Oka.controler.GameBoard;
import Oka.model.Cell;
import Oka.model.Enums;
import Oka.model.plot.Plot;

import java.awt.*;
import java.util.HashMap;

public class GardenerGoal extends Goal {
    private Enums.Color color;
    private int bambooAmount;

    public GardenerGoal (int value,int bambooAmount,Enums.Color color) {
        super(value);
        this.bambooAmount=bambooAmount;
        this.color=color;
    }

    public int getBambooAmount () {
        return bambooAmount;
    }

    public void setBambooAmount (int bambooAmount) {
        this.bambooAmount = bambooAmount;
    }


    public Enums.Color getColor () {
        return color;
    }

    public void setColor (Enums.Color color) {
        this.color = color;
    }


    public String toString(){
        return super.toString() +" bambooAmount = "+bambooAmount +" plotColor = " + color;
    }

    public boolean validate(){
        HashMap<Point, Cell> grid = GameBoard.getInstance().getGrid();
        for(Cell cell : grid.values()) {
            if ((cell instanceof Plot) && ((Plot) cell).getColor().equals(getColor()) && (((Plot) cell).getBamboo().size() == (getBambooAmount()))) {
                setValidated(true);


            }
        }
        return false;
    }

}
