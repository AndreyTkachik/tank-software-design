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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mipt.bit.platformer.config.AppConfig;
import ru.mipt.bit.platformer.input.*;
import ru.mipt.bit.platformer.input.action.*;
import ru.mipt.bit.platformer.level.LevelEntitiesData;
import ru.mipt.bit.platformer.level.LevelLoader;
import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.TankView;
import ru.mipt.bit.platformer.view.TreeView;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private static final String LOADER_PROFILE = "random";
    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;

    private TankModel tankModel;
    private List<EntityModel> entityModels = new ArrayList<>();

    private List<TankModel> aiTanks = new ArrayList<>();
    private final List<InputHandler> aiHandlers = new ArrayList<>();

    private InputHandler inputHandler;

    private AnnotationConfigApplicationContext context;

    @Override
    public void create() {
        batch = new SpriteBatch();

        initAppContext();

        TiledMapTileLayer groundLayer = loadLevelTiles();

        loadLevelEntities(groundLayer);

        loadInputHandler();

        loadAIInputHandler();
    }

    private void initAppContext() {
        AnnotationConfigApplicationContext contextTmp = new AnnotationConfigApplicationContext();
        contextTmp.getEnvironment().setActiveProfiles(LOADER_PROFILE);
        contextTmp.register(AppConfig.class);
        contextTmp.refresh();
        context = contextTmp;
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
        inputHandler = context.getBean("inputHandler", InputHandler.class);
    }

    private void loadAIInputHandler() {
        for (int i = 0; i < aiTanks.size(); i++) {
            aiHandlers.add(context.getBean(AIInputHandlerImpl.class));
        }
    }

    private void loadLevelEntities(TiledMapTileLayer groundLayer) {
        TankView tankView = new TankView("images/tank_blue.png");
        TreeView treeView = new TreeView("images/greenTree.png");

        LevelLoader loader = context.getBean(LevelLoader.class);

        LevelEntitiesData levelEntitiesData = loader.loadLevel(groundLayer, tankView, treeView);

        tankModel = levelEntitiesData.getTank();
        aiTanks = levelEntitiesData.getAiTanks();
        entityModels = new ArrayList<>(levelEntitiesData.getAllEntities());
        entityModels.add(tankModel);
    }

    private TiledMapTileLayer loadLevelTiles() {
        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        return getSingleLayer(level);
    }

    private void handleInputAndRenderIt(float deltaTime) {
        inputHandler.handleInput(tankModel, entityModels);
        // render each tile of the level
        for (int i = 0; i < aiTanks.size(); i++) {
            aiHandlers.get(i).handleInput(aiTanks.get(i), entityModels);
        }
        tankModel.update(new TileMovement(getSingleLayer(level), Interpolation.smooth), deltaTime);
        aiTanks.forEach(tank -> tank.update(new TileMovement(getSingleLayer(level), Interpolation.smooth), deltaTime));
        updateBulletState();
        levelRenderer.render();
    }

    private void updateBulletState() {
        List<EntityModel> copy = new ArrayList<>(entityModels);
        do {
            for (EntityModel e : entityModels) {
                if (e instanceof BulletModel) {
                    ((BulletModel) e).update(entityModels);
                }
                if (!entityModels.equals(copy)) {
                    copy = new ArrayList<>(entityModels);
                    break;
                }
            }
        } while (!entityModels.equals(copy));
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
