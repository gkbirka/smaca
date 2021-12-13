package gr.smaca.user;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
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
        UserState state = context.getStateRegistry().getState(UserState.class);

        UserService service = new UserService();
        UserViewModel viewModel = new UserViewModel(context.getEventBus(), state, service);
        UserView view = new UserView(viewModel);

        EventListener<TagReportEvent> tagReportEventListener = view::handle;
        context.getEventBus().subscribe(TagReportEvent.class, tagReportEventListener);

        EventListener<UserEvent> userEventListener = new EventListener<>() {
            @Override
            public void handle(UserEvent event) {
                switch (event.getType()) {
                    case CONNECTION_ERROR:
                    case USER_NOT_FOUND:
                        context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.STOP_READING));
                        view.handle(event);
                        context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.START_READING));
                        break;
                    case USER_FOUND:
                        context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.STOP_READING));
                        context.getEventBus().unsubscribe(UserEvent.class, this);
                        viewModel.dispose();

                        context.getEventBus().emit(new NavigationEvent(View.AUTH));
                        break;
                    case CANCEL:
                        context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.START_READING));
                        state.userProperty().set(null);

                        view.handle(event);
                        break;
                }
            }
        };
        context.getEventBus().subscribe(UserEvent.class, userEventListener);

        context.getContainer().setLeft(null);
        context.getContainer().setCenter(view.load());
        context.getEventBus().emit(new ReaderEvent(ReaderEvent.Type.START_READING));
    }
}