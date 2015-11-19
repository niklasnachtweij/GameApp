package com.ripasso.game;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private GameLoopThread gameLoopThread;
    private List<SpriteObj> sprites = new ArrayList<SpriteObj>();
    private List<TSprite> temps = new ArrayList<TSprite>();
    private long lastClick;
    private Bitmap bmpBlood;
    private HighScore score;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {}
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createSprites();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
        score = new HighScore();
    }

    //Create and add Sprites to the Sprite arraylist.
    private void createSprites() {
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad2));
        sprites.add(createSprite(R.drawable.bad3));
        sprites.add(createSprite(R.drawable.bad4));
        sprites.add(createSprite(R.drawable.bad5));
        sprites.add(createSprite(R.drawable.bad6));
        sprites.add(createSprite(R.drawable.good1));
        sprites.add(createSprite(R.drawable.good2));
        sprites.add(createSprite(R.drawable.good3));
        sprites.add(createSprite(R.drawable.good4));
        sprites.add(createSprite(R.drawable.good5));
        sprites.add(createSprite(R.drawable.good6));
    }

    private SpriteObj createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new SpriteObj(this, bmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.BLACK);

        //Drawing the background scaled
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.sky_back_layer);
        float scale = (float)background.getHeight()/(float)getHeight();
        int newWidth = Math.round(background.getWidth()/scale);
        int newHeight = Math.round(background.getHeight()/scale);
        Bitmap scaled = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);
        canvas.drawBitmap(scaled, 0, 0, null);

        //Draw highscore
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
        String score_string = "Score: " + Integer.toString(score.getScore());
        canvas.drawText(score_string,30,60, paint);

        //Drawing the temp TSprite (Temporary Sprite)
        for (int i = temps.size() - 1; i >= 0; i--) {
            temps.get(i).onDraw(canvas);
        }
        //Drawing the Sprite objects
        for (SpriteObj sprite : sprites) {
            sprite.onDraw(canvas);
        }
    }

    //Listen for touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 300) {
            lastClick = System.currentTimeMillis();
            float x = event.getX();
            float y = event.getY();
            synchronized (getHolder()) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    SpriteObj sprite = sprites.get(i);
                    if (sprite.isCollision(x, y)) {
                        sprites.remove(sprite);
                        temps.add(new TSprite(temps, this, x, y, bmpBlood));
                        score.AddScore(1);//Add a score
                        break;
                    }
                }
            }
        }
        return true;
    }
}