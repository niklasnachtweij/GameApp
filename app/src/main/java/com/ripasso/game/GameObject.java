package com.ripasso.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject {
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    protected float x;
    protected float y;
    protected Bitmap bmp;

    public abstract void onDraw(Canvas canvas);
    protected abstract void update();

}
