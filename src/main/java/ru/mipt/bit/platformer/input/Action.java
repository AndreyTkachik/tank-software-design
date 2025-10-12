package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.EntityModel;

import java.util.List;

public interface Action {
    void execute(TankModel tank, List<EntityModel> entities);
}
