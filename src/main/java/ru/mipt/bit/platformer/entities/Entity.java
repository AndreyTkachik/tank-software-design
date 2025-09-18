package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Entity {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private float rotation;
    private final GridPoint2 position;
    private final TiledMapTileLayer layer;

    public Entity(String texturePath, float rotation, GridPoint2 position, TiledMapTileLayer layer) {
        this.texture = new Texture(texturePath);
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.rotation = rotation;
        this.position = position;
        this.layer = layer;
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, this.textureRegion, this.rectangle, this.rotation);
    }

    public GridPoint2 getPosition() {
        return this.position;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public TiledMapTileLayer getLayer() {
        return layer;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
