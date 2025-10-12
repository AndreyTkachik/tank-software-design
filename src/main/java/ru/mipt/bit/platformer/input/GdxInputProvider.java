package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Gdx;

public class GdxInputProvider implements InputProvider {

    @Override
    public boolean isKeyPressed(int keyCode) {
        return Gdx.input.isKeyPressed(keyCode);
    }
}
