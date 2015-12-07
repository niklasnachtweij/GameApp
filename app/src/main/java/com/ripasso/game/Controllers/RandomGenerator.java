package com.ripasso.game.Controllers;

import java.util.Random;

public class RandomGenerator {

    public static int getRandomInt(int interval){
        Random tmp = new Random();
        return tmp.nextInt(interval);
    }

}
