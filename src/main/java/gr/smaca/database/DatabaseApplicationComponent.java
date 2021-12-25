package gr.smaca.database;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.config.Config;
import gr.smaca.config.ConfigState;

public class DatabaseApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
        ConnectionState state = new ConnectionState();
        context.getStateRegistry().register(ConnectionState.class, state);
    }

    @Override
    public void initComponent(ApplicationContext context) {
        ConfigState configState = context.getStateRegistry().getState(ConfigState.class);
        Config config = configState.configProperty().get();
        ConnectionState connectionState = context.getStateRegistry().getState(ConnectionState.class);

        DatabaseManager databaseManager = new DatabaseManager(connectionState, config);

        EventListener<DatabaseEvent> eventListener = new EventListener<>() {
            @Override
            public void handle(DatabaseEvent event) {
                databaseManager.handle(event);

                if (event.getType() == DatabaseEvent.Type.DISCONNECT) {
                    context.getEventBus().unsubscribe(DatabaseEvent.class, this);
                    context.getStateRegistry().unregister(ConnectionState.class);
                }
            }
        };
        context.getEventBus().subscribe(DatabaseEvent.class, eventListener);

        context.getEventBus().emit(new DatabaseEvent(DatabaseEvent.Type.CONNECT));
    }
}