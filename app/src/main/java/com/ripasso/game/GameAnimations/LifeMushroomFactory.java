package com.ripasso.game.GameAnimations;

import android.graphics.Bitmap;

import com.ripasso.game.GameViews.GameView_Level1;

public class LifeMushroomFactory {

    public static LifeMushroom createLifeMushroom(GameView_Level1 gameview, Bitmap bmp){

        return new LifeMushroom(gameview, bmp);
    }

}
