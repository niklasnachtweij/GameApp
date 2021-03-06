package com.ripasso.game.GameFigures;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.ripasso.game.GameViews.GameView_Level1;

import java.util.Random;

/*Super villain class also called "Boss". Not 'The Boss', it's Bruce Springsteen but just a boss in the game.
* It also uses free animations from Famitsu (http://www.famitsu.com/freegame/tool/chibi/index2.html).
* Mathias Berneland & Niklas Nachtweij
* */

public class SuperVillain extends GameObject {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};
    private static final int BMP_ROWS = 4; //How many rows it's in the bitmap.
    private static final int BMP_COLUMNS = 3; //How many columns it's in the bitmap.
    private int max_speed = 5;
    private GameView_Level1 gameViewLevel1;
    private Bitmap bmp;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame = 0;
    private int width;
    private int height;

    //Constructor
    public SuperVillain(GameView_Level1 gameViewLevel1, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameViewLevel1 = gameViewLevel1;
        this.bmp = bmp;

        //Create the SuperVillain at random position at canvas.
        Random rnd = new Random();
        x = rnd.nextInt(gameViewLevel1.getWidth() - width);
        y = rnd.nextInt(gameViewLevel1.getHeight() - 50 - height);
        xSpeed = max_speed;
        ySpeed = max_speed;
    }

    //Choose right picture depending on direction and reversing X and Y speed if canvasborder is hit. Increase Xspeed and Yspeed.
    protected void update() {

        if (x >= gameViewLevel1.getWidth() - width - xSpeed || x + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }

        x = x + xSpeed;

        if (y >= gameViewLevel1.getHeight() - 50 - height - ySpeed || y + ySpeed <= 0) {
            ySpeed = -ySpeed;
        }

        y = y + ySpeed;

        currentFrame = ++currentFrame % BMP_COLUMNS; //Move the animation frame.
    }


    //Draw the object to the canvas.
    public void onDraw(Canvas canvas) {

        update();

        int srcX = currentFrame * width; //Get the right column of the row.
        int srcY = getAnimationRow() * height; //Get the right row in the bitmap.
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height); //Make the source rectangle in the bitmap.
        Rect dst = new Rect((int) x, (int) y, (int) x + width, (int) y + height); //Make the destination rectangle.

        if (canvas != null)
            canvas.drawBitmap(bmp, src, dst, null);
    }

    //Get direction of the animation.
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2); //Get direction.
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    //Get bounds of this object as a Rect.
    public Rect getBounds() {
        return new Rect((int) x, (int) y, (int) x + this.width, (int) y + this.height);
    }

    //Increase the animations speed on the canvas.
    public void increaseSpeed() {
        this.max_speed =+ 5;
    }

    public void reverseX_Speed(){
        xSpeed = -xSpeed;
    }

    public void reverseY_Speed(){
        ySpeed = -ySpeed;
    }
}