package com.david.frontend.strategies;

import java.util.Map;

public interface DifficultyStrategy {
    Map<String, Integer> getObstacleWeights();

    float getSpawnInterval();

    float getDensity();

    float getMinGap();
}

