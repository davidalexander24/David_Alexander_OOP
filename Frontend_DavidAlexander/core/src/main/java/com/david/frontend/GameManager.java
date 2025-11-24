package com.david.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.david.frontend.observers.Observer;
import com.david.frontend.observers.ScoreManager;
import com.david.frontend.services.BackendService;

public class GameManager {
    private static GameManager instance;

    private ScoreManager scoreManager;
    private boolean gameActive;
    private BackendService backendService;
    private String currentPlayerId;
    private int coinsCollected = 0;

    private GameManager() {
    scoreManager = new ScoreManager();
    scoreManager.setScore(0);
    gameActive = false;
    backendService = new BackendService();
    }

    public void registerPlayer(String username) {
        backendService.createPlayer(username, new BackendService.RequestCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    com.badlogic.gdx.utils.JsonValue json = new JsonReader().parse(response);
                    currentPlayerId = json.getString("playerId");
                    Gdx.app.log("GameManager", "Player ID saved: " + currentPlayerId);
                } catch (RuntimeException e) {
                    Gdx.app.error("GameManager", "Failed to parse response: " + e.getMessage());
                }
            }

            @Override
            public void onError(String error) {
                Gdx.app.error("GameManager", "Error registering player: " + error);
            }
        });
    }

    public static GameManager getInstance() {
    if (instance == null) {
    instance = new GameManager();
    }
    return instance;
    }

    public void startGame() {
    scoreManager.setScore(0);
    coinsCollected = 0;
    gameActive = true;
    System.out.println("Game Started!");
    }

    public void endGame() {
        if (currentPlayerId == null) {
            Gdx.app.error("GameManager", "Cannot submit score...");
            return;
        }
        int finalScore = scoreManager.getScore() + (coinsCollected * 10);
        int distance = scoreManager.getScore();
        backendService.submitScore(currentPlayerId, finalScore, coinsCollected, distance, new BackendService.RequestCallback() {
            @Override
            public void onSuccess(String response) {
                Gdx.app.log("GameManager", "Score submitted successfully: " + response);
            }

            @Override
            public void onError(String error) {
                Gdx.app.error("GameManager", "Failed to submit score: " + error);
            }
        });
    }

    public void addCoin() {
        coinsCollected++;
        Gdx.app.log("GameManager", "COIN COLLECTED! Total: " + coinsCollected);
    }

    public void setScore(int distance) {
        if (gameActive) {
            scoreManager.setScore(distance);
        }
    }

    public void addObserver(Observer observer) {
    scoreManager.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
    scoreManager.removeObserver(observer);
    }

    // Getters
    public int getScore() { return scoreManager.getScore(); }

    public int getCoins() {
        return coinsCollected;
    }
}
