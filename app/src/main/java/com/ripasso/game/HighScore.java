package com.ripasso.game;


public class HighScore {

    private int score;
    private int DEFAULT_SCORE = 0;

    public HighScore(){
        score = DEFAULT_SCORE;
    }

    public HighScore(int score){
        this.score = score;
    }

    public void AddScore(int amount_of_scores){
        this.score += amount_of_scores;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
