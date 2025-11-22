package ru.mipt.bit.platformer.input;

import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.input.action.MoveAIRandom;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;

@Component
public class AIInputHandlerImpl implements InputHandler {

    private final Action moveAIRandom;

    public AIInputHandlerImpl(MoveAIRandom moveAIRandom) {
        this.moveAIRandom = moveAIRandom;
    }

    @Override
    public void handleInput(TankModel tank, List<EntityModel> entities) {
        moveAIRandom.execute(tank, entities);
    }
}
