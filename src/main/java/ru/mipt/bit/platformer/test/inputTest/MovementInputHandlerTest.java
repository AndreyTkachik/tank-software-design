package ru.mipt.bit.platformer.test.inputTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.enums.MovementDirection;
import ru.mipt.bit.platformer.input.MovementInputHandler;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;

import static org.mockito.Mockito.*;

class MovementInputHandlerTest {

    private MovementInputHandler handler;
    private TankModel tank;
    private List<EntityModel> entities;
    private Input originalInput;

    @BeforeEach
    void setUp() {
        handler = new MovementInputHandler();
        tank = mock(TankModel.class);
        entities = List.of();
        originalInput = Gdx.input;
        Gdx.input = mock(Input.class);
    }

    @AfterEach
    void tearDown() {
        Gdx.input = originalInput;
    }

    @Test
    void testHandleInputMoveUp() {
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);

        handler.handleInput(tank, entities);

        verify(tank).move(MovementDirection.UP, entities);
    }

    @Test
    void testHandleInputMoveDown() {
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(true);

        handler.handleInput(tank, entities);

        verify(tank).move(MovementDirection.DOWN, entities);
    }

    @Test
    void testHandleInputMoveLeft() {
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);

        handler.handleInput(tank, entities);

        verify(tank).move(MovementDirection.LEFT, entities);
    }

    @Test
    void testHandleInputMoveRight() {
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);

        handler.handleInput(tank, entities);

        verify(tank).move(MovementDirection.RIGHT, entities);
    }

    @Test
    void testNoKeyPressedDoesNothing() {
        when(Gdx.input.isKeyPressed(anyInt())).thenReturn(false);

        handler.handleInput(tank, entities);

        verifyNoInteractions(tank);
    }
}