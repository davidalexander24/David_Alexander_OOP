package com.david.frontend.factories;

import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.david.frontend.obstacles.BaseObstacle;
import com.david.frontend.obstacles.VerticalLaser;
import com.david.frontend.obstacles.HorizontalLaser;
import com.david.frontend.obstacles.HomingMissile;
import com.david.frontend.pools.VerticalLaserPool;
import com.david.frontend.pools.HorizontalLaserPool;
import com.david.frontend.pools.HomingMissilePool;

public class ObstacleFactory {

    /** Factory Method implementor */
    public interface ObstacleCreator {
        // TODO: Deklarasikan abstract method untuk membuat objek baru.
        // Menerima groundTopY, spawnX, playerHeight, dan Random,
        // dan mengembalikan BaseObstacle.
        BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random random);

        // TODO: Deklarasikan abstract method release() untuk me-release BaseObstacle ke pool.
        // Menerima BaseObstacle.
        void release(BaseObstacle obstacle);

        // TODO: Deklarasikan abstract method releaseAll() untuk me-release semua objek aktif.
        // Tidak menerima apapun.
        void releaseAll();

        // TODO: Deklarasikan abstract method getInUse() untuk mendapatkan list objek yang sedang digunakan.
        // Mengembalikan List<? extends BaseObstacle>.
        // Tidak menerima apapun.
        List<? extends BaseObstacle> getInUse();

        // TODO: Deklarasikan abstract method supports() untuk mengecek apakah creator ini mendukung tipe BaseObstacle yang diberikan.
        // Mengembalikan boolean.
        // Menerima BaseObstacle.
        boolean supports(BaseObstacle obstacle);

        // TODO: Deklarasikan abstract method getName() untuk mendapatkan nama creator.
        // Mengembalikan String.
        // Tidak menerima apapun.
        String getName();
    }

    /** Weighted creator for probability-based spawning */
    private static class WeightedCreator {
        ObstacleCreator creator;
        int weight;

        WeightedCreator(ObstacleCreator creator, int weight) {
            this.creator = creator;
            this.weight = weight;
        }
    }

    private static class VerticalLaserCreator implements ObstacleCreator {
        private final VerticalLaserPool pool = new VerticalLaserPool();

        @Override
        public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random random) {
            int minLength = (int) (playerHeight * 1.5f);
            int maxLength = Gdx.graphics.getHeight() - (int) groundTopY;
            int length = random.nextInt(maxLength - minLength + 1) + minLength;

            Vector2 position = new Vector2(spawnX, groundTopY);
            return pool.obtain(position, length);
        }

        @Override
        public void release(BaseObstacle obstacle) {
            if (obstacle instanceof VerticalLaser) {
                pool.release((VerticalLaser) obstacle);
            }
        }

        @Override
        public void releaseAll() {
            pool.releaseAll();
        }

        @Override
        public List<? extends BaseObstacle> getInUse() {
            return pool.getInUse();
        }

        @Override
        public boolean supports(BaseObstacle obstacle) {
            return obstacle instanceof VerticalLaser;
        }

        @Override
        public String getName() {
            return "VerticalLaserCreator";
        }
    }

    private static class HorizontalLaserCreator implements ObstacleCreator {
        private final HorizontalLaserPool pool = new HorizontalLaserPool();

        @Override
        public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random random) {
            int minLength = 100;
            int maxLength = 400;
            int length = random.nextInt(maxLength - minLength + 1) + minLength;

            float minY = groundTopY + playerHeight;
            float maxY = Gdx.graphics.getHeight() - 50;
            float y = random.nextFloat() * (maxY - minY) + minY;

            Vector2 position = new Vector2(spawnX, y);
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
        public List<? extends BaseObstacle> getInUse() {
            return pool.getInUse();
        }

        @Override
        public boolean supports(BaseObstacle obstacle) {
            return obstacle instanceof HorizontalLaser;
        }

        @Override
        public String getName() {
            return "HorizontalLaserCreator";
        }
    }

    private static class HomingMissileCreator implements ObstacleCreator {
        private final HomingMissilePool pool = new HomingMissilePool();

        @Override
        public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random random) {
            float minY = groundTopY;
            float maxY = Gdx.graphics.getHeight() - 100;
            float y = random.nextFloat() * (maxY - minY) + minY;

            Vector2 position = new Vector2(spawnX, y);
            return pool.obtain(position);
        }

        @Override
        public void release(BaseObstacle obstacle) {
            if (obstacle instanceof HomingMissile) {
                pool.release((HomingMissile) obstacle);
            }
        }

        @Override
        public void releaseAll() {
            pool.releaseAll();
        }

        @Override
        public List<? extends BaseObstacle> getInUse() {
            return pool.getInUse();
        }

        @Override
        public boolean supports(BaseObstacle obstacle) {
            return obstacle instanceof HomingMissile;
        }

        @Override
        public String getName() {
            return "HomingMissileCreator";
        }
    }

    private final List<WeightedCreator> weightedCreators = new ArrayList<>();
    private final Random random = new Random();
    private int totalWeight = 0;

    public ObstacleFactory() {
        register(new VerticalLaserCreator(), 2);
        register(new HorizontalLaserCreator(), 2);
        register(new HomingMissileCreator(), 1);
    }

    public void register(ObstacleCreator creator, int weight) {
        weightedCreators.add(new WeightedCreator(creator, weight));
        totalWeight += weight;
    }

    /** Factory Method using weighted random selection */
    public BaseObstacle createRandomObstacle(float groundTopY, float spawnX, float playerHeight) {
        if (weightedCreators.isEmpty()) {
            throw new IllegalStateException("No obstacle creators registered");
        }

        ObstacleCreator creator = selectWeightedCreator();
        return creator.create(groundTopY, spawnX, playerHeight, random);
    }

    private ObstacleCreator selectWeightedCreator() {
        int randomValue = random.nextInt(totalWeight);
        int currentWeight = 0;

        for (WeightedCreator wc : weightedCreators) {
            currentWeight += wc.weight;
            if (randomValue < currentWeight) {
                return wc.creator;
            }
        }

        return weightedCreators.get(0).creator;
    }

    public void releaseObstacle(BaseObstacle obstacle) {
        for (WeightedCreator wc : weightedCreators) {
            if (wc.creator.supports(obstacle)) {
                wc.creator.release(obstacle);
                return;
            }
        }
    }

    public void releaseAllObstacles() {
        for (WeightedCreator wc : weightedCreators) {
            wc.creator.releaseAll();
        }
    }

    public List<BaseObstacle> getAllInUseObstacles() {
        List<BaseObstacle> list = new ArrayList<>();

        for (WeightedCreator wc : weightedCreators) {
            list.addAll(wc.creator.getInUse());
        }

        return list;
    }

    public List<String> getRegisteredCreatorNames() {
        List<String> names = new ArrayList<>();

        for (WeightedCreator wc : weightedCreators) {
            names.add(wc.creator.getName());
        }

        return names;
    }
}
