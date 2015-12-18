package com.ripasso.game.GameAnimations;

import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.ripasso.game.GameFigures.GameObject;
import com.ripasso.game.GameViews.GameView_Level1;

/*Class that creates blood animation effects at the canvas.
* Mathias Berneland & Niklas Nachtweij
* */

public class Blood extends GameObject {

    private Bitmap bmp;
    private int life = 15;
    private List<Blood> temps;

    //Constructor
    public Blood(List<Blood> temps, GameView_Level1 gameViewLevel1, float x,
                 float y, Bitmap bmp) {
        //Make the blood appear at right place at the screen.
        this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0),
                gameViewLevel1.getWidth() - bmp.getWidth());
        this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0),
                gameViewLevel1.getHeight() - bmp.getHeight());
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
