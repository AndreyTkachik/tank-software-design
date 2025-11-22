package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.Input;
import org.springframework.context.annotation.*;
import ru.mipt.bit.platformer.input.Action;
import ru.mipt.bit.platformer.input.action.*;
import ru.mipt.bit.platformer.level.FileLevelLoader;
import ru.mipt.bit.platformer.level.LevelLoader;
import ru.mipt.bit.platformer.level.RandomLevelLoader;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("ru.mipt.bit.platformer")
public class AppConfig {

    @Bean
    public Map<Integer, Action> keyActions(
            MoveUp moveUp,
            MoveDown moveDown,
            MoveLeft moveLeft,
            MoveRight moveRight,
            ActivateHealth activateHealth,
            Shoot shoot) {

        Map<Integer, Action> actions = new HashMap<>();
        actions.put(Input.Keys.W, moveUp);
        actions.put(Input.Keys.S, moveDown);
        actions.put(Input.Keys.A, moveLeft);
        actions.put(Input.Keys.D, moveRight);
        actions.put(Input.Keys.L, activateHealth);
        actions.put(Input.Keys.SPACE, shoot);
        return actions;
    }

    @Bean
    @Profile("!random")
    public LevelLoader fileLevelLoader() {
        return new FileLevelLoader(Path.of("src/main/resources/levels/test_level"), 0.4f);
    }

    @Bean
    @Profile("random")
    public LevelLoader randomLevelLoader() {
        return new RandomLevelLoader(10, 8, 10, 3, 0.4f);
    }
}