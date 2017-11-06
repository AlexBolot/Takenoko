package Oka.model;


public class Enums {

    public enum GoalType
    {
        BambooGoal, PlotGoal, GardenGoal;
    }
    public enum Axis {
        x, y, z
    }
    public enum Color {
        GREEN,
        YELLOW,
        PINK,
        NONE
    }

    /* ORDRE IMPORTANT
    * Les aménagements doivent etre dans le même ordre
    * que dans le tabState[]
    * */
    public enum State {
        Neutral,
        Pond,
        Enclosure,
        Fertilizer
    }
    public enum DrawStackPlot {
        // NeutralState, PondState, EnclosureState, FertilizerState
       nbGreenPlot(), nbYellowPlot(), nbPinkPlot();

        private int tabState[] = new int[4];

        DrawStackPlot(){ }
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
    public enum DrawStackGoal{

    }

}
