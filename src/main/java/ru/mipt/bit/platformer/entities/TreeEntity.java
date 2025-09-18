package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeEntity extends Entity {

    public TreeEntity(String texturePath, float rotation, GridPoint2 position, TiledMapTileLayer layer) {
        super(texturePath, rotation, position, layer);
        moveRectangleAtTileCenter(super.getLayer(), super.getRectangle(), super.getPosition());
    }
}
