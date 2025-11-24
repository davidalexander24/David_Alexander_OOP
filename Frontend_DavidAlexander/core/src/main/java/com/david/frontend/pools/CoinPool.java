package com.david.frontend.pools;

import com.badlogic.gdx.math.Vector2;
import com.david.frontend.Coin;

public class CoinPool extends ObjectPool<Coin> {

    @Override
    protected Coin createObject() {
        return new Coin(new Vector2(0, 0));
    }

    @Override
    protected void resetObject(Coin coin) {
        coin.setActive(false);
    }

    public Coin obtain(float x, float y) {
        Coin coin = super.obtain();
        coin.setPosition(x, y);
        coin.setActive(true);
        return coin;
    }
}
