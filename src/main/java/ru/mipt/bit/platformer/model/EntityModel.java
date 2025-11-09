package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.view.EntityView;

public abstract class EntityModel {
    protected final GridPoint2 position;
    protected float rotation;
    protected final TiledMapTileLayer layer;
    protected EntityView view;
    protected int health;

    public EntityModel(GridPoint2 position, float rotation, TiledMapTileLayer layer, EntityView view) {
        this.position = position;
        this.rotation = rotation;
        this.layer = layer;
        this.view = view;
        this.health = 100;
    }

    public GridPoint2 getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public TiledMapTileLayer getLayer() {
        return layer;
    }

    public EntityView getView() {
        return view;
    }

    public void setView(EntityView view) { this.view = view;}

    public int getHealth() {
        return health;
    }
}
