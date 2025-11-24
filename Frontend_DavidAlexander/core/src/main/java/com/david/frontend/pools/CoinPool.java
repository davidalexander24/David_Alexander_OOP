package com.david.frontend.pools;

import com.badlogic.gdx.math.Vector2;
import com.david.frontend.Coin;

public class CoinPool extends ObjectPool<Coin> {

    @Override
    public void createObject{
        return new Coin(new Vector2(0,0));
    }

    @Override
    public void resetObject{
        Coin.setActive(false)
    }


    public obtain(float x, float y) {
        super.obtain();
        Coin()
        return Coin;
    }
}
