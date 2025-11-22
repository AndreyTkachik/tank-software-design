package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.view.EntityView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileLevelLoader implements LevelLoader {
    private final Path levelFilePath;
    private final float movementSpeed;

    public FileLevelLoader(Path levelFilePath, float movementSpeed) {
        this.levelFilePath = levelFilePath;
        this.movementSpeed = movementSpeed;
    }

    @Override
    public LevelEntitiesData loadLevel(TiledMapTileLayer layer, EntityView tankView, EntityView treeView) {
        List<String> lines;
        try {
            lines = Files.readAllLines(levelFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Нет доступа к файлу:" + levelFilePath, e);
        }

        List<EntityModel> obstacles = new ArrayList<>();
        TankModel player = null;

        int height = lines.size();
        for (int row = 0; row < height; row++) {
            String line = lines.get(row);
            int yCoordinate = height - 1 - row;
            for (int xCoordinate = 0; xCoordinate < line.length(); xCoordinate++) {
                char sign = line.charAt(xCoordinate);
                switch (sign) {
                    case 'T':
                        obstacles.add(new TreeModel(new GridPoint2(xCoordinate, yCoordinate), 0f, layer, treeView));
                        break;
                    case 'X':
                        player = new TankModel(new GridPoint2(xCoordinate, yCoordinate), 0f, layer, movementSpeed, tankView);
                        break;
                    default:
                        break;
                }
            }
        }
        return new LevelEntitiesData(player, Collections.emptyList(), obstacles);
    }
}
