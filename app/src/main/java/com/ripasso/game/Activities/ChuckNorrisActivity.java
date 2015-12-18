package com.ripasso.game.Activities;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ripasso.game.Controllers.ChuckNorrisQuoteGenerator;
import com.ripasso.game.R;

/*This activity is responsible for the Chuck Norris activity.
* Mathias Berneland & Niklas Nachtweij.
* */

public class ChuckNorrisActivity extends Activity {

    private TextView quote_text;
    private Button quote_btn;
    private Vibrator vibrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chuck_norris_layout);

        //Get rid of the top banner.
        this.findViewById(android.R.id.content).getRootView().setSystemUiVisibility(
                this.findViewById(android.R.id.content).getRootView().SYSTEM_UI_FLAG_IMMERSIVE |
                        this.findViewById(android.R.id.content).getRootView().SYSTEM_UI_FLAG_FULLSCREEN);

        //Initiate fields.
        this.vibrator = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        this.quote_text = (TextView) findViewById(R.id.quote_text);
        this.quote_btn = (Button) findViewById(R.id.generate_quote_btn);
        Log.d("ChuckNorris.java: ", "onCreate()");
    }


    public void onPause(){
        super.onPause();
        Log.d("ChuckNorris.java: ", "onPause()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ChuckNorris.java: ", "onResume()");
    }

    //When generating a quote, vibrate and change text at the TextView.
    public void generateQuote(View v){
        //Vibrate
        vibrator.vibrate(200);
        quote_text.setText(ChuckNorrisQuoteGenerator.generateQuote(this));
        quote_btn.setText("Hell yeah, you can't stop!");

    }

}
