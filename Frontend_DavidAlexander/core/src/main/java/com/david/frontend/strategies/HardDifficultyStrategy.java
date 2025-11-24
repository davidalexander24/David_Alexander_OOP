package com.david.frontend.strategies;

import java.util.HashMap;
import java.util.Map;

public class HardDifficultyStrategy implements DifficultyStrategy {
    @Override
    public Map<String, Integer> getObstacleWeights() {
        HashMap<String, Integer> weights = new HashMap<>();
        weights.put("VerticalLaser", 3);
        weights.put("HorizontalLaser", 3);
        weights.put("DiagonalLaser", 3);
        weights.put("HomingMissile", 4);
        return weights;
    }

    @Override
    public float getSpawnInterval() {
        return 0.8f; // Fast spawn rate
    }

    @Override
    public float getDensity() {
        return 0.8f; // High density
    }

    @Override
    public float getMinGap() {
        return 70f; // Smaller gap between obstacles
    }
}

