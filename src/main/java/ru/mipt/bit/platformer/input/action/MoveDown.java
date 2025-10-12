package ru.mipt.bit.platformer.input.action;

import ru.mipt.bit.platformer.enums.MovementDirection;
import ru.mipt.bit.platformer.input.Action;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;
import java.util.List;

public class MoveDown implements Action {

    @Override
    public void execute(TankModel tank, List<EntityModel> entities) {
        tank.move(MovementDirection.DOWN, entities);
    }
}
