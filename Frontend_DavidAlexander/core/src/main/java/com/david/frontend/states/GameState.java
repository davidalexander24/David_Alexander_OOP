package com.david.frontend.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameState {
    public void update(float delta);
    public void render(SpriteBatch batch);
    public void dispose();
}
