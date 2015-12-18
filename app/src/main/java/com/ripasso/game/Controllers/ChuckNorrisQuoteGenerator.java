package com.ripasso.game.Controllers;

import android.app.Activity;

import com.ripasso.game.R;

/*
* This class is responsible for generating random strings from a string array source.
* Mathias Berneland & Niklas Nachtweij
* */

public class ChuckNorrisQuoteGenerator {

    //Generate a random Chuck Norris quote
    public static String generateQuote(Activity activity){

        String[] chuck_norris_array = activity.getResources().getStringArray(R.array.chuck_norris_quotes);

        //Use randomgenerator to generate a random string.
        return chuck_norris_array[RandomGenerator.getRandomInt(chuck_norris_array.length)];
    }

}
