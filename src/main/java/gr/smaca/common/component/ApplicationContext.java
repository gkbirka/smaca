package gr.smaca.common.component;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.view.Container;
import gr.smaca.common.state.StateRegistry;

public class ApplicationContext {
    private final EventBus eventBus;
    private final StateRegistry stateRegistry;
    private final Container container;

    public ApplicationContext(EventBus eventBus, StateRegistry stateRegistry, Container container) {
        this.eventBus = eventBus;
        this.stateRegistry = stateRegistry;
        this.container = container;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public StateRegistry getStateRegistry() {
        return stateRegistry;
    }

    public Container getContainer() {
        return container;
    }
}