package com.ripasso.game.GameAnimations;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.ripasso.game.GameFigures.GameObject;
import com.ripasso.game.GameFigures.Hero;
import com.ripasso.game.GameViews.GameView_Level1;
import com.ripasso.game.GameLogic.HighScore;

/*Class reponsible for drawing the GameMenu.
* Mathias Berneland & Niklas Nachtweij
* */

public class GameMenu extends GameObject {

    private int width; //Width of the menu.
    private int height; //Height of the menu.
    private GameView_Level1 gameViewLevel1;
    private HighScore score; //Current highscore.

    public GameMenu(GameView_Level1 gameViewLevel1, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gameViewLevel1 = gameViewLevel1;
    }

    //Draw method for drawing to canvas.
    @Override
    public void onDraw(Canvas canvas) {
        if(canvas!=null) {
            drawMenu(canvas);
            drawActionButton(canvas);
            drawHighScore(canvas);
            drawHerosHealth(canvas);
        }
    }

    //Draw highscore to canvas.
    private void drawHighScore(Canvas canvas){
        Paint paintText = new Paint();
        score = gameViewLevel1.getHighScore();
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(20);
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Score: " + score.getScore(), 200, 600, paintText);
    }


    private void drawHerosHealth(Canvas canvas){
        Paint paintText = new Paint();
        Hero tmp_hero = gameViewLevel1.getHeroObj();
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(20);
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Health: " + tmp_hero.getHealth(), 400, 600, paintText);
    }

    //Draw the action button to canvas.
    private void drawActionButton(Canvas canvas){
        Paint buttonPaint = new Paint();
        buttonPaint.setColor(Color.DKGRAY);
        buttonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        buttonPaint.setStrokeWidth(10);
        Rect rect = new Rect((int)x,(int)y, (int)x+150, (int)y+(this.height));
        canvas.drawRect(rect, buttonPaint);
    }

    //Draw the menu to canvas.
    private void drawMenu(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10);
        canvas.drawRect(getBounds(), paint);
    }

    //Get bounds as a Rect.
    @Override
    public Rect getBounds() {
        return new Rect((int) x, (int) y, (int)x+this.width, (int)y+this.height);
    }
}
