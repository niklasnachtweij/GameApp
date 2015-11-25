package com.ripasso.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/*Class responsible for create and draw Obstacle object to the a canvas
* Mathias Berneland & Niklas Nachtweij
* */

public class Obstacle extends GameObject {

    private GameView gameView;
    private Bitmap bmp;
    private int width;
    private int height;

    //Constructor
    public Obstacle(GameView gameview, Bitmap bmp) {

        this.gameView = gameview;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();

        //Place new Obstacle at random position on canvas.
        Random rand = new Random();
        x = rand.nextInt(gameView.getWidth() - width);
        y = rand.nextInt(gameView.getHeight() - 200 - height); //200 because because the GameMenu is taking up some space in GameView.
    }

    //Draw method for drawing this object to canvas.
    @Override
    public void onDraw(Canvas canvas) {

        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect((int) x, (int) y, (int)x + width, (int)y + height);

        if(canvas!=null)
            canvas.drawBitmap(bmp, src, dst, null);

    }

    //Get this objects bounds as a Rect.
    @Override
    public Rect getBounds(){
        return new Rect((int) x, (int) y, (int)x+this.width, (int)y+this.height);
    }
}

