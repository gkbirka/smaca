package gr.smaca.navigation;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;

public class NavigationApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
        NavigationState state = new NavigationState();
        context.getStateRegistry().register(NavigationState.class, state);
    }

    @Override
    public void initComponent(ApplicationContext context) {
        NavigationState state = context.getStateRegistry().getState(NavigationState.class);
        NavigationManager navigationManager = new NavigationManager(context);

        EventListener<NavigationEvent> onNavigationEvent = event -> {
            if (event.getView() != state.getView()) {
                state.setView(event.getView());
                navigationManager.handle(event);
            }
        };

        context.getEventBus().subscribe(NavigationEvent.class, onNavigationEvent);
    }
}