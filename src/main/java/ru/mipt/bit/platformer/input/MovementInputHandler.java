package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.enums.MovementDirection;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;

public class MovementInputHandler implements InputHandler {

    @Override
    public void handleInput(TankModel tank, List<EntityModel> entities) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) tank.move(MovementDirection.UP, entities);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) tank.move(MovementDirection.DOWN, entities);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) tank.move(MovementDirection.LEFT, entities);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) tank.move(MovementDirection.RIGHT, entities);
    }
}
