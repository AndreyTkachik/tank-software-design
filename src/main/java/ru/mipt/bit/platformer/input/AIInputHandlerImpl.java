package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.input.action.MoveAIRandom;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;

public class AIInputHandlerImpl implements InputHandler {

    private final Action moveAIRandom = new MoveAIRandom();

    @Override
    public void handleInput(TankModel tank, List<EntityModel> entities) {
        moveAIRandom.execute(tank, entities);
    }
}
