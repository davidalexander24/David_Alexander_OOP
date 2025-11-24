package com.david.frontend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private Vector2 position;
    private Rectangle collider;
    private float radius = 15f;
    private boolean active;

    private float bobOffset;
    private float bobSpeed;

    public Coin(Vector2 startPosition) {
        this.position = new Vector2();
        this.collider = new Rectangle();
    }

    public void update(float delta) {
        Math.sin(bobSpeed * delta);
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(Color.YELLOW);
    }

    public boolean isColliding(Rectangle playerCollider) {
        return active && collider.overlaps(playerCollider);
    }
}
