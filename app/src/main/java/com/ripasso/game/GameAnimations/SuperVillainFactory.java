package com.ripasso.game.GameAnimations;


import android.graphics.BitmapFactory;

import com.ripasso.game.GameFigures.SuperVillain;
import com.ripasso.game.GameViews.GameView_Level1;
import com.ripasso.game.R;

import java.util.ArrayList;

/*
* This is a factory class for SuperVillain objects.
* Mathias Berneland & Niklas Nachtweij
* */

public class SuperVillainFactory {

    //Create and return an amount of SuperVillains.
    public static ArrayList<SuperVillain> createSuperVillains(GameView_Level1 gameview, int amount){

        ArrayList<SuperVillain> tmp_list = new ArrayList<SuperVillain>();

        for(int i=0; i<amount; i++)
            tmp_list.add(new SuperVillain(gameview, BitmapFactory.decodeResource(gameview.getResources(), R.drawable.bad4)));

        return tmp_list;
    }

}
