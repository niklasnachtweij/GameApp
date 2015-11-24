package com.ripasso.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Obstacle extends GameObject {

    private GameView gameView;
    private Bitmap bmp;
    private int width;
    private int height;

    public Obstacle(GameView gameview, Bitmap bmp) {

        this.gameView = gameview;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();

        Random rand = new Random();
        x = rand.nextInt(gameView.getWidth() - width);
        y = rand.nextInt(gameView.getHeight() - 200 - height);

    }


    @Override
    public void onDraw(Canvas canvas) {

        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect((int) x, (int) y, (int)x + width, (int)y + height);
        canvas.drawBitmap(bmp, src, dst, null);

    }

    @Override
    public Rect getBounds() {
        return null;
    }
}

