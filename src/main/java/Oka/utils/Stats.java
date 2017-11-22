package Oka.utils;

import java.util.HashMap;

public class Stats {
    /**
     <hr>
     <h3>Hashmap used to store all the statAverage.<br>
     Used for multiple-game runs, to accumulate the statAverage.</h3>
     */
    private static HashMap<String, int[]> stats = new HashMap<>();


    public static HashMap<String, int[]> getStatAverage()
    {
        return stats;
    }

    public static void saveStatPoint(String player, int point){
        int[] tab = {0,point};
        if (stats.containsKey(player)){
            tab = stats.get(player);
            tab[1]+=point;
            stats.replace(player,tab );
        }else{
            stats.put(player,tab);
        }
    }
    public static void saveStatWinner(String player){
        int[] tab = {1,0};
        if (stats.containsKey(player)) {
            tab = stats.get(player);
            tab[0]++;
            stats.replace(player, tab);
        }else{
            stats.put(player, tab);
        }
    }
}
