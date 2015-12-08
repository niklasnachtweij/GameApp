package com.ripasso.game.Controllers;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RemoteController;

import com.ripasso.game.Enums.Sound;
import com.ripasso.game.R;

import java.util.Random;

/*
* Class responsible for play, paus and stop different game sound.
* Mathias Berneland & Niklas Nachtweij
* */

public class AudioController {

    private Context ctx;
    private MediaPlayer mp;
    private MediaPlayer mp2;
    private MediaPlayer backgroundMusic;
    private int current_position;

    //Constructor
    public AudioController(Context ctx){
        this.ctx = ctx;
        this.mp = new MediaPlayer();
        this.mp2 = new MediaPlayer();
        this.backgroundMusic = new MediaPlayer();
    }

    //Making different sound depending on wich enum Sound is put in the method.
    public void makeSound(Sound indata) {

        /*if(mp.isPlaying()) {

            mp.stop();
            mp.release();

        }*/

        switch(indata) {

            case MONSTER_DIE:

                if(mp.isPlaying()) {

                    mp.stop();
                    mp.release();

                }

                Random rand = new Random();
                int randomNumber =  rand.nextInt(3); //Get random number to make different sound.

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

            case HERO_HIT:

                if(!mp2.isPlaying()) {

                    mp2.stop();
                    mp2.release();
                }

                mp2 = MediaPlayer.create(ctx, R.raw.horror_knife_stab_male_scream_001);
                mp2.start();
                break;

            case LAUGH:

                if(mp.isPlaying()) {

                    mp.stop();
                    mp.release();

                }

                mp = MediaPlayer.create(ctx, R.raw.demonic_laugh);
                mp.start();
                break;

            case HIT_PUNCH:

                if(!mp2.isPlaying()) {

                    mp2 = MediaPlayer.create(ctx, R.raw.solarplexis_hit);
                    mp2.start();


                }

                break;


            case BACKGROUND_MUSIC:

                if(backgroundMusic!=null) {

                    backgroundMusic = MediaPlayer.create(ctx, R.raw.zoombies);

                }
                break;

                default:
                    break;
        }
    }

    //Pause background music
    public void pauseBackgroundMusic() {

        current_position = backgroundMusic.getCurrentPosition();
        backgroundMusic.pause();
        backgroundMusic.release();

    }

    //Start background music
    public void startBackgroundMusic() {

        if(!backgroundMusic.isPlaying()) {
            backgroundMusic.seekTo(current_position);
            backgroundMusic.start();

        }
    }
}


