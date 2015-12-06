package com.ripasso.game.GameFigures;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/*Abstract class for GameObjects responsible for drawing to a canvas.
* Mathias Berneland & Niklas Nachtweij.
* */

public abstract class GameObject {

    protected float x; //X position.
    protected float y; //Y position.
    protected Bitmap bmp; // The bitmap of the object

    protected int health; // Object's health

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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    //Increase object's health.
    public void increaseHealth(int health){
        this.health += health;
    }

    //Decrease object's health.
    public void decreaseHealth(int health){
        this.health -= health;

        //If health is negative, set health to 0.
        if(this.health<0)
            this.health = 0;
    }

}
