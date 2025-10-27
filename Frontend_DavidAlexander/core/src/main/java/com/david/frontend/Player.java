package com.david.frontend;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private static final float GRAVITY = 2000f;
    private static final float force = 4500f;
    private static final float maxVerticalSpeed = 700f;
    private Rectangle collider;

}
