package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.enums.MovementDirection;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.EntityView;

import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class TankModel extends EntityModel {
    private final GridPoint2 playerDestinationCoordinates;
    private float progress = 1f;
    private final float speed;

    public TankModel(GridPoint2 position, float rotation, TiledMapTileLayer layer, float speed, EntityView view) {
        super(position, rotation, layer, view);
        this.playerDestinationCoordinates = new GridPoint2(position);
        this.speed = speed;
        this.health = 80 + new Random().nextInt(21);
    }

    public void move(MovementDirection direction, List<EntityModel> obstacles) {
        if (isEqual(progress, 1f)) {
            GridPoint2 next = new GridPoint2(
                    position.x + direction.getCoordinateX(),
                    position.y + direction.getCoordinateY()
            );

            if (checkLevelBounds(direction, next)) return;

            boolean collision = isCollision(obstacles, next);

            setNewCoordinates(direction, collision, next);
        }
    }

    public void update(TileMovement movement, float deltaTime) {
        movement.moveRectangleBetweenTileCenters(
                getView().getRectangle(), position, playerDestinationCoordinates, progress
        );
        progress = continueProgress(progress, deltaTime, speed);
        if (isEqual(progress, 1f)) {
            position.set(playerDestinationCoordinates);
        }
    }

    public GridPoint2 getPlayerDestinationCoordinates() {
        return playerDestinationCoordinates;
    }

    private void setNewCoordinates(MovementDirection direction, boolean collision, GridPoint2 next) {
        if (collision) {
            playerDestinationCoordinates.set(next);
            progress = 0f;
            rotation = direction.getRotation();
        }
    }

    private boolean isCollision(List<EntityModel> obstacles, GridPoint2 next) {
        boolean collision = true;
        for (EntityModel e: obstacles) {
            if (e instanceof TankModel) {
                TankModel other = (TankModel) e;
                if (other == this) continue;

                if (other.getPosition().equals(next)
                        || other.getPlayerDestinationCoordinates().equals(next)) {
                    collision = false;
                    break;
                }
            } else {
                if (e.getPosition().equals(next)) {
                    collision = false;
                    break;
                }
            }
        }
        return collision;
    }

    private boolean checkLevelBounds(MovementDirection direction, GridPoint2 next) {
        int width = layer.getWidth();
        int height = layer.getHeight();
        if (next.x < 0 || next.y < 0 || next.x >= width || next.y >= height) {
            rotation = direction.getRotation();
            return true;
        }
        return false;
    }
}
