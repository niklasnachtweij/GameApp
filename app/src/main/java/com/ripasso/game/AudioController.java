package com.ripasso.game;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class AudioController {

    private Context ctx;
    MediaPlayer mp;

    AudioController(Context ctx){
        this.ctx = ctx;
    }

    public void makeSound(Sound indata) {

        switch(indata) {
            case MONSTER_DIE:
                mp = MediaPlayer.create(ctx, R.raw.crash_x);
                mp.start();
                break;
            default:
                break;
        }

    }
}


