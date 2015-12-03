package com.ripasso.game.Activitys;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.ripasso.game.R;

/*Activity class for How to play view.
* Mathias Berneland & Niklas Nachtweij.
* */

public class HowToPlayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.howtoplay_layout);

        Log.d("HowToPlayActivity: ", "onCreate()");
    }


    public void onPause(){
        super.onPause();
        Log.d("HowToPlayActivity: ", "onPause()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("HowToPlayActivity: ", "onResume()");
    }

}
