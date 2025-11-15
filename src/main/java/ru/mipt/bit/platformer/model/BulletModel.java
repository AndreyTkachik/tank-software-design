package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.view.BulletView;

import java.util.ArrayList;
import java.util.List;

public class BulletModel extends EntityModel {
    private final TankModel shooter;
    private final int damage = 20;

    public BulletModel(GridPoint2 position, float rotation, TiledMapTileLayer layer, TankModel shooter) {
        super(position, rotation, layer, new BulletView("images/bullet.png"));
        this.shooter = shooter;
    }

    public void update(List<EntityModel> entities) {
        GridPoint2 next = new GridPoint2(
                position.x + getOffsetX(),
                position.y + getOffsetY()
        );

        if (next.x < 0 || next.y < 0 || next.x >= layer.getWidth() || next.y >= layer.getHeight()) {
            entities.remove(this);
            return;
        }

        for (EntityModel e : new ArrayList<>(entities)) {
            if (e == this || e == shooter) continue;

            if (e.getPosition().equals(next)) {
                if (e instanceof TankModel) {
                    e.health -= damage;
                    if (e.health <= 0) {
                        entities.remove(e);
                    }
                }
                entities.remove(this);
                return;
            }
        }

        position.set(next);
    }

    private int getOffsetX() {
        if (rotation == 0f) return 1;
        if (rotation == 180f) return -1;
        return 0;
    }

    private int getOffsetY() {
        if (rotation == 90f) return 1;
        if (rotation == -90f) return -1;
        return 0;
    }
}

