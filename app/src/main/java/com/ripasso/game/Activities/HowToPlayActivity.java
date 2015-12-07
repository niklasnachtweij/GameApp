package com.ripasso.game.Activities;


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

        //Get rid of the top banner.
        this.findViewById(android.R.id.content).getRootView().setSystemUiVisibility(
                this.findViewById(android.R.id.content).getRootView().SYSTEM_UI_FLAG_IMMERSIVE |
                        this.findViewById(android.R.id.content).getRootView().SYSTEM_UI_FLAG_FULLSCREEN);

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
