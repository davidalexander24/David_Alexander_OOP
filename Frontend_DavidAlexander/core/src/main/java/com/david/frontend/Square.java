package com.david.frontend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Square extends Shape {
    public Square() {
        this.type = "Square";
    }

    @Override
    public void render(ShapeRenderer renderer) {
        float half = size / 2f;
        renderer.rect(position.x - half, position.y - half, size, size);
    }
}
