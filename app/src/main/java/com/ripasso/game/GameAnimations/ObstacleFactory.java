package com.ripasso.game.GameAnimations;

import android.graphics.BitmapFactory;

import com.ripasso.game.Controllers.CollisionControl;
import com.ripasso.game.GameViews.GameView_Level1;
import com.ripasso.game.R;

import java.util.ArrayList;

/*This is a factory class for creating obstacles.
* Mathias Berneland & Niklas Nachtweij.
* */

public class ObstacleFactory {

    //Create a number of Obstacle that is not intersecting and returning as ArrayList<Obstacle>.
    public static ArrayList<Obstacle> createObstacle(GameView_Level1 gameViewLevel1, int numberObstacles) {

        CollisionControl collision_control = new CollisionControl();
        int number = numberObstacles+1;
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();


        while (obstacles.size() < number) { //Loop as long as the arraylist is smaller than number.

            //Fill the arraylist with a given number of Obstacles.
            for (int i = 0; i < number; i++)
                obstacles.add(new Obstacle(gameViewLevel1, BitmapFactory.decodeResource(gameViewLevel1.getResources(), R.drawable.lavapuddle)));

            //Check if some objects is intersecting, then remove and add a new one.
            for (int i = 0; i < obstacles.size(); i++) {
                for (int j = 1; j < obstacles.size() - 1; j++) {

                    if (collision_control.checkCollision(obstacles.get(i).getBounds(), obstacles.get(j).getBounds())) {
                        obstacles.remove(i);
                        obstacles.add(new Obstacle(gameViewLevel1, BitmapFactory.decodeResource(gameViewLevel1.getResources(), R.drawable.lavapuddle)));
                    }
                }
            }
        }
        return obstacles;
    }



}