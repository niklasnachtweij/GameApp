package com.ripasso.game;

/*Class responsible for High score counts.
* Mathias Berneland & Niklas Nachtweij.
* */

public class HighScore {

    private int score; //Current score
    private int DEFAULT_SCORE = 0; //Default score

    //Constructor
    public HighScore(){
        score = DEFAULT_SCORE;
    }

    //Constructor
    public HighScore(int score){
        this.score = score;
    }

    //Increase the amount of score.
    public void AddScore(int amount_of_scores){
        this.score += amount_of_scores;
    }

    //Get method for highscore.
    public int getScore() {
        return score;
    }

    //Set method for highscore.
    public void setScore(int score) {
        this.score = score;
    }
}
