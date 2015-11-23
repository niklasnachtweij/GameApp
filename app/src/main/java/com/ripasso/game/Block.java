package com.ripasso.game;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Block extends GameObject{

    int width = 50;
    int height = 50;

    public Block(){

    }

    @Override
    public void onDraw(Canvas canvas) {
        
    }

    @Override
    public Rect getBounds() {
        return new Rect((int) x, (int) y, (int)x+this.width, (int)y+this.height);
    }
}
