package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;

public class LevelEntitiesData {
    private final TankModel tank;
    private final List<EntityModel> obstacles;

    public LevelEntitiesData(TankModel tank, List<EntityModel> obstacles) {
        this.tank = tank;
        this.obstacles = obstacles;
    }

    public List<EntityModel> getObstacles() {
        return obstacles;
    }

    public TankModel getTank() {
        return tank;
    }
}
