package com.ripasso.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/*Abstract class for GameObjects responsible for drawing to a canvas.
* Mathias Berneland & Niklas Nachtweij.
* */

public abstract class GameObject {

    protected float x; //X position.
    protected float y; //Y position.
    protected Bitmap bmp;

    //Abstract onDraw method for drawing to canvas.
    public abstract void onDraw(Canvas canvas);

    //Abstract method for returning the rectangle of this object.
    public abstract Rect getBounds();

    //Get X position.
    public float getX() {
        return x;
    }

    //Set X position.
    public void setX(float x) {
        this.x = x;
    }

    //Get Y position.
    public float getY() {
        return y;
    }

    //Set Y Position.
    public void setY(float y) {
        this.y = y;
    }

    //Set bitmap.
    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    //Get bitmap.
    public Bitmap getBmp() {
        return bmp;
    }

}
