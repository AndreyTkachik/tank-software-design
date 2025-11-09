package ru.mipt.bit.platformer.input.action;

import ru.mipt.bit.platformer.enums.MovementDirection;
import ru.mipt.bit.platformer.input.Action;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;
import java.util.Random;

public class MoveAIRandom implements Action {

    private final Random random = new Random();

    @Override
    public void execute(TankModel tank, List<EntityModel> entities) {
        MovementDirection[] directions = MovementDirection.values();
        MovementDirection randomDirection = directions[random.nextInt(directions.length)];
        tank.move(randomDirection, entities);
    }
}
