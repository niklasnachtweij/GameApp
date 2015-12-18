package com.ripasso.game.GameFigures;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.ripasso.game.Enums.Direction;
import com.ripasso.game.GameViews.GameView_Level1;

/*Hero object class responsible for drawing a hero to GameView canvas.
* Mathias Berneland & Niklas Nachtweij
* */

public class Hero extends GameObject {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private GameView_Level1 gameViewLevel1;
    private Bitmap bmp;
    private int xSpeed; //Speed in X direction.
    private int ySpeed; //Speed in Y direction.
    private int currentFrame = 0;
    private int width;
    private int height;


    //Constructor
    public Hero(GameView_Level1 gameViewLevel1, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameViewLevel1 = gameViewLevel1;
        this.bmp = bmp;

        //Set Hero's health.
        this.setHealth(70);

        Random rnd = new Random();
        x = (gameViewLevel1.getWidth() - width)/2 ;
        y = (gameViewLevel1.getHeight() - height)/2;
        xSpeed = 0;
        ySpeed = 0;
    }


    protected void update() {
        if (x >= gameViewLevel1.getWidth() - width - xSpeed || x + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y >= gameViewLevel1.getHeight() - height - ySpeed || y + ySpeed <= 0) {
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

        if(canvas!=null)
            canvas.drawBitmap(bmp, src, dst, null);
    }

    //Get the right row depending on direction.
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    //Get bounds as a Rect.
    @Override
    public Rect getBounds(){
        return new Rect((int) x, (int) y, (int)x+this.width, (int)y+this.height);
    }

    //Move hero in different direction.
    public void move(Direction direction, int speed){

        switch(direction){
            case NORTH:
                y= y -(float) speed;
                break;
            case SOUTH:
                y= y + (float) speed;
                break;
            case WEST:
                x= x - (float) speed;
                break;
            case EAST:
                x= x + (float) speed;
                break;
            default:
                break;
            }
    }


    //Get method for width of Hero object.
    public int getWidth() {
        return width;
    }


    //Get method for width of Hero object.
    public int getHeight() {
        return height;
    }
}