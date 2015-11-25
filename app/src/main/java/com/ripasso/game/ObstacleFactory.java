package com.ripasso.game;

import android.graphics.BitmapFactory;
import java.util.ArrayList;

/*Class responsible for creating Obstacles.
* Mathias Berneland & Niklas Nachtweij.
* */

public class ObstacleFactory {

    //Create a number of Obstacle that is not intersecting and returning as ArrayList<Obstacle>.
    public static ArrayList<Obstacle> createObstacle(GameView gameView, int numberObstacles) {

        CollisionControl collision_control = new CollisionControl();
        int number = numberObstacles+1;
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();


        while (obstacles.size() < number) { //Loop as long as the arraylist is smaller than number.

            //Fill the arraylist with a given number of Obstacles.
            for (int i = 0; i < number; i++)
                obstacles.add(new Obstacle(gameView, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.tablet)));

            //Check if some objects is intersecting, then remove and add a new one.
            for (int i = 0; i < obstacles.size(); i++) {
                for (int j = 1; j < obstacles.size() - 1; j++) {

                    if (collision_control.checkCollision(obstacles.get(i).getBounds(), obstacles.get(j).getBounds())) {
                        obstacles.remove(i);
                        obstacles.add(new Obstacle(gameView, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.tablet)));
                    }
                }
            }
        }
        return obstacles;
    }



}