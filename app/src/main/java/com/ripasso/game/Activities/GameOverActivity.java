package com.ripasso.game.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.ripasso.game.R;

/*Game over activity for handling game over view.
* Mathias Berneland & Niklas Nachtweij.
* */

public class GameOverActivity extends Activity {

    TextView highscore_text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gameover_layout);

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


    public void onPause(){
        super.onPause();
        Log.d("GameOverActivity.java: ", "onPause()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("GameOverActivity.java: ", "onResume()");
    }

}
