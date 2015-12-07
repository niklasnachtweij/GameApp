package com.ripasso.game.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.ripasso.game.R;

/*Class for new game activities.
* Mathias Berneland & Niklas Nachtweij.
* */

public class NewGameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.newgame_layout);

        //Get rid of the top banner.
        this.findViewById(android.R.id.content).getRootView().setSystemUiVisibility(
                this.findViewById(android.R.id.content).getRootView().SYSTEM_UI_FLAG_IMMERSIVE |
                        this.findViewById(android.R.id.content).getRootView().SYSTEM_UI_FLAG_FULLSCREEN);

        Log.d("NewGameActivity.java: ", "onCreate()");
    }


    public void onPause(){
        super.onPause();
        Log.d("NewGameActivity.java: ", "onPause()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("NewGameActivity.java: ", "onResume()");
    }

    //Start HowToPlay activity
    public void startHowToPlayView(View v){
        Intent intent = new Intent(NewGameActivity.this, HowToPlayActivity.class);
        startActivity(intent);
    }

    //Start new game activity
    public void startNewGame(View v){
        Intent intent = new Intent(NewGameActivity.this, Activity_Level1.class);
        startActivity(intent);
    }

    //Override onBackPressed to disable back button.
    @Override
    public void onBackPressed() {}


}
