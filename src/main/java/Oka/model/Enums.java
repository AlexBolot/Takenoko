package Oka.model;


public class Enums
{

    public enum GoalType
    {
        BambooGoal,
        GardenGoal,
        PlotGoal
    }

    public enum Axis
    {
        x,
        y,
        z
    }

    public enum Color
    {
        GREEN,
        YELLOW,
        PINK,
        NONE
    }

    /* ORDRE IMPORTANT
    * Les aménagements doivent etre dans le même ordre
    * que dans le tabState[]
    * */
    public enum State
    {
        Neutral,
        Pond,
        Enclosure,
        Fertilizer
    }

    public enum Action
    {
        movePanda,
        moveGardener,
        drawChannel,
        drawPlotState,
        drawGoal,
        placePlot
    }

}
