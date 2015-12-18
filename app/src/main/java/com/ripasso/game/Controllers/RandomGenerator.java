package com.ripasso.game.Controllers;

import java.util.Random;

/*
* This class is generating random numbers at a specific interval.
* Mathias Berneland & Niklas Nachtweij
* */

public class RandomGenerator {

    public static int getRandomInt(int interval){
        Random tmp = new Random();
        return tmp.nextInt(interval);
    }

}
