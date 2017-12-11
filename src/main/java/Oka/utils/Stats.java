package Oka.utils;

import Oka.ai.AI;
import Oka.ai.Playable;

import java.util.ArrayList;
import java.util.HashMap;

public class Stats {
    /**
     <hr>
     <h3>Hashmap used to store all the statAverage.<br>
     Used for multiple-game runs, to accumulate the statAverage.</h3>
     */
    private static HashMap<String, int[]> statsPlayer = new HashMap<>();
    private static int nbTour = 0;
    private static int maxTour = 0;
    private static int minTour = 500;
    private static HashMap<String, int[]> statsGoal = new HashMap<>();


    public static HashMap<String, int[]> getStatAverage()
    {
        return statsPlayer;
    }
    public static int getNbTour(){return nbTour;}
    public static int getMaxTour(){return maxTour;}
    public static int getMinTour(){return minTour;}
    public static HashMap<String, int[]> getStatsGoal() {return statsGoal;}

    public static void resetStats(){
        statsPlayer = new HashMap<>();
        nbTour = 0;
        maxTour = 0;
        minTour = 500;
        statsGoal = new HashMap<>();
    }
    public static void saveStatPoint(String player, int point){
        int[] tab = {0,point};
        if (statsPlayer.containsKey(player)){
            tab = statsPlayer.get(player);
            tab[1]+=point;
            statsPlayer.replace(player,tab );
        }else{
            statsPlayer.put(player,tab);
        }
    }
    public static void saveStatWinner(String player){
        int[] tab = {1,0};
        if (statsPlayer.containsKey(player)) {
            tab = statsPlayer.get(player);
            tab[0]++;
            statsPlayer.replace(player, tab);
        }else{
            statsPlayer.put(player, tab);
        }
    }
    public static void saveStatTurn(int turn){
        nbTour += turn;
        if(maxTour<turn)
            maxTour = turn;
        else if(minTour>turn)
            minTour = turn;
    }
    public static void saveStatGoal(ArrayList<Playable> listAI){
        int [] PointWin, PointWinInCurrentGame;

        for(Playable ai : listAI) {
            PointWinInCurrentGame = ai.getInventory().getNbGoalByType(true);

            if (statsGoal.containsKey(ai.getName())) {
                PointWin = statsGoal.get(ai.getName());
                PointWin[0] += PointWinInCurrentGame[0];
                PointWin[1] += PointWinInCurrentGame[1];
                PointWin[2] += PointWinInCurrentGame[2];
                statsGoal.replace(ai.getName(), PointWin);
            }else{
                statsGoal.put(ai.getName(), PointWinInCurrentGame);
            }
        }
    }
}
