package com.ripasso.game.GameThreads;

import android.graphics.Canvas;

import com.ripasso.game.GameViews.GameView_Level1;

/*The main loop Thread class responsible for updating the GameView.
* Mathias Berneland & Niklas Nachtweij
* */

public class GameLoopThread extends Thread {

    static final long FPS = 10; //Frames per second.
    private GameView_Level1 view;
    private boolean running = false;

    //Constructor
    public GameLoopThread(GameView_Level1 view) {
        this.view = view;
    }

    //Set Thread running or stop
    public void setRunning(boolean run) {
        running = run;
    }

    //Run method responsible for updating the the on draw at <FPS> times per second.
    @Override
    public void run() {
        long ticksPS = 1000 / FPS; //Ticks per second.
        long startTime;
        long sleepTime;

        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis(); //Get current time

            view.update();

            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }


            //Set sleepTime to the amount of ticks per second minus current time minus the start time.
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime); //If there is time left then sleep that time.
                else
                    sleep(10); //If not, sleep 10 millisecond anyway.
            } catch (Exception e) {}
        }
    }

}