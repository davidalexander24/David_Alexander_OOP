package com.david.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.david.frontend.GameManager;

public class GameOverState implements GameState{
    private final GameStateManager gsm;
    private final BitmapFont font;

    public GameOverState(GameStateManager gsm) {
        this.gsm = gsm;
        this.font = new BitmapFont();
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gsm.set(new PlayingState(gsm));
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        font.draw(batch, "GAME OVER", 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Press SPACE to restart", 20, Gdx.graphics.getHeight() - 60);
    }

    public void dispose() {
        font.dispose();
    }

}
