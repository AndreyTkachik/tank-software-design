package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.ArrayList;
import java.util.List;

public class LevelEntitiesData {
    private final TankModel tank;
    private final List<TankModel> aiTanks;
    private final List<EntityModel> obstacles;

    public LevelEntitiesData(TankModel tank, List<TankModel> aiTanks, List<EntityModel> obstacles) {
        this.tank = tank;
        this.aiTanks = aiTanks;
        this.obstacles = obstacles;
    }

    public TankModel getTank() {
        return tank;
    }

    public List<TankModel> getAiTanks() {
        return aiTanks;
    }

    public List<EntityModel> getAllEntities() {
        List<EntityModel> result = new ArrayList<>(obstacles);
        result.add(tank);
        result.addAll(aiTanks);
        return result;
    }
}
