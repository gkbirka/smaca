package gr.smaca.basket;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.database.ConnectionState;
import gr.smaca.database.DatabaseEvent;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;
import gr.smaca.reader.TagReportEvent;
import gr.smaca.user.UserState;

public class BasketApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {}

    @Override
    public void initComponent(ApplicationContext context) {
        ConnectionState connectionState = context.getStateRegistry().getState(ConnectionState.class);
        UserState userState = context.getStateRegistry().getState(UserState.class);

        BasketService service = new BasketService(connectionState.getConnection());
        BasketViewModel viewModel = new BasketViewModel(userState.getUser(), service, context.getEventBus());
        BasketView view = new BasketView(viewModel);

        EventListener<TagReportEvent> onTagReportEvent = view::handle;
        context.getEventBus().subscribe(TagReportEvent.class, onTagReportEvent);

        EventListener<BasketEvent> onBasketEvent = view::handle;
        context.getEventBus().subscribe(BasketEvent.class, onBasketEvent);

        EventListener<DatabaseEvent> onDatabaseEvent = event -> viewModel.dispose();
        context.getEventBus().subscribe(DatabaseEvent.class, onDatabaseEvent);

        EventListener<NavigationEvent> onNavigationEvent = new EventListener<>() {
            @Override
            public void handle(NavigationEvent event) {
                if (event.getView() != View.BASKET) {
                    context.getEventBus().unsubscribe(NavigationEvent.class, this);
                    context.getEventBus().unsubscribe(TagReportEvent.class, onTagReportEvent);
                    context.getEventBus().unsubscribe(BasketEvent.class, onBasketEvent);
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