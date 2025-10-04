package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.view.EntityView;

public class TreeModel extends EntityModel {
    public TreeModel(GridPoint2 position, float rotation, TiledMapTileLayer layer, EntityView view) {
        super(position, rotation, layer, view);
    }
}
