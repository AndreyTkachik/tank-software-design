package ru.mipt.bit.platformer.test.inputTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.enums.MovementDirection;
import ru.mipt.bit.platformer.input.Action;
import ru.mipt.bit.platformer.input.InputHandlerImpl;
import ru.mipt.bit.platformer.input.InputProvider;
import ru.mipt.bit.platformer.input.action.MoveDown;
import ru.mipt.bit.platformer.input.action.MoveLeft;
import ru.mipt.bit.platformer.input.action.MoveRight;
import ru.mipt.bit.platformer.input.action.MoveUp;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class MovementInputHandlerTest {

    private InputProvider inputProvider;
    private TankModel tank;
    private List<EntityModel> entities;
    private InputHandlerImpl handler;

    @BeforeEach
    void setUp() {
        inputProvider = mock(InputProvider.class);
        tank = mock(TankModel.class);
        entities = List.of();

        Map<Integer, Action> actions = Map.of(
                com.badlogic.gdx.Input.Keys.W, new MoveUp(),
                com.badlogic.gdx.Input.Keys.S, new MoveDown(),
                com.badlogic.gdx.Input.Keys.A, new MoveLeft(),
                com.badlogic.gdx.Input.Keys.D, new MoveRight()
        );

        handler = new InputHandlerImpl(inputProvider, actions);
    }

    @Test
    void testHandleInputMoveUp() {
        when(inputProvider.isKeyPressed(com.badlogic.gdx.Input.Keys.W)).thenReturn(true);

        handler.handleInput(tank, entities);

        verify(tank).move(MovementDirection.UP, entities);
    }

    @Test
    void testHandleInputMoveDown() {
        when(inputProvider.isKeyPressed(com.badlogic.gdx.Input.Keys.S)).thenReturn(true);

        handler.handleInput(tank, entities);

        verify(tank).move(MovementDirection.DOWN, entities);
    }

    @Test
    void testHandleInputMoveLeft() {
        when(inputProvider.isKeyPressed(com.badlogic.gdx.Input.Keys.A)).thenReturn(true);

        handler.handleInput(tank, entities);

        verify(tank).move(MovementDirection.LEFT, entities);
    }

    @Test
    void testHandleInputMoveRight() {
        when(inputProvider.isKeyPressed(com.badlogic.gdx.Input.Keys.D)).thenReturn(true);

        handler.handleInput(tank, entities);

        verify(tank).move(MovementDirection.RIGHT, entities);
    }

    @Test
    void testNoKeyPressedDoesNothing() {
        when(inputProvider.isKeyPressed(anyInt())).thenReturn(false);

        handler.handleInput(tank, entities);

        verifyNoInteractions(tank);
    }
}