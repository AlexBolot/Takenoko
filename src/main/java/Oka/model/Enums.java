package Oka.model;

public class Enums {

    public enum goalType{
        BambooGoal, PlotGoal, GardenerGoal;
    }
    public enum Axis {
        x, y, z
    }
    public enum Color {
        GREEN, YELLOW, PINK
    }
    public enum DrawStack{
        nbPinkPlot(7), nbYellowPlot(9), nbGreenPlot(11);

        private int nb ;
        DrawStack(int nb){
            this.nb = nb;
        }
        public int getNb() {
            return this.nb;
        }
    }

}
