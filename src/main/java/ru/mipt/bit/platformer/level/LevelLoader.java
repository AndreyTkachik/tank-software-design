package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.view.EntityView;

public interface LevelLoader {
    LevelEntitiesData loadLevel(TiledMapTileLayer layer, EntityView tankView, EntityView treeView);
}
