package com.david.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.david.frontend.observers.Observer;
import com.david.frontend.observers.ScoreManager;
import com.david.frontend.services.BackendService;

import java.net.IDN;

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
                    new JsonReader().parse(response);
                    currentPlayerId = response;
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }

                Gdx.app.log(ID);
            }

            @Override
            public void onError(String error) {
                Gdx.app.error("MyTag", "error");
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
        if(currentPlayerId == null) {
            Gdx.app.error("mYTAG","Cannot submit score...");
            return;
        }
    }

    public void setScore(int newScore) {
    if (gameActive) {
    scoreManager.setScore(newScore);
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
}
