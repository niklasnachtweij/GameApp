package com.ripasso.game;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

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

    private GameView gameView;
    private Bitmap bmp;

    private int xSpeed;
    private int ySpeed;
    private int width;
    private int height;

    private int currentFrame = 0;

    //Create the Villain at random position at canvas.
    public Villain(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        x = rnd.nextInt(gameView.getWidth() - width);
        y = rnd.nextInt(gameView.getHeight()-200 - height); //200 because GameMenu is taking up 200 pixels of the canvas.
        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
    }

    //Choose right picture depending on direction and reversing X and Y speed if canvasborder is hit. Increase Xspeed and Yspeed.
    protected void update() {

        if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }

        x = x + xSpeed;

        if (y >= gameView.getHeight()-200 - height - ySpeed || y + ySpeed <= 0) {
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
}