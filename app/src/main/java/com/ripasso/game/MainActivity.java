package com.ripasso.game;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    GameView gameview;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity.java: ", "onCreate()");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameview = new GameView(this);


        setContentView(gameview);
    }

    public void onPause(){
        super.onPause();
        Log.d("MainActivity.java: ", "onPause()");
        gameview.StopView();    //Stop view when onPause() is called.
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(new GameView(this));
    }
}
