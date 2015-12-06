package com.ripasso.game.GameFigures;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.ripasso.game.GameViews.GameView_Level1;

/*GameObject class for drawing Villains to a canvas.
* Mathias Berneland & Niklas Nachtweij
* */

public class Villain extends GameObject {

    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };

    private static final int BMP_ROWS = 4; //Rows of the bitmap.
    private static final int BMP_COLUMNS = 3; //Columns of the bitmap.
    private static final int MAX_SPEED = 5;

    private GameView_Level1 gameViewLevel1;
    private Bitmap bmp;

    private int xSpeed;
    private int ySpeed;
    private int width;
    private int height;

    private int currentFrame = 0;

    //Create the Villain at random position at canvas.
    public Villain(GameView_Level1 gameViewLevel1, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameViewLevel1 = gameViewLevel1;
        this.bmp = bmp;

        //Set the Health of the Villain
        this.setHealth(20);

        Random rnd = new Random();
        x = rnd.nextInt(gameViewLevel1.getWidth() - width);
        y = rnd.nextInt(gameViewLevel1.getHeight()-200 - height); //200 because GameMenu is taking up 200 pixels of the canvas.
        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
    }



    //Choose right picture depending on direction and reversing X and Y speed if canvasborder is hit. Increase Xspeed and Yspeed.
    protected void update() {

        if (x >= gameViewLevel1.getWidth() - width - xSpeed || x + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }

        x = x + xSpeed;

        if (y >= gameViewLevel1.getHeight()-200 - height - ySpeed || y + ySpeed <= 0) {
            ySpeed = -ySpeed;
        }

        y = y + ySpeed;

        currentFrame = ++currentFrame % BMP_COLUMNS; //Move the animation frame.
    }

    //Draw the correct frame to the canvas.
    public void onDraw(Canvas canvas) {
        update();

        int srcX = currentFrame * width; //Get correct column.
        int srcY = getAnimationRow() * height; //Get correct row depending on direction.
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect((int) x, (int) y, (int)x + width, (int)y + height);

        if(canvas!=null)
            canvas.drawBitmap(bmp, src, dst, null);
    }

    //Get direction of the animation.
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    //This objects bounds as a Rect.
    public Rect getBounds(){
        return new Rect((int) x, (int) y, (int)x+this.width, (int)y+this.height);
    }

    public void reverseX_Speed(){
        xSpeed = -xSpeed;
    }

    public void reverseY_Speed(){
        ySpeed = -ySpeed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}