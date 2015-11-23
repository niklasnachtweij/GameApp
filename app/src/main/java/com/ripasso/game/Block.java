package com.ripasso.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class Block extends GameObject{

    private int width;
    private int height;
    GameView gameView;
    HighScore score;

    public Block(GameView gameView, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gameView = gameView;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10);
        canvas.drawRect(getBounds(), paint);

        Paint paintText = new Paint();
        score = new HighScore();
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(50);
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Score: " + score.getScore(), 20, 600, paintText);



    }

    @Override
    public Rect getBounds() {
        return new Rect((int) x, (int) y, (int)x+this.width, (int)y+this.height);
    }
}
