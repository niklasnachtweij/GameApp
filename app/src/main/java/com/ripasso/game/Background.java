package com.ripasso.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/*Class responsible for creating and drawing a background picture.
* Mathias Berneland & Niklas Nachtweij
* */

public class Background {

    private Bitmap bmp;
    private int width;
    private int height;

    //Constructor
    public Background(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.bmp = bmp;
    }

    //Draw the background at GameView canvas.
    public void onDraw(Canvas canvas) {

        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(0, 0, width, height);
        if(canvas!=null)
            canvas.drawBitmap(bmp, src, dst, null);
    }
}