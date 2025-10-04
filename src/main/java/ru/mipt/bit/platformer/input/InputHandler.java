package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;

public interface InputHandler {
    void handleInput(TankModel mover, List<EntityModel> entities);
}
