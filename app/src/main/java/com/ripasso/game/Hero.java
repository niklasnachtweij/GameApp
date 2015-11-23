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
    private static final int MAX_SPEED = 15;
    private GameView gameView;
    private Bitmap bmp;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame = 0;
    private int width;
    private int height;


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


    @Override
    public Rect getBounds(){
        return new Rect((int) x, (int) y, (int)x+this.width, (int)y+this.height);
    }

    public void move(Direction direction){

        switch(direction){
            case NORTH:
                y= y -(float) MAX_SPEED;
                break;
            case SOUTH:
                y= y + (float) MAX_SPEED;
                break;
            case WEST:
                x= x - (float) MAX_SPEED;
                break;
            case EAST:
                x= x + (float) MAX_SPEED;
                break;
            default:
                break;
            }
    }



    public int getWidth() {
        return width;
    }



    public int getHeight() {
        return height;
    }
}