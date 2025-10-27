package com.david.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

    public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    private float squareX;
    private float squareY;
    private float squareSize = 50f;
    private float moveSpeed = 300f;

    private enum ColorState {
        RED, YELLOW, BLUE
    }

    private ColorState currentColor = ColorState.RED;
    private boolean wasMousePressed = false;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

        squareX = (Gdx.graphics.getWidth() - squareSize) / 2;
        squareY = (Gdx.graphics.getHeight() - squareSize) / 2;

        System.out.println("Game started. Initial color: RED");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        handleInput();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        switch (currentColor) {
            case RED:
                shapeRenderer.setColor(Color.RED);
                break;
            case YELLOW:
                shapeRenderer.setColor(Color.YELLOW);
                break;
            case BLUE:
                shapeRenderer.setColor(Color.BLUE);
                break;
        }

        shapeRenderer.rect(squareX, squareY, squareSize, squareSize);
        shapeRenderer.end();
    }

    private void handleInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        float newX = squareX;
        float newY = squareY;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            newX -= moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += moveSpeed * deltaTime;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            newY -= moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            newY += moveSpeed * deltaTime;
        }

        newX = Math.max(0, Math.min(newX, Gdx.graphics.getWidth() - squareSize));
        newY = Math.max(0, Math.min(newY, Gdx.graphics.getHeight() - squareSize));

        squareX = newX;
        squareY = newY;

        boolean isMousePressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        if (isMousePressed && !wasMousePressed) {
            changeColor();
        }

        wasMousePressed = isMousePressed;
    }

    private void changeColor() {
        switch (currentColor) {
            case RED:
                currentColor = ColorState.YELLOW;
                System.out.println("Color changed to: YELLOW");
                break;
            case YELLOW:
                currentColor = ColorState.BLUE;
                System.out.println("Color changed to: BLUE");
                break;
            case BLUE:
                currentColor = ColorState.RED;
                System.out.println("Color changed to: RED");
                break;
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
