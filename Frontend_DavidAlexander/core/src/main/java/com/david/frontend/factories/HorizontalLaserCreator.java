package com.david.frontend.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.david.frontend.obstacles.BaseObstacle;
import com.david.frontend.obstacles.HorizontalLaser;
import com.david.frontend.pools.HorizontalLaserPool;

import java.util.List;
import java.util.Random;

public class HorizontalLaserCreator implements ObstacleFactory.ObstacleCreator {
    private final HorizontalLaserPool pool = new HorizontalLaserPool();
    private static final float MIN_LENGTH = 100f;
    private static final float MAX_LENGTH = 300f;

    @Override
    public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng) {
        int length = (int) (rng.nextFloat() * (MAX_LENGTH - MIN_LENGTH) + MIN_LENGTH);

        float minY = groundTopY + playerHeight;
        float maxY = Gdx.graphics.getHeight() - playerHeight;
        float randomY = rng.nextFloat() * (maxY - minY) + minY;

        Vector2 position = new Vector2(spawnX, randomY);
        return pool.obtain(position, length);
    }

    @Override
    public void release(BaseObstacle obstacle) {
        if (obstacle instanceof HorizontalLaser) {
            pool.release((HorizontalLaser) obstacle);
        }
    }

    @Override
    public void releaseAll() {
        pool.releaseAll();
    }

    @Override
    public List<HorizontalLaser> getInUse() {
        return pool.getInUse();
    }

    @Override
    public boolean supports(BaseObstacle obstacle) {
        return obstacle instanceof HorizontalLaser;
    }

    @Override
    public String getName() {
        return "HorizontalLaser";
    }
}
