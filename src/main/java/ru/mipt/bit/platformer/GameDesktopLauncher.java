package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.input.*;
import ru.mipt.bit.platformer.input.action.MoveDown;
import ru.mipt.bit.platformer.input.action.MoveLeft;
import ru.mipt.bit.platformer.input.action.MoveRight;
import ru.mipt.bit.platformer.input.action.MoveUp;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.TankView;
import ru.mipt.bit.platformer.view.TreeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private TankModel tankModel;
    private final List<EntityModel> entityModels = new ArrayList<>();

    private InputHandler inputHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();

        TiledMapTileLayer groundLayer = loadLevelTiles();

        loadEntities(groundLayer);

        loadInputHandler();
    }

    @Override
    public void render() {
        float deltaTime = getDeltaTimeAndRenderGdxColors();

        handleInputAndRenderIt(deltaTime);

        recordDrawingCommands();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        for (EntityModel e : entityModels) {
            e.getView().dispose();
        }
        level.dispose();
        batch.dispose();
    }

    private void loadInputHandler() {
        InputProvider inputProvider = new GdxInputProvider();
        Map<Integer, Action> actions = Map.of(
                com.badlogic.gdx.Input.Keys.W, new MoveUp(),
                com.badlogic.gdx.Input.Keys.S, new MoveDown(),
                com.badlogic.gdx.Input.Keys.A, new MoveLeft(),
                com.badlogic.gdx.Input.Keys.D, new MoveRight()
        );

        inputHandler = new InputHandlerImpl(inputProvider, actions);
    }

    private void loadEntities(TiledMapTileLayer groundLayer) {
        tankModel = new TankModel(
                new GridPoint2(1, 1), 0f, groundLayer, MOVEMENT_SPEED,
                new TankView("images/tank_blue.png")
        );

        TreeModel treeModel = new TreeModel(
                new GridPoint2(1, 3), 0f, groundLayer,
                new TreeView("images/greenTree.png")
        );

        entityModels.add(treeModel);
        entityModels.add(tankModel);
    }

    private TiledMapTileLayer loadLevelTiles() {
        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        return groundLayer;
    }

    private void handleInputAndRenderIt(float deltaTime) {
        inputHandler.handleInput(tankModel, entityModels);
        // render each tile of the level
        tankModel.update(tileMovement, deltaTime);
        levelRenderer.render();
    }

    private static float getDeltaTimeAndRenderGdxColors() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        // get time passed since the last render
        return Gdx.graphics.getDeltaTime();
    }

    private void recordDrawingCommands() {
        // start recording all drawing commands
        batch.begin();
        // render player and tree obstacle
        for (EntityModel e: entityModels) {
            e.getView().render(batch, e.getPosition(), e.getRotation(), e.getLayer());
        }
        // submit all drawing requests
        batch.end();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
