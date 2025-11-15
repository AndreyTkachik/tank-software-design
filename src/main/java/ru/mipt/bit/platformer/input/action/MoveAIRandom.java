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
        int choice = random.nextInt(directions.length + 1);
        if (choice < directions.length) {
            tank.move(directions[choice], entities);
        } else {
            new Shoot(entities).execute(tank, entities);
        }

    }
}
