package com.ripasso.game.GameAnimations;

import android.graphics.Bitmap;

import com.ripasso.game.GameViews.GameView_Level1;

/*
* This is a factory class for LifeMushroom objects.
* Mathias Berneland & Niklas Nachtweij
* */

public class LifeMushroomFactory {

    //Create and return a LifeMushroom object.
    public static LifeMushroom createLifeMushroom(GameView_Level1 gameview, Bitmap bmp){

        return new LifeMushroom(gameview, bmp);
    }

}
