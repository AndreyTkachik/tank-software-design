package ru.mipt.bit.platformer.input.action;

import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.input.Action;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.view.EntityView;
import ru.mipt.bit.platformer.view.decorator.HealthDecorator;

import java.util.List;

@Component
public class ActivateHealth implements Action {
    private boolean enabled = false;

    @Override
    public void execute(TankModel tank, List<EntityModel> entities) {
        enabled = !enabled;

        for (EntityModel entity : entities) {
            EntityView view = entity.getView();

            if (enabled && !(view instanceof HealthDecorator)) {
                entity.setView(new HealthDecorator(view, entity));
            } else if (!enabled && view instanceof HealthDecorator) {
                entity.setView(((HealthDecorator) view).getWrappedView());
            }
        }
    }
}