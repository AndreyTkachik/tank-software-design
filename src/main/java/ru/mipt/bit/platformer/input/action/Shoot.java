package ru.mipt.bit.platformer.input.action;

import com.badlogic.gdx.math.GridPoint2;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.input.Action;
import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;

@Component
public class Shoot implements Action {

    @Override
    public void execute(TankModel tank, List<EntityModel> entities) {
        GridPoint2 bulletPos = new GridPoint2(
                tank.getPosition().x + tankDirectionOffsetX(tank),
                tank.getPosition().y + tankDirectionOffsetY(tank)
        );

        BulletModel bullet = new BulletModel(bulletPos, tank.getRotation(), tank.getLayer(), tank);
        entities.add(bullet);
    }

    private int tankDirectionOffsetX(TankModel tank) {
        if (tank.getRotation() == 0f) return 1;
        if (tank.getRotation() == 180f) return -1;
        return 0;
    }

    private int tankDirectionOffsetY(TankModel tank) {
        if (tank.getRotation() == 90f) return 1;
        if (tank.getRotation() == -90f) return -1;
        return 0;
    }
}
