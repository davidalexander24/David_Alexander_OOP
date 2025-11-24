package com.david.frontend.strategies;

import java.util.HashMap;
import java.util.Map;

public class EasyDifficultyStrategy implements DifficultyStrategy {
    @Override
    public Map<String, Integer> getObstacleWeights() {
        HashMap<String, Integer> weights = new HashMap<>();
        weights.put("VerticalLaser", 1);
        weights.put("HorizontalLaser", 1);
        return weights;
    }

    @Override
    public float getSpawnInterval() {
        return 2.5f; // Slower spawn rate
    }

    @Override
    public float getDensity() {
        return 0.3f; // Lower density
    }

    @Override
    public float getMinGap() {
        return 150f; // Larger gap between obstacles
    }
}
