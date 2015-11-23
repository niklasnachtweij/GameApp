package com.ripasso.game;

import android.graphics.Canvas;

public class MoveHeroThread extends Thread {
    static final long FPS = 10;
    private GameView view;
    private boolean running = false;
    private Direction direction;
    private Hero hero;

    public MoveHeroThread(GameView view, Hero hero) {
        this.view = view;
        this.hero = hero;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {

            startTime = System.currentTimeMillis();
            hero.move(direction);

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }
}