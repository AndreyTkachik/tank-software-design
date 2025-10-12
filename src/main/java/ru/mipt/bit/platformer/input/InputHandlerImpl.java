package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;
import java.util.Map;

public class InputHandlerImpl implements InputHandler {
    private final InputProvider inputProvider;
    private final Map<Integer, Action> actions;

    public InputHandlerImpl(InputProvider inputProvider, Map<Integer, Action> keyActions) {
        this.inputProvider = inputProvider;
        this.actions = keyActions;
    }

    @Override
    public void handleInput(TankModel tank, List<EntityModel> entities) {
        actions.forEach((key, action) -> {
            if (inputProvider.isKeyPressed(key)) {
                action.execute(tank, entities);
            }
        });
    }
}
