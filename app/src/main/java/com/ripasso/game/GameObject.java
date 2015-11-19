package com.ripasso.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject {

    protected float x;
    protected float y;
    protected Bitmap bmp;

    public abstract void onDraw(Canvas canvas);
    protected abstract void update();

}
