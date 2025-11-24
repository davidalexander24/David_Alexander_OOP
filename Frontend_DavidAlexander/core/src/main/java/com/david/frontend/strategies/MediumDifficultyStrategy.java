package com.david.frontend.strategies;

import java.util.HashMap;
import java.util.Map;

public class MediumDifficultyStrategy implements DifficultyStrategy {
    @Override
    public Map<String, Integer> getObstacleWeights() {
        HashMap<String, Integer> weights = new HashMap<>();
        weights.put("VerticalLaser", 2);
        weights.put("HorizontalLaser", 2);
        weights.put("DiagonalLaser", 2);
        weights.put("HomingMissile", 1);
        return weights;
    }

    @Override
    public float getSpawnInterval() {
        return 1.5f; // Medium spawn rate
    }

    @Override
    public float getDensity() {
        return 0.5f; // Medium density
    }

    @Override
    public float getMinGap() {
        return 100f; // Medium gap between obstacles
    }
}

