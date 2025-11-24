package com.david.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.david.frontend.strategies.DifficultyStrategy;

public class DifficultyTransitionState implements GameState {
    private final GameStateManager gsm;
    private final PlayingState playingState;
    private final DifficultyStrategy newStrategy;
    private final BitmapFont font;
    private float timer;

    public DifficultyTransitionState(GameStateManager gsm, PlayingState playingState, DifficultyStrategy newStrategy) {
        this.gsm = gsm;
        this.playingState = playingState;
        this.newStrategy = newStrategy;
        this.font = new BitmapFont();
        this.timer = 2.0f;
    }

    @Override
    public void update(float delta) {
        timer -= delta;
        if (timer <= 0) {
            playingState.setDifficulty(newStrategy);
            gsm.pop();
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        playingState.render(batch);

        batch.begin();

        String difficultyText = "DIFFICULTY INCREASED!";
        String strategyName = getStrategyName(newStrategy);

        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        font.draw(batch, difficultyText, centerX - 100, centerY + 20);

        font.draw(batch, "New Difficulty: " + strategyName, centerX - 100, centerY - 20);

        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    private String getStrategyName(DifficultyStrategy strategy) {
        String className = strategy.getClass().getSimpleName();

        if (className.endsWith("DifficultyStrategy")) {
            return className.substring(0, className.length() - "DifficultyStrategy".length());
        }
        return className;
    }
}

