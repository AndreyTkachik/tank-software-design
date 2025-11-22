package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Gdx;
import org.springframework.stereotype.Component;

@Component
public class GdxInputProvider implements InputProvider {

    @Override
    public boolean isKeyPressed(int keyCode) {
        return Gdx.input.isKeyPressed(keyCode);
    }
}
