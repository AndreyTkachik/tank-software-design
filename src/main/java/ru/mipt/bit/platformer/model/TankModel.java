package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.enums.MovementDirection;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.EntityView;

import java.util.List;

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
    }

    public void move(MovementDirection direction, List<EntityModel> obstacles) {
        if (isEqual(progress, 1f)) {
            GridPoint2 next = new GridPoint2(
                    position.x + direction.getCoordinateX(),
                    position.y + direction.getCoordinateY()
            );

            boolean noCollision = true;
            for (EntityModel e: obstacles) {
                if(e.getPosition().equals(next)){
                    noCollision = false;
                    break;
                }
            }

            if (noCollision) {
                playerDestinationCoordinates.set(next);
                progress = 0f;
                rotation = direction.getRotation();
            }
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

}
