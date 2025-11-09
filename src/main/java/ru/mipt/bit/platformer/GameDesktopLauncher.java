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
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.input.*;
import ru.mipt.bit.platformer.input.action.*;
import ru.mipt.bit.platformer.level.FileLevelLoader;
import ru.mipt.bit.platformer.level.LevelEntitiesData;
import ru.mipt.bit.platformer.level.LevelLoader;
import ru.mipt.bit.platformer.level.RandomLevelLoader;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.TankView;
import ru.mipt.bit.platformer.view.TreeView;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
    private static final boolean RANDOM_LOADER_ENABLE = true;
    private static final int AI_TANK_COUNT = 3;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private TankModel tankModel;
    private List<EntityModel> entityModels = new ArrayList<>();

    private List<TankModel> aiTanks = new ArrayList<>();
    private final List<InputHandler> aiHandlers = new ArrayList<>();

    private InputHandler inputHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();

        TiledMapTileLayer groundLayer = loadLevelTiles();

        loadLevelEntities(groundLayer);

        loadInputHandler();

        loadAIInputHandler();
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
                com.badlogic.gdx.Input.Keys.D, new MoveRight(),
                com.badlogic.gdx.Input.Keys.L, new ActivateHealth()
        );

        inputHandler = new InputHandlerImpl(inputProvider, actions);
    }

    private void loadAIInputHandler() {
        for (int i = 0; i < aiTanks.size(); i++) {
            aiHandlers.add(new AIInputHandlerImpl());
        }
    }

    private void loadLevelEntities(TiledMapTileLayer groundLayer) {
        TankView tankView = new TankView("images/tank_blue.png");
        TreeView treeView = new TreeView("images/greenTree.png");

        LevelLoader loader = getLoader(groundLayer, tankView, treeView);

        LevelEntitiesData levelEntitiesData = loader.loadLevel();

        tankModel = levelEntitiesData.getTank();
        aiTanks = levelEntitiesData.getAiTanks();
        entityModels = new ArrayList<>(levelEntitiesData.getAllEntities());
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

    private LevelLoader getLoader(TiledMapTileLayer groundLayer, TankView tankView, TreeView treeView) {
        LevelLoader loader;

        if (RANDOM_LOADER_ENABLE) {
            loader = new RandomLevelLoader(
                    10, 8, 10,
                    AI_TANK_COUNT,
                    MOVEMENT_SPEED,
                    groundLayer,
                    tankView,
                    treeView
            );
        } else {
            loader = new FileLevelLoader(
                    Path.of("src/main/resources/levels/test_level"),
                    MOVEMENT_SPEED,
                    groundLayer,
                    tankView,
                    treeView
            );
        }
        return loader;
    }

    private void handleInputAndRenderIt(float deltaTime) {
        inputHandler.handleInput(tankModel, entityModels);
        // render each tile of the level
        for (int i = 0; i < aiTanks.size(); i++) {
            aiHandlers.get(i).handleInput(aiTanks.get(i), entityModels);
        }
        tankModel.update(tileMovement, deltaTime);
        aiTanks.forEach(tank -> tank.update(tileMovement, deltaTime));
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
