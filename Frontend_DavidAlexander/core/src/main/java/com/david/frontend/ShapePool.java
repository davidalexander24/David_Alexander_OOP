package com.david.frontend;

import java.util.ArrayList;

public class ShapePool {
    private final ArrayList<Shape> circlePool = new ArrayList<>();
    private final ArrayList<Shape> squarePool = new ArrayList<>();
    private final int MAX = 3;

    public Shape obtain(String type) {
        ArrayList<Shape> pool = getPool(type);
        if (pool == null) return null;
        if (!pool.isEmpty()) {
            return pool.remove(0);
        }
        return null;
    }

    public void release(Shape shape) {
        if (shape == null) return;
        ArrayList<Shape> pool = getPool(shape.getType());
        if (pool == null) return;
        if (pool.size() < MAX) {
            pool.add(shape);
        }
    }

    public ArrayList<Shape> getPool(String type) {
        if ("Circle".equals(type)) return circlePool;
        if ("Square".equals(type)) return squarePool;
        return null;
    }
}
