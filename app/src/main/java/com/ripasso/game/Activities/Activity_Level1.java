package com.ripasso.game.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.ripasso.game.GameViews.GameView_Level1;

/*Activity responsible for handling the Game activity.
* Mathias Berneland & Niklas Nachtweij.
* */

public class Activity_Level1 extends Activity {

    private GameView_Level1 gameview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Activity_Level1.java: ", "onCreate()");

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Set a new GameView as Activity content view.
        gameview = new GameView_Level1(this);
        setContentView(gameview);
    }

    public void onPause(){
        super.onPause();

        Log.d("Activity_Level1.java: ", "onPause()");

        //Stop view Thread when onPause() is called.
        gameview.StopView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Create a new GameView when resuming to the game.
        setContentView(new GameView_Level1(this));
    }

    //Override onBackPressed to disable back button.
    @Override
    public void onBackPressed() {}
}
