package com.david.frontend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Shape {
    protected Vector2 position = new Vector2();
    protected float size = 50f;
    protected String type;

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public String getType() {
        return type;
    }

    public abstract void render(ShapeRenderer renderer);
}
