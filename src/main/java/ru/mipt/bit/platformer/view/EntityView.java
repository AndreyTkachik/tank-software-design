package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class EntityView {
    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;

    public EntityView(String texturePath) {
        this.texture = new Texture(texturePath);
        this.region = new TextureRegion(texture);
        this.rectangle = GdxGameUtils.createBoundingRectangle(region);
    }

    public void render(Batch batch, GridPoint2 position, float rotation, TiledMapTileLayer layer) {
        GdxGameUtils.moveRectangleAtTileCenter(layer, rectangle, position);
        GdxGameUtils.drawTextureRegionUnscaled(batch, region, rectangle, rotation);
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
