package com.ripasso.game.GameViews;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ripasso.game.Controllers.AudioController;
import com.ripasso.game.Controllers.CollisionControl;
import com.ripasso.game.Enums.Direction;
import com.ripasso.game.Enums.Sound;
import com.ripasso.game.GameAnimations.Background;
import com.ripasso.game.GameAnimations.Blood;
import com.ripasso.game.GameAnimations.GameMenu;
import com.ripasso.game.GameAnimations.Obstacle;
import com.ripasso.game.GameAnimations.ObstacleFactory;
import com.ripasso.game.GameFigures.Hero;
import com.ripasso.game.GameFigures.SuperVillain;
import com.ripasso.game.GameFigures.Villain;
import com.ripasso.game.GameLogic.HighScore;
import com.ripasso.game.GameThreads.GameLoopThread;
import com.ripasso.game.R;

/*Main game view responsible drawing the game and handling user input events.
* Mathias Berneland & Niklas Nachtweij.
* */

public class GameView_Level1 extends SurfaceView {

    //Main game loop thread
    private GameLoopThread gameLoopThread;

    //Canvas game objects
    private List<Villain> villain_objects;
    private List<Blood> blood;
    private List<Obstacle> obstacles;
    private Hero hero_object;
    private SuperVillain superVillain_object;

    //Controllers
    private AudioController audioController;
    private CollisionControl collision_control;
    private Vibrator vibrator;

    //Drawables
    private Background background;
    private GameMenu gameMenu;
    private HighScore score;

    //TouchEvent variables
    private boolean isPressed;
    private float eventX = 0f;
    private float eventY = 0f;

    //Constructor
    public GameView_Level1(Context context) {
        super(context);

        gameLoopThread = new GameLoopThread(this);

        //Get callbacks
        getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                Log.d("GameView.java: ", "SurfaceDestroyed");

                //audioController.pauseBackgroundMusic(); THIS LINE IS CAUSING THE APP TO CRASH. BUGG!

                gameLoopThread.setRunning(false);

                //Stop Thread.
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

                initializeGameObjects();

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
        score = new HighScore();
        audioController = new AudioController(getContext());
        audioController.makeSound(Sound.BACKGROUND_MUSIC);
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        background = new Background(this, BitmapFactory.decodeResource(getResources(), R.drawable.vegtn_hms_srcbed));

        villain_objects = new ArrayList<Villain>();
        blood = new ArrayList<Blood>();
        obstacles = new ArrayList<Obstacle>();

        collision_control = new CollisionControl();

        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;
        gameMenu = new GameMenu(this, 0, height-200, width, 200);
    }



    //Create and initialize Game objects.
    private void initializeGameObjects() {
        villain_objects.add(createVillainObject(R.drawable.bad1));
        villain_objects.add(createVillainObject(R.drawable.bad3));
        villain_objects.add(createVillainObject(R.drawable.bad5));
        hero_object = new Hero(this, BitmapFactory.decodeResource(getResources(), R.drawable.good6));
        superVillain_object = new SuperVillain(this, BitmapFactory.decodeResource(getResources(), R.drawable.bad4));

        //Fill the list with an amount obstacles
        createObstacle(10);

}

    private Villain createVillainObject(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Villain(this, bmp);
    }

    //Creates an arraylist with obstacle objects, taken an number of have many
    //objects you want. Check collision so that objects doesnt get on top of eachother
    private void createObstacle(int numberObs) {
        this.obstacles = ObstacleFactory.createObstacle(this, numberObs);
    }

    //Draw objects to the canvas.
    @Override
    public void onDraw(Canvas canvas) {

        //Draw the background
        background.onDraw(canvas);

        //Draw the game menu
        gameMenu.onDraw(canvas);

        //Draw the blood.
        for (int i = blood.size() - 1; i >= 0; i--) {
            blood.get(i).onDraw(canvas);
        }

        //Draw villains.
        for (Villain sprite : villain_objects) {
            sprite.onDraw(canvas);
        }

        //Draw SuperVillain.
        superVillain_object.onDraw(canvas);

        //Draw Hero.
        hero_object.onDraw(canvas);

        //Draw obstacles.
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

        //Check for collisions
        for (int i = villain_objects.size() - 1; i >= 0; i--) {

            Villain villain = villain_objects.get(i);

            if(collision_control.checkCollision(hero_object.getBounds(), villain.getBounds())){

                //Plays sound effect for dying monster
                audioController.makeSound(Sound.MONSTER_DIE);

                //35ms vibration on impact
                vibrator.vibrate(35);

                //Add blood
                blood.add(new Blood(blood, this, villain.getX(), villain.getY(), BitmapFactory.decodeResource(getResources(), R.drawable.blood1)));

                //Remove the bad guy when it's hit by hero
                villain_objects.remove(villain);

                //Add a new bad guy
                villain_objects.add(createVillainObject(R.drawable.bad1));

                //Increase score with 1
                score.AddScore(1);

                //When score increased with 10, add a badguy and increase SuperVillains speed.
                if(score.getScore()%10 ==0)
                    villain_objects.add(createVillainObject(R.drawable.bad3));
                    superVillain_object.increaseSpeed();

                break;
            }
        }

        //Check collision between our Hero and SuperVillain
        if(collision_control.checkCollision(hero_object.getBounds(), superVillain_object.getBounds())) {

            audioController.makeSound(Sound.HERO_DIE);
            gameLoopThread.setRunning(false);

            //Draw a new background.
            Background bg = new Background(this, BitmapFactory.decodeResource(getResources(), R.drawable.space));
            bg.onDraw(canvas);

            //Stop music.
            audioController.pauseBackgroundMusic();
            audioController.makeSound(Sound.LAUGH);

            //Paint text with the highscore.
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