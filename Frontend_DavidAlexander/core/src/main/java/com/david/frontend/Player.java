package com.david.frontend;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private static final float GRAVITY = 2000f;
    private static final float force = 4500f;
    private static final float maxVerticalSpeed = 700f;
    private Rectangle collider;
    private float width = 64f;
    private float height = 64f;

    private float baseSpeed = 300f;
    private float distanceTraveled = 0f;

    private boolean isDead;
    private Vector2 startPosition;

    public Player(Vector2 startPosition) {
        this.position = startPosition;
        this.startPosition = new Vector2(startPosition);
        this.velocity = new Vector2(baseSpeed, 0);
        this.collider = new Rectangle(position.x, position.y, width, height);
        this.isDead = false;
    }

    public void update(float delta, boolean isFlying) {
        if (!isDead) {
            updateDistanceAndSpeed(delta);
            applyGravity(delta);
            if (isFlying) {
                fly(delta);
            }
            updatePosition(delta);
        }
    }

    private void updateDistanceAndSpeed(float delta) {
        distanceTraveled += velocity.x * delta;
    }

    private void updatePosition(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        updateCollider();
    }


    private void applyGravity(float delta) {
        velocity.y -= GRAVITY * delta;
        velocity.x = baseSpeed;

        if (velocity.y > maxVerticalSpeed) {
            velocity.y = maxVerticalSpeed;
        }

        if (velocity.y < -maxVerticalSpeed) {
            velocity.y = -maxVerticalSpeed;
        }
    }

    private void fly(float delta) {
        velocity.y += force * delta;
    }

    private void updateCollider() {
        collider.setPosition(position.x, position.y);
    }

    public void checkBoundaries(Ground ground, float ceilingY) {
        if (ground.isColliding(collider)) {
            position.y = ground.getTopY();
            velocity.y = 0;
            updateCollider();
        }

        if (position.y + height > ceilingY) {
            position.y = ceilingY - height;
            velocity.y = 0;
            updateCollider();
        }
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0.2f, 0.6f, 1f, 1f);
        shapeRenderer.rect(position.x, position.y, width, height);
    }

    public void die() {
        isDead = true;
        velocity.set(0, 0);
    }

    public void reset() {
        isDead = false;
        position.set(startPosition);
        velocity.set(baseSpeed, 0);
        distanceTraveled = 0f;
        updateCollider();
    }

    public boolean isDead() {
        return isDead;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getCollider() {
        return collider;
    }

    public float getDistanceTraveled() {
        return distanceTraveled / 10;
    }
}
