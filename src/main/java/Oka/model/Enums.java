package Oka.model;


public class Enums {

    public enum goalType{
        BambooGoal, PlotGoal, GardenGoal;
    }
    public enum Axis {
        x, y, z
    }
    public enum Color {
        GREEN, YELLOW, PINK
    }
    public enum State {
        Fertilizer, Neutral, Pen, Pond
    }
    public enum DrawStack{
        // NeutralState, PondState, PenState, FertilizerState
        nbYellowPlot(new int[]{6,1,1,1}),nbPinkPlot(new int[]{4,1,1,1}), nbGreenPlot(new int[]{6,2,2,1});

        private int tabState[] = new int[4];

        DrawStack(int tab[]){
            this.tabState = tab;
        }
        public int[] getTabState() {
            return this.tabState;
        }
        public void setTabState(int tabState[]){
             this.tabState = tabState;
        }

        public int getnbPlotByColor(){
            int total = 0;
            for(int i : tabState){
                total += i;
            }
            return total;
        }
        public static int getNbPlot(){
            return nbGreenPlot.getnbPlotByColor() + nbYellowPlot.getnbPlotByColor() + nbPinkPlot.getnbPlotByColor();
        }
    }

}
