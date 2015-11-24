package com.ripasso.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background {

    private Bitmap bmp;
    private int width;
    private int height;

    public Background(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.bmp = bmp;
    }
    public void onDraw(Canvas canvas) {

        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(0, 0, width, height);
        canvas.drawBitmap(bmp, src, dst, null);
    }
}