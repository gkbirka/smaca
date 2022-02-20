package gr.smaca.user;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.database.ConnectionState;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;
import gr.smaca.reader.ReaderEvent;
import gr.smaca.reader.TagReportEvent;

public class UserApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
        UserState state = new UserState();
        context.getStateRegistry().register(UserState.class, state);
    }

    @Override
    public void initComponent(ApplicationContext context) {
        ConnectionState connectionState = context.getStateRegistry().getState(ConnectionState.class);
        UserState userState = context.getStateRegistry().getState(UserState.class);

        UserService service = new UserService(connectionState.getConnection());
        UserViewModel viewModel = new UserViewModel(userState, service, context.getEventBus());
        UserView view = new UserView(viewModel);

        EventListener<TagReportEvent> onTagReportEvent = event -> {
            //context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.STOP_READING));
            view.handle(event);

            if (event.getTags().size() > 1) {
                context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.START_READING));
            }
        };
        context.getEventBus().subscribe(TagReportEvent.class, onTagReportEvent);

        EventListener<UserEvent> onUserEvent = new EventListener<>() {
            @Override
            public void handle(UserEvent event) {
                switch (event.getType()) {
                    case CONNECTION_ERROR:
                    case USER_NOT_FOUND:
                        view.handle(event);

                        context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.START_READING));
                        break;
                    case USER_FOUND:
                        context.getEventBus().unsubscribe(UserEvent.class, this);
                        context.getEventBus().unsubscribe(TagReportEvent.class, onTagReportEvent);
                        context.getContainer().setCenter(null);
                        viewModel.dispose();

                        context.getEventBus().emit(new NavigationEvent(View.AUTH));
                        break;
                }
            }
        };
        context.getEventBus().subscribe(UserEvent.class, onUserEvent);

        context.getContainer().setCenter(view.load());
        context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.START_READING));
    }
}