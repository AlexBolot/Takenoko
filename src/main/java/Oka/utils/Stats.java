package Oka.utils;

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
    private static int[] statsGoal = new int[3];


    public static HashMap<String, int[]> getStatAverage()
    {
        return statsPlayer;
    }
    public static int getNbTour(){return nbTour;}
    public static int getMaxTour(){return maxTour;}
    public static int getMinTour(){return minTour;}
    public static int[] getStatsGoal() {return statsGoal;}

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
    public static void saveStatGoal(int[] tab){
        statsGoal[0] += tab[0];
        statsGoal[1] += tab[1];
        statsGoal[2] += tab[2];
    }
}
