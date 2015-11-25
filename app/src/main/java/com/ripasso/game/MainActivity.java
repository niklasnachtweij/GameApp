package com.ripasso.game;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/*Activity responsible for handling the Game activity. */

public class MainActivity extends Activity {

    private GameView gameview; //

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity.java: ", "onCreate()");

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Set a new GameView as Activity content view.
        gameview = new GameView(this);
        setContentView(gameview);
    }

    public void onPause(){
        super.onPause();

        Log.d("MainActivity.java: ", "onPause()");
        //Stop view when onPause() is called.
        gameview.StopView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Create a new GameView when resuming to the game.
        setContentView(new GameView(this));
    }
}
