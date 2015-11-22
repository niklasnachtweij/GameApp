package com.ripasso.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

    GameView gameview;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameview = new GameView(this);
        setContentView(gameview);
    }

    public void onPause(){
        super.onPause();
        gameview.StopView();    //Stop view when onPause() is called.
    }
}
