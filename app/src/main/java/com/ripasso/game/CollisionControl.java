package com.ripasso.game;

import android.graphics.Rect;

public class CollisionControl {

    public CollisionControl(){}

    public boolean checkCollision(Rect first_sprite, Rect collision_object){
        if(first_sprite.intersect(collision_object)){
            return true;
        }
        else{
            return false;
        }
    }

}
