package ru.mipt.bit.platformer.test.tankTest;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.enums.MovementDirection;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.EntityView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TankModelTest {

    private TankModel tank;

    @BeforeEach
    void setUp() {
        TiledMapTileLayer layer = mock(TiledMapTileLayer.class);
        EntityView view = mock(EntityView.class);
        tank = new TankModel(new GridPoint2(1, 1), 0f, layer, 1f, view);
    }

    @Test
    void testMoveWithoutObstacles() {
        var obstacles = List.<EntityModel>of();
        tank.move(MovementDirection.UP, obstacles);

        assertEquals(new GridPoint2(1, 2), tank.getPlayerDestinationCoordinates());
        assertEquals(90f, tank.getRotation());
    }

    @Test
    void testMoveWithObstacle() {
        var obstacle = mock(EntityModel.class);
        when(obstacle.getPosition()).thenReturn(new GridPoint2(1, 2));

        tank.move(MovementDirection.UP, List.of(obstacle));

        assertEquals(new GridPoint2(1, 1), tank.getPlayerDestinationCoordinates());
    }

    @Test
    void testMoveWhenProgressNotCompleteDoesNothing() {
        tank.move(MovementDirection.UP, List.of());
        tank.update(mock(TileMovement.class), 1f);

        tank.move(MovementDirection.DOWN, List.of());
        assertNotEquals(180f, tank.getRotation());
    }

    @Test
    void testUpdateCompletesMovement() {
        TileMovement movement = mock(TileMovement.class);
        tank.move(MovementDirection.UP, List.of());

        tank.update(movement, 10f);

        assertEquals(new GridPoint2(1, 2), tank.getPosition());
    }
}