package ru.mipt.bit.platformer.view.decorator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.view.EntityView;
import ru.mipt.bit.platformer.view.TankView;

public class HealthDecorator extends EntityView {
    private final EntityView wrappedView;
    private final EntityModel model;
    private final BitmapFont font;

    public HealthDecorator(EntityView wrappedView, EntityModel model) {
        super("images/blank.png");
        this.wrappedView = wrappedView;
        this.model = model;
        this.font = new BitmapFont();
        this.font.getData().setScale(0.8f);
    }

    @Override
    public void render(Batch batch, GridPoint2 position, float rotation, TiledMapTileLayer layer) {
        wrappedView.render(batch, position, rotation, layer);

        if (!(wrappedView instanceof TankView)) {
            return;
        }

        Rectangle rect = wrappedView.getRectangle();
        float x = rect.getX();
        float y = rect.getY() + rect.getHeight() + 5;

        font.draw(batch, "HP: " + model.getHealth(), x, y);
    }

    public EntityView getWrappedView() {
        return wrappedView;
    }
}
