package gr.smaca.basket;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.database.ConnectionState;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.reader.ReaderEvent;
import gr.smaca.reader.TagReportEvent;

import java.sql.Connection;

public class BasketApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        ConnectionState connectionState = context.getStateRegistry().getState(ConnectionState.class);
        Connection connection = connectionState.getConnection();

        BasketService service = new BasketService(connection);
        BasketViewModel viewModel = new BasketViewModel(service, context.getEventBus());
        BasketView view = new BasketView(viewModel);

        EventListener<TagReportEvent> tagReportEventListener = event -> {
            context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.STOP_READING));
            view.handle(event);
        };
        context.getEventBus().subscribe(TagReportEvent.class, tagReportEventListener);

        EventListener<BasketEvent> basketEventListener = view::handle;
        context.getEventBus().subscribe(BasketEvent.class, basketEventListener);

        EventListener<NavigationEvent> navigationEventListener = new EventListener<>() {
            @Override
            public void handle(NavigationEvent event) {
                context.getEventBus().unsubscribe(TagReportEvent.class, tagReportEventListener);
                context.getEventBus().unsubscribe(BasketEvent.class, basketEventListener);
                context.getEventBus().unsubscribe(NavigationEvent.class, this);
            }
        };
        context.getEventBus().subscribe(NavigationEvent.class, navigationEventListener);

        context.getContainer().setCenter(view.load());
    }
}