package com.ripasso.game;

import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/*Class that creates blood animation effects at the canvas.
* Mathias Berneland & Niklas Nachtweij
* */

public class Blood extends GameObject {

    private Bitmap bmp;
    private int life = 15;
    private List<Blood> temps;

    //Constructor
    public Blood(List<Blood> temps, GameView gameView, float x,
                 float y, Bitmap bmp) {
        this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0),
                gameView.getWidth() - bmp.getWidth());
        this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0),
                gameView.getHeight() - bmp.getHeight());
        this.bmp = bmp;
        this.temps = temps;
    }


    //Draw the animation to the GameView canvas.
    public void onDraw(Canvas canvas) {
        update();

        if(canvas!=null)
            canvas.drawBitmap(bmp, x, y, null);
    }

    //Decrease life with 1,
    protected void update() {
        if (--life < 1) {
            temps.remove(this);
        }
    }

    @Override
    public Rect getBounds(){
        return new Rect(0,0,0,0);
    }



}
