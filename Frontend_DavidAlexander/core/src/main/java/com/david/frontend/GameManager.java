package com.david.frontend;

import com.david.frontend.observers.ScoreManager;

public class GameManager {
    private static GameManager instance;

    private ScoreManager score;
    private boolean gameActive;

    private GameManager() {
        score.setScore(0);
        gameActive = false;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startGame() {
        score.setScore(0);
        gameActive = true;
        System.out.println("Game Started!");
    }

    public void setScore(int newScore) {
        if (gameActive) {
            score.setScore(newScore);
        }
    }

    // Getters
    public int getScore() { return score.getScore(); }
}
