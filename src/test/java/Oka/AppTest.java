package Oka;

import Oka.utils.Logger;
import org.junit.Test;

public class AppTest {

    @Test
    public void appTest(){
        int nbParti = 500;
        Logger.setLoggerMode(nbParti);

        //App.runAppSimple(nbParti);
        //System.out.println("---------Success AISImple vs AISimple---------");
        App.runAppGoal();
        System.out.println("---------Success AIGoal vs AIGoal---------");
        App.runAppRandom();
        System.out.println("---------Success AIRandom vs AIRandom---------");

        App.runAppRandomvsSimple();
        System.out.println("---------Success AIRandom vs AISimple---------");
        App.runAppSimplevsGoal();
        System.out.println("---------Success AIGoal vs AISimple---------");
        App.runAppRandomvsGoal();
        System.out.println("---------Success AIGoal vs AIRandom---------");

    }
}
