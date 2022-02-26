package gr.smaca.history;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.database.ConnectionState;
import gr.smaca.database.DatabaseEvent;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;
import gr.smaca.user.UserState;

public class HistoryApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {}

    @Override
    public void initComponent(ApplicationContext context) {
        ConnectionState connectionState = context.getStateRegistry().getState(ConnectionState.class);
        UserState userState = context.getStateRegistry().getState(UserState.class);

        HistoryService service = new HistoryService(connectionState.getConnection());
        HistoryViewModel viewModel = new HistoryViewModel(userState.getUser(), service, context.getEventBus());
        HistoryView view = new HistoryView(viewModel);

        EventListener<HistoryEvent> onHistoryEvent = view::handle;
        context.getEventBus().subscribe(HistoryEvent.class, onHistoryEvent);

        EventListener<DatabaseEvent> onDatabaseEvent = event -> viewModel.dispose();
        context.getEventBus().subscribe(DatabaseEvent.class, onDatabaseEvent);

        EventListener<NavigationEvent> onNavigationEvent = new EventListener<>() {
            @Override
            public void handle(NavigationEvent event) {
                if (event.getView() != View.HISTORY) {
                    context.getEventBus().unsubscribe(NavigationEvent.class, this);
                    context.getEventBus().unsubscribe(HistoryEvent.class, onHistoryEvent);
                    context.getEventBus().unsubscribe(DatabaseEvent.class, onDatabaseEvent);
                    context.getContainer().setCenter(null);
                    viewModel.dispose();
                }
            }
        };
        context.getEventBus().subscribe(NavigationEvent.class, onNavigationEvent);

        context.getContainer().setCenter(view.load());
    }
}