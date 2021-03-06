package com.ripasso.game.GameViews;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ripasso.game.Activities.GameOverActivity;
import com.ripasso.game.Controllers.AudioController;
import com.ripasso.game.Controllers.CollisionControl;
import com.ripasso.game.Controllers.RandomGenerator;
import com.ripasso.game.Enums.Direction;
import com.ripasso.game.Enums.Sound;
import com.ripasso.game.GameAnimations.Background;
import com.ripasso.game.GameAnimations.Blood;
import com.ripasso.game.GameAnimations.GameMenu;
import com.ripasso.game.GameAnimations.LifeMushroom;
import com.ripasso.game.GameAnimations.LifeMushroomFactory;
import com.ripasso.game.GameAnimations.Obstacle;
import com.ripasso.game.GameAnimations.ObstacleFactory;
import com.ripasso.game.GameAnimations.SuperVillainFactory;
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
    private List<LifeMushroom> life_mushrooms;
    private List<SuperVillain> super_villains;

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

                Log.d("GameView_Level1.java: ", "SurfaceDestroyed");

                //Stop the background music
                audioController.stopBackgroundMusic();

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
                Log.d("GameView_Level1.java: ", "SurfaceCreated");

                initializeGameObjects();

                //Get rid of top panel.
                setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE | SYSTEM_UI_FLAG_FULLSCREEN);

                //Start the gameloop
                gameLoopThread.setRunning(true);
                gameLoopThread.start();

                //Start background music
                audioController.startBackgroundMusic();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                Log.d("GameView_Level1.java: ", "SurfaceChanged");
            }
        });
        //Initialize fields
        Initialize();

    }

    //Initializing variables.
    private void Initialize(){
        score = new HighScore();
        audioController = new AudioController(getContext());
        audioController.makeSound(Sound.BACKGROUND_MUSIC);
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        background = new Background(this, BitmapFactory.decodeResource(getResources(), R.drawable.lavabg));

        villain_objects = new ArrayList<Villain>();
        blood = new ArrayList<Blood>();
        obstacles = new ArrayList<Obstacle>();
        life_mushrooms = new ArrayList<LifeMushroom>();
        super_villains = new ArrayList<SuperVillain>();

        collision_control = new CollisionControl();

        //Get height and width for the canvas.
        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;

        gameMenu = new GameMenu(this, 0, height-50, width, 50);
    }



    //Create and initialize Game objects.
    private void initializeGameObjects() {
        villain_objects.add(createVillainObject(R.drawable.bad1));
        villain_objects.add(createVillainObject(R.drawable.bad3));
        villain_objects.add(createVillainObject(R.drawable.bad5));
        hero_object = new Hero(this, BitmapFactory.decodeResource(getResources(), R.drawable.good6));

        //Create SuperVillains
        createSuperVillains(2);

        //Fill the list with an amount obstacles
        createObstacle(10);
    }

    //Create Villain objects
    private Villain createVillainObject(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Villain(this, bmp);
    }

    //Create SuperVillains
    private void createSuperVillains(int amount){
        this.super_villains = SuperVillainFactory.createSuperVillains(this, amount);
    }

    //Creates an arraylist with obstacle objects, taken an number of have many
    //objects you want. Check collision so that objects doesnt get on top of eachother.
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

        //Draw obstacles.
        for(Obstacle obstacle : obstacles) {
            obstacle.onDraw(canvas);
        }

        //Draw LifeMushroom objects.
        for(LifeMushroom tmp : life_mushrooms){
            tmp.onDraw(canvas);
        }

        //Draw the blood.
        for (int i = blood.size() - 1; i >= 0; i--) {
            blood.get(i).onDraw(canvas);
        }

        //Draw villains
        for (Villain sprite : villain_objects) {
            sprite.onDraw(canvas);
        }

        //Draw Hero.
        hero_object.onDraw(canvas);

        //Draw SuperVillains.
        for (SuperVillain tmp : super_villains)
            tmp.onDraw(canvas);
    }

    //Check collision between hero and villains.
    private void checkCollision_Hero_Villain(){

        //Check for collisions
        for (int i = villain_objects.size() - 1; i >= 0; i--) {

            Villain villain = villain_objects.get(i);

            if(collision_control.checkCollision(hero_object.getBounds(), villain.getBounds())){

                villain.decreaseHealth(1);
                audioController.makeSound(Sound.HIT_PUNCH);
                heroBounce();

                if(villain.getHealth() == 0) {
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

                }
            break;
            }
        }
    }

    //Make the Hero walk in different directions.
    private void moveHero(){
        if (isPressed && hero_object.getX() < eventX &&
                eventY < hero_object.getY() + 60 &&
                eventY > hero_object.getY() - 60 &&
                (hero_object.getX() + hero_object.getWidth()) < (getWidth()-50)) {
            hero_object.move(Direction.EAST, 30);

        } else if (isPressed && hero_object.getY() < eventY &&
                eventX < hero_object.getX() + 60 &&
                eventX > hero_object.getX() &&
                hero_object.getY() >= this.getY() &&
                hero_object.getY() + hero_object.getHeight() < this.getHeight()-hero_object.getHeight()) {
            hero_object.move(Direction.SOUTH,30);

        } else if (isPressed && hero_object.getX() > eventX &&
                eventY < hero_object.getY() + 60 &&
                eventY > hero_object.getY() - 60 &&
                hero_object.getX() >= 0) {
            hero_object.move(Direction.WEST, 30);

        } else if (isPressed && hero_object.getY() > eventY &&
                eventX < hero_object.getX() + 60 &&
                eventX > hero_object.getX() - 60){
            hero_object.move(Direction.NORTH, 30);
        }
    }

    //Make the Hero bounce if he's hiting something.
    private void heroBounce(){
        if(hero_object.getX() + hero_object.getWidth() + 50 < this.getWidth() &&
                hero_object.getX() > 50 &&
                hero_object.getY()+hero_object.getHeight()+50 < this.getHeight() &&
                hero_object.getY() > 50) {

                hero_object.move(Direction.values()[RandomGenerator.getRandomInt(3)], 50);
        }
    }


    //Check collision between Hero object and SuperVillain object.
    private void checkCollision_Hero_SuperVillain(){

        for(SuperVillain tmp : super_villains) {
            //Check collision between our Hero and SuperVillain
            if (collision_control.checkCollision(hero_object.getBounds(), tmp.getBounds())) {

                //Decrease Hero's health when impact.
                hero_object.decreaseHealth(5);
                audioController.makeSound(Sound.HIT_PUNCH);

                //Play sound hit by Super Villain
                audioController.makeSound(Sound.HERO_HIT);

                //If health is 0, kill Hero.
                if (hero_object.getHealth() == 0) {
                    audioController.makeSound(Sound.LAUGH);
                    //Game over
                    callGameOver();
                }
            }
        }
    }

    //Method for Game Over.
    public void callGameOver(){
        Intent intent = new Intent(getContext(), GameOverActivity.class);
        intent.putExtra("score", Integer.toString(score.getScore()));
        //Stop looping thread.
        gameLoopThread.setRunning(false);
        //Start a new activity.
        getContext().startActivity(intent);
    }

    //Update, is called from GameLoop thread.
    public void update(){

        //Move the Hero in different directions.
        moveHero();

        //Check for collisions between Hero and regular Villain
        checkCollision_Hero_Villain();

        //Create a random mushroom
        addRandomLifeMushroom();

        //Check collision between life mushroom and hero.
        checkCollision_Hero_LifeMushroom();

        //Check for collision between Hero and Supervillain
        checkCollision_Hero_SuperVillain();

        //Check for collision between Hero and Obstacle
        checkCollision_Obstacle_Hero();
    }

    //Check for collision between LifeMushroom and Hero.
    private void checkCollision_Hero_LifeMushroom(){

        for(LifeMushroom tmp : life_mushrooms) {

            if (collision_control.checkCollision(tmp.getBounds(), hero_object.getBounds())) {
                //Increase Hero's health.
                hero_object.increaseHealth(20);
                //Remove the mushroom.
                life_mushrooms.remove(tmp);
            }
        }
    }

    private void checkCollision_Obstacle_Hero(){

        //Check for collision between Lava pool and Hero.

        for(Obstacle tmp : obstacles) {

            if (collision_control.checkCollision(tmp.getBounds(), hero_object.getBounds())) {

                //Make lava sound
                audioController.makeSound(Sound.WALK_ON_LAVA);

                //Increase Hero's health.
                hero_object.decreaseHealth(1);
                heroBounce();
            }
        }

        //If health 0, Game over!
        if(hero_object.getHealth() == 0){
            audioController.makeSound(Sound.HERO_HIT);
            callGameOver();
        }
    }


    //Create a life giving mushroom at a random moment.
    private void addRandomLifeMushroom(){
        if(life_mushrooms.size() == 0 && RandomGenerator.getRandomInt(1000)==5) {
            life_mushrooms.add(LifeMushroomFactory.createLifeMushroom(this,
                    BitmapFactory.decodeResource(getResources(), R.drawable.up_mushroom_smb)));
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

    //Get for Highscore.
    public HighScore getHighScore(){
        return score;
    }

    //Get for Hero object.
    public Hero getHeroObj(){
        return hero_object;
    }
}