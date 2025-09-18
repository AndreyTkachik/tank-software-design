package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.enums.MovementDirection;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class TankEntity extends Entity {

    private final GridPoint2 playerDestinationCoordinates;
    private float playerMovementProgress = 1f;
    private final float speed;

    public TankEntity(String texturePath, float rotation, GridPoint2 position, TiledMapTileLayer layer, float speed) {
        super(texturePath, rotation, position, layer);
        this.playerDestinationCoordinates = super.getPosition();
        this.speed = speed;
    }

    public void move(List<Entity> entities, TileMovement tileMovement, float deltaTime) {

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            makeMove(MovementDirection.FORWARD, 90f, entities);
        }

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            makeMove(MovementDirection.TO_THE_LEFT, -180f, entities);
        }

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            makeMove(MovementDirection.BACKWARD, -90f, entities);
        }

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            makeMove(MovementDirection.TO_THE_RIGHT, 0f, entities);
        }

        tileMovement.moveRectangleBetweenTileCenters(super.getRectangle(),
                super.getPosition(), playerDestinationCoordinates, playerMovementProgress);
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, speed);

        if (isEqual(playerMovementProgress, 1f)) {
            super.getPosition().set(playerDestinationCoordinates);
        }
    }

    private void makeMove(MovementDirection direction, float rotation, List<Entity> entities) {
        if (isEqual(playerMovementProgress, 1f)) {
            boolean noCollision = true;
            GridPoint2 destinationCoordinates = new GridPoint2(
                    super.getPosition().x + direction.getCoordinateX(),
                    super.getPosition().y + direction.getCoordinateY()
            );

            for (Entity entity : entities) {
                if (entity.getPosition().equals(destinationCoordinates)) {
                    noCollision = false;
                    break;
                }
            }

            if (noCollision) {
                playerDestinationCoordinates.set(destinationCoordinates);
                playerMovementProgress = 0f;
                super.setRotation(rotation);
            }
        }
    }
}
