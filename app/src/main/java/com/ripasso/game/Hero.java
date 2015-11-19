package com.ripasso.game;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Hero extends GameObject {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private static final int MAX_SPEED = 5;
    private GameView gameView;
    private Bitmap bmp;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame = 0;
    private int width;
    private int height;
    //Directions
    protected final int NORTH = 1;
    protected final int SOUTH = 2;
    protected final int WEST = 3;
    protected final int EAST = 4;

    public Hero(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        x = (gameView.getWidth() - width)/2 ;
        y = (gameView.getHeight() - height)/2;
        xSpeed = 0;
        ySpeed = 0;
    }

    @Override
    protected void update() {
        if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    @Override
    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect((int) x, (int) y, (int)x + width, (int)y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public boolean isCollision(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }

    public void move(int direction){
        if(direction>0 && direction <5){
            switch(direction){
                case 1:
                    y= y -(float) 5;
                    break;
                case 2:
                    y= y + (float) 5;
                    break;
                case 3:
                    x= x - (float) 5;
                    break;
                case 4:
                    x= x + (float) 5;
                    break;
                default:
                    break;
            }
        }
    }
}