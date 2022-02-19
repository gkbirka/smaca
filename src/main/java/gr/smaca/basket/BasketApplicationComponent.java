package gr.smaca.basket;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.database.ConnectionState;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.reader.ReaderEvent;
import gr.smaca.reader.TagReportEvent;

public class BasketApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        ConnectionState connectionState = context.getStateRegistry().getState(ConnectionState.class);

        BasketService service = new BasketService(connectionState.getConnection());
        BasketViewModel viewModel = new BasketViewModel(service, context.getEventBus());
        BasketView view = new BasketView(viewModel);

        EventListener<TagReportEvent> onTagReportEvent = event -> {
            context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.STOP_READING));
            view.handle(event);
        };
        context.getEventBus().subscribe(TagReportEvent.class, onTagReportEvent);

        EventListener<BasketEvent> onBasketEvent = view::handle;
        context.getEventBus().subscribe(BasketEvent.class, onBasketEvent);

        EventListener<NavigationEvent> onNavigationEvent = new EventListener<>() {
            @Override
            public void handle(NavigationEvent event) {
                context.getEventBus().unsubscribe(NavigationEvent.class, this);
                context.getEventBus().unsubscribe(TagReportEvent.class, onTagReportEvent);
                context.getEventBus().unsubscribe(BasketEvent.class, onBasketEvent);
                context.getContainer().setCenter(null);
                viewModel.dispose();

                context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.STOP_READING));
            }
        };
        context.getEventBus().subscribe(NavigationEvent.class, onNavigationEvent);

        context.getContainer().setCenter(view.load());
    }
}