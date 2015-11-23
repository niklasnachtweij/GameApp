package com.ripasso.game;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.util.Random;

public class AudioController {

    private Context ctx;
    MediaPlayer mp;
    MediaPlayer backgroundMusic;

    AudioController(Context ctx){
        this.ctx = ctx;
    }

    public void makeSound(Sound indata) {

        switch(indata) {
            case MONSTER_DIE:
                Random rand = new Random();
                int randomNumber =  rand.nextInt(3);

                if(randomNumber == 0) {
                    mp = MediaPlayer.create(ctx, R.raw.body_impact_1_with_grunt_);
                }

                else if(randomNumber == 1) {
                    mp = MediaPlayer.create(ctx, R.raw.body_impact_2_with_grunt_);
                }

                else  {
                    mp = MediaPlayer.create(ctx, R.raw.body_impact_3_with_grunt_);
                }

                mp.start();

                break;

            case BACKGROUND_MUSIC:
                backgroundMusic = MediaPlayer.create(ctx, R.raw.zoombies);
                backgroundMusic.setLooping(true);
                backgroundMusic.start();

                break;

                default:
                    break;
        }
    }
}


