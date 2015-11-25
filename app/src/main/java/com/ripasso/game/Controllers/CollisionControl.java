package com.ripasso.game.Controllers;

import android.graphics.Rect;

/*Class for collision control between objects.
* Mathias Berneland & Niklas Nachtweij
* */

public class CollisionControl {

    //Constructor
    public CollisionControl(){}

    //Collision control method that check if two rectangle is intersecting.
    public boolean checkCollision(Rect first_sprite, Rect collision_object){

        if(first_sprite.intersect(collision_object)){
            return true;
        }
        else{
            return false;
        }
    }
}
