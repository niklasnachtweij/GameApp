package com.ripasso.game;

import android.graphics.BitmapFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ObstacleFactory {

    public static ArrayList<Obstacle> createObstacle(GameView gameView, int numberObstacles) {

        CollisionControl collision_control = new CollisionControl();

        int number = numberObstacles+1;

        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

        while (obstacles.size() < number) {

            for (int i = 0; i < number; i++)
                obstacles.add(new Obstacle(gameView, BitmapFactory.decodeResource(gameView.getResources(), R.drawable.tablet)));

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