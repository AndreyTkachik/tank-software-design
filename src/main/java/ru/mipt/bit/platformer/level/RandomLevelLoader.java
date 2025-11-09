package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.view.EntityView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomLevelLoader implements LevelLoader {

    private final int width;
    private final int height;
    private final int obstacleCount;
    private final int aiTankCount;
    private final float tankSpeed;
    private final TiledMapTileLayer layer;
    private final EntityView tankView;
    private final EntityView treeView;

    public RandomLevelLoader(int width, int height, int obstacleCount,
                             int aiTankCount, float tankSpeed,
                             TiledMapTileLayer layer, EntityView tankView,
                             EntityView treeView) {
        this.width = width;
        this.height = height;
        this.obstacleCount = obstacleCount;
        this.aiTankCount = aiTankCount;
        this.tankSpeed = tankSpeed;
        this.layer = layer;
        this.tankView = tankView;
        this.treeView = treeView;
    }

    @Override
    public LevelEntitiesData loadLevel() {
        Random random = new Random();

        GridPoint2 playerPos = new GridPoint2(random.nextInt(width), random.nextInt(height));
        TankModel tank = new TankModel(playerPos, 0f, layer, tankSpeed, tankView);

        List<EntityModel> obstacles = new ArrayList<>();
        List<TankModel> aiTanks = new ArrayList<>();

        for (int indx = 0; indx < obstacleCount; indx++) {
            GridPoint2 pos = new GridPoint2(random.nextInt(width), random.nextInt(height));
            if (!pos.equals(playerPos)) {
                obstacles.add(new TreeModel(pos, 0f, layer, treeView));
            }
        }

        for (int i = 0; i < aiTankCount; i++) {
            GridPoint2 newPosition = null;

            while (newPosition == null) {
                GridPoint2 attemptNewPosition = new GridPoint2(random.nextInt(width), random.nextInt(height));

                boolean isPlayerOnPosition = attemptNewPosition.equals(playerPos);
                boolean isObstacleOnPosition = false;
                for (int indx = 0; indx < obstacleCount; indx++) {
                    if (obstacles.get(indx).getPosition().equals(attemptNewPosition)) {
                        isObstacleOnPosition = true;
                        break;
                    }
                }

                if (!isPlayerOnPosition && !isObstacleOnPosition) {
                    newPosition = attemptNewPosition;
                    break;
                }
            }

            aiTanks.add(new TankModel(newPosition, 0f, layer, tankSpeed, tankView));
        }

        return new LevelEntitiesData(tank, aiTanks, obstacles);
    }
}
