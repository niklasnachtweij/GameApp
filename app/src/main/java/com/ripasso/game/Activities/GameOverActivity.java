package com.ripasso.game.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ripasso.game.R;

/*Game over activity for handling game over view.
* Mathias Berneland & Niklas Nachtweij.
* */

public class GameOverActivity extends Activity {

    private TextView highscore_text;
    private Vibrator vibrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gameover_layout);

        this.vibrator = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);

        //Get rid of the top banner.
        this.findViewById(android.R.id.content).getRootView().setSystemUiVisibility(
                this.findViewById(android.R.id.content).getRootView().SYSTEM_UI_FLAG_IMMERSIVE |
                        this.findViewById(android.R.id.content).getRootView().SYSTEM_UI_FLAG_FULLSCREEN);

        Log.d("GameOverActivity.java: ", "onCreate()");

        //Initialize TextView variable.
        this.highscore_text = (TextView) findViewById(R.id.score_textview);

        //Get message from intent.
        Intent extras = getIntent();
        if(extras !=null)
        {
            //Set the highscore value.
            this.highscore_text.setText(extras.getStringExtra("score"));
        }
    }

    private void generateChuckNorrisQuote(){

    }

    public void onPause(){
        super.onPause();
        Log.d("GameOverActivity.java: ", "onPause()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("GameOverActivity.java: ", "onResume()");
    }

    //Override onBackPressed to disable back button.
    @Override
    public void onBackPressed() {}

    public void backToNewGameActivity(View v){
        vibrator.vibrate(200);
        Intent intent = new Intent(GameOverActivity.this, NewGameActivity.class);
        startActivity(intent);
    }

    //Start new game
    public void restartNewGame(View v){
        vibrator.vibrate(200);
        Intent intent = new Intent(GameOverActivity.this, Activity_Level1.class);
        startActivity(intent);
    }


}
