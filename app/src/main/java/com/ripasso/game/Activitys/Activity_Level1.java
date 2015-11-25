package com.ripasso.game.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.ripasso.game.GameViews.GameView_Level1;

/*Activity responsible for handling the Game activity. */

public class Activity_Level1 extends Activity {

    private GameView_Level1 gameview; //

    /** Called when the activity is first created. */
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

        //Stop view when onPause() is called.
        gameview.StopView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Create a new GameView when resuming to the game.
        setContentView(new GameView_Level1(this));
    }
}
