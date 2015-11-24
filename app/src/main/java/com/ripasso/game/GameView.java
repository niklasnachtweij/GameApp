package com.ripasso.game;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private GameLoopThread gameLoopThread;
    private List<SpriteObj> sprites = new ArrayList<SpriteObj>();
    private List<TSprite> temps = new ArrayList<TSprite>();
    private CollisionControl collision_controll = new CollisionControl();
    private long lastClick;
    private Bitmap bmpBlood;
    private HighScore score;
    private Hero hero_object;
    private AudioController audioController;
    private GameMenu gameMenu;
    private Vibrator vibrator;
    private Context mainContext;

    private boolean isPressed;
    private float eventX = 0f;
    private float eventY = 0f;

    public GameView(Context context) {
        super(context);
        this.mainContext = context;
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
        audioController = new AudioController(getContext());

        vibrator = (Vibrator) mainContext.getSystemService(Context.VIBRATOR_SERVICE);

        audioController.makeSound(Sound.BACKGROUND_MUSIC);          //Starts playing the background music

        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;
        gameMenu = new GameMenu(this, 0, height-200, width, 200); //Create a GameMenu

    }



    //Create and add Sprites to the Sprite arraylist.
    private void createSprites() {
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad2));
        sprites.add(createSprite(R.drawable.bad3));
        hero_object = new Hero(this, BitmapFactory.decodeResource(getResources(), R.drawable.good6));
    }

    private SpriteObj createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new SpriteObj(this, bmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.rgb(00, 44, 66));
        /*  Was trying to paint tiles to a complete background, didn't work...
            My guess it must be done to canvas, but DrawBitMap can't take a
            BitMapDrawable as an argument

        BitmapDrawable TileMe = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.sprite_sheet));
        TileMe.setTileModeX(Shader.TileMode.REPEAT);
        TileMe.setTileModeY(Shader.TileMode.REPEAT);
        canvas.DrawBitmap(TileMe, 0, 0, null);
        */

        gameMenu.onDraw(canvas);

        //Drawing the temp TSprite (Temporary Sprite)
        for (int i = temps.size() - 1; i >= 0; i--) {
            temps.get(i).onDraw(canvas);
        }

        //Drawing the Sprite objects
        for (SpriteObj sprite : sprites) {
            sprite.onDraw(canvas);
        }

        hero_object.onDraw(canvas);


        //Make the Hero walk in different directions.
        if (isPressed && hero_object.getX() < eventX &&
                eventY < hero_object.getY() + 60 &&
                eventY > hero_object.getY() - 60 &&
                (hero_object.getX() + hero_object.getWidth()) < (getWidth()-50)) {
            hero_object.move(Direction.EAST);

        } else if (isPressed && hero_object.getY() < eventY &&
                eventX < hero_object.getX() + 60 &&
                eventX > hero_object.getX() &&
                hero_object.getY() >= this.getY()) {
            hero_object.move(Direction.SOUTH);

        } else if (isPressed && hero_object.getX() > eventX &&
                eventY < hero_object.getY() + 60 &&
                eventY > hero_object.getY() - 60 &&
                hero_object.getX() >= 0) {
            hero_object.move(Direction.WEST);

        } else if (isPressed && hero_object.getY() > eventY &&
                eventX < hero_object.getX() + 60 &&
                eventX > hero_object.getX() - 60){
            hero_object.move(Direction.NORTH);
        }





        for (int i = sprites.size() - 1; i >= 0; i--) {

            SpriteObj sprite = sprites.get(i);

            if(collision_controll.checkCollision(hero_object.getBounds(), sprite.getBounds())){
                audioController.makeSound(Sound.MONSTER_DIE);                                   //Plays soundeffect for dying monster
                vibrator.vibrate(35);                                                           //35ms vibration on impact
                temps.add(new TSprite(temps, this, sprite.getX(), sprite.getY(), bmpBlood));    //Add blood
                sprites.remove(sprite);                                                         //Remove the bad guy when it's hit by hero
                sprites.add(createSprite(R.drawable.bad1));                                     //Add a new bad guy
                score.AddScore(1);                                                              //Increase score with 1

                if(score.getScore()%10 ==0) //When score increased with 10, add a badguy
                    sprites.add(createSprite(R.drawable.bad3));

                break;
            }
        }


    }

    //Stop the view in the Thread is not null.
    public void StopView() {
        if (gameLoopThread != null) {
            gameLoopThread.setRunning(false);
        }
    }


    //Listen for touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {

            eventX = event.getX();
            eventY = event.getY();
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                isPressed = true;
            } else if(event.getAction()==MotionEvent.ACTION_UP){
                isPressed = false;
            }


        /*
        if (System.currentTimeMillis() - lastClick > 300) {
            lastClick = System.currentTimeMillis();
            float x = event.getX();
            float y = event.getY();
            synchronized (getHolder()) {

                for (int i = sprites.size() - 1; i >= 0; i--) {
                    SpriteObj sprite = sprites.get(i);

                    if(collision_controll.checkCollision(hero_object.getBounds(), sprite.getBounds())){
                        temps.add(new TSprite(temps, this, hero_object.getX(), hero_object.getY(), bmpBlood));    //Add blood
                        sprites.remove(sprite);                                 //Remove the bad guy when it's hit by hero
                        sprites.add(createSprite(R.drawable.bad1));             //Add a new bad guy
                        score.AddScore(1);                                      //Increase score with 1
                        break;
                    }
                }
            }
        }
        */
        return true;
    }

    //Sharing is caring, so other classes can see current score for printing purposes
    public HighScore getHighscore() {

        return score;

    }

}