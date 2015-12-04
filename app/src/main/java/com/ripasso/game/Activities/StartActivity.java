package com.ripasso.game.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.ripasso.game.R;

/*First activity in the game containing a layout with logotype.
* Mathias Berneland & Niklas Nachtweij
* */

public class StartActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start_layout);

        Log.d("StartActivity.java: ", "onCreate()");

        //Hold for a moment before start NewGameActivity.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, NewGameActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

}
