package com.ripasso.game.Controllers;

import android.app.Activity;

import com.ripasso.game.R;

public class ChuckNorrisQuoteGenerator {


    public static String generateQuote(Activity activity){

        String[] chuck_norris_array = activity.getResources().getStringArray(R.array.chuck_norris_quotes);

        return chuck_norris_array[RandomGenerator.getRandomInt(chuck_norris_array.length)];
    }

}
