package gr.smaca.user;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.lifecycle.AbstractViewModel;
import gr.smaca.reader.Tag;
import javafx.concurrent.Task;

class UserViewModel extends AbstractViewModel {
    private final UserState state;
    private final UserService service;
    private final EventBus eventBus;

    UserViewModel(UserState state, UserService service, EventBus eventBus) {
        super(true);
        this.state = state;
        this.service = service;
        this.eventBus = eventBus;
    }

    void getUser(Tag tag) {
        Task<User> task = new Task<>() {
            @Override
            protected User call() throws Exception {
                return service.getUser(tag.getEpc());
            }

            @Override
            protected void succeeded() {
                User user = this.getValue();
                state.setUser(user);

                if (user != null) {
                    eventBus.emit(new UserEvent(UserEvent.Type.USER_FOUND));
                } else {
                    eventBus.emit(new UserEvent(UserEvent.Type.USER_NOT_FOUND));
                }
            }

            @Override
            protected void failed() {
                this.getException().printStackTrace();

                eventBus.emit(new UserEvent(UserEvent.Type.CONNECTION_ERROR));
            }
        };

        execute(task);
    }
}