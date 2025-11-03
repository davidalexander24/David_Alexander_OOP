package com.david.frontend;

public class ShapeFactory {
    private final ShapePool pool;

    public ShapeFactory(ShapePool pool) {
        this.pool = pool;
    }

    public Shape createShape(String type) {
        Shape s = pool.obtain(type);
        if (s != null) return s;
        if ("Circle".equals(type)) return new Circle();
        if ("Square".equals(type)) return new Square();
        return null;
    }

    public ShapePool getPool() {
        return pool;
    }
}
