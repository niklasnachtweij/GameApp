package com.ripasso.game;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private GameLoopThread gameLoopThread; //Current game loop

    private List<Villain> sprites = new ArrayList<Villain>(); //List of Villains
    private List<Blood> temps = new ArrayList<Blood>();
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();

    private AudioController audioController;
    private CollisionControl collision_control = new CollisionControl();

    private Bitmap bmpBlood;
    private Background background;
    private GameMenu gameMenu;
    private HighScore score;

    private Hero hero_object;
    private SuperVillain superVillain_object;

    private Vibrator vibrator;

    //TouchEvent variables
    private boolean isPressed;
    private float eventX = 0f;
    private float eventY = 0f;

    public GameView(Context context) {
        super(context);

        gameLoopThread = new GameLoopThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                Log.d("GameView.java: ", "SurfaceDestroyed");

                audioController.pauseBackgroundMusic();
                gameLoopThread.setRunning(false);

                boolean retry = true;
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {}
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d("GameView.java: ", "SurfaceCreated");
                createSprites();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
                audioController.startBackgroundMusic();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                Log.d("GameView.java: ", "SurfaceChanged");
            }
        });

        Initialize();

    }

    //Initializing variables.
    private void Initialize(){
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
        score = new HighScore();
        audioController = new AudioController(getContext());
        audioController.makeSound(Sound.BACKGROUND_MUSIC);
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        background = new Background(this, BitmapFactory.decodeResource(getResources(), R.drawable.vegtn_hms_srcbed));

        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;
        gameMenu = new GameMenu(this, 0, height-200, width, 200);
    }



    //Create and add Sprites to the Sprite arraylist.
    private void createSprites() {
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad3));
        sprites.add(createSprite(R.drawable.bad5));
        hero_object = new Hero(this, BitmapFactory.decodeResource(getResources(), R.drawable.good6));
        superVillain_object = new SuperVillain(this, BitmapFactory.decodeResource(getResources(), R.drawable.bad4));
        createObstacle(10); //Fills the list with x obstacles

}

    private Villain createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Villain(this, bmp);
    }

    //Creates an arraylist with obstacle objects, taken an number of have many
    //objects you want. Check collision so that objects doesnt get on top of eachother
    private void createObstacle(int numberObs) {
        this.obstacles = ObstacleFactory.createObstacle(this, numberObs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        background.onDraw(canvas);
        gameMenu.onDraw(canvas);

        //Drawing the temp Blood (Temporary Sprite)
        for (int i = temps.size() - 1; i >= 0; i--) {
            temps.get(i).onDraw(canvas);
        }

        //Drawing the Sprite objects
        for (Villain sprite : sprites) {
            sprite.onDraw(canvas);
        }

        superVillain_object.onDraw(canvas);
        hero_object.onDraw(canvas);

        for(Obstacle obstacle : obstacles) {
            obstacle.onDraw(canvas);
        }


        //Make the Hero walk in different directions.
        if (isPressed && hero_object.getX() < eventX &&
                eventY < hero_object.getY() + 60 &&
                eventY > hero_object.getY() - 60 &&
                (hero_object.getX() + hero_object.getWidth()) < (getWidth()-50)) {
            hero_object.move(Direction.EAST);

        } else if (isPressed && hero_object.getY() < eventY &&
                eventX < hero_object.getX() + 60 &&
                eventX > hero_object.getX() &&
                hero_object.getY() >= this.getY() &&
                hero_object.getY() + hero_object.getHeight() < this.getHeight()-200) {
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

            Villain sprite = sprites.get(i);

            if(collision_control.checkCollision(hero_object.getBounds(), sprite.getBounds())){
                audioController.makeSound(Sound.MONSTER_DIE);                                   //Plays soundeffect for dying monster
                vibrator.vibrate(35);                                                           //35ms vibration on impact
                temps.add(new Blood(temps, this, sprite.getX(), sprite.getY(), bmpBlood));      //Add blood
                sprites.remove(sprite);                                                         //Remove the bad guy when it's hit by hero
                sprites.add(createSprite(R.drawable.bad1));                                     //Add a new bad guy
                score.AddScore(1);                                                              //Increase score with 1

                if(score.getScore()%10 ==0) //When score increased with 10, add a badguy
                    sprites.add(createSprite(R.drawable.bad3));
                    superVillain_object.increaseSpeed();

                break;
            }
        }

        //Check collision between our Hero and SuperVillain
        if(collision_control.checkCollision(hero_object.getBounds(), superVillain_object.getBounds())) {
            audioController.makeSound(Sound.HERO_DIE);

            gameLoopThread.setRunning(false);
            Background bg = new Background(this, BitmapFactory.decodeResource(getResources(), R.drawable.space));
            bg.onDraw(canvas);
            audioController.pauseBackgroundMusic();
            audioController.makeSound(Sound.LAUGH);

            Paint paintText = new Paint();
            int height = this.getResources().getDisplayMetrics().heightPixels;
            paintText.setColor(Color.WHITE);
            paintText.setTextSize(100);
            paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Your score: " + score.getScore(), 100, height/2, paintText);

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
        return true;
    }

    //Sharing is caring <3, so other classes can see current score for printing purposes
    public HighScore getHighscore() {
        return score;
    }
}