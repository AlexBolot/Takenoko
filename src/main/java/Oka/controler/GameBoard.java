package Oka.controler;

import Oka.model.Cell;

import java.awt.*;
import java.util.ArrayList;

public class GameBoard {
    private static GameBoard ourInstance = new GameBoard();
    private ArrayList<Cell> grid = new ArrayList<Cell>();

    public ArrayList<Cell> getGrid () {
        return grid;
    }

    public void setGrid (ArrayList<Cell> grid) {
        this.grid = grid;
    }

    public static GameBoard getInstance () {
        return ourInstance;
    }
    public Cell getCell(Point point){
        for(Cell cell : grid){
            if (cell.getCoords()==point){
                return cell;
            }
        }
        return null;
    }
    private GameBoard () {
    }
}
