package gr.smaca.auth;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.lifecycle.AbstractViewModel;
import gr.smaca.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

class AuthViewModel extends AbstractViewModel {
    private final User user;
    private final AuthService service;
    private final EventBus eventBus;

    private final StringProperty pin = new SimpleStringProperty("");

    AuthViewModel(User user, AuthService service, EventBus eventBus) {
        super(true);
        this.user = user;
        this.service = service;
        this.eventBus = eventBus;
    }

    void auth() {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return service.auth(user.getEpc(), pin.get());
            }

            @Override
            protected void succeeded() {
                boolean authed = this.getValue();

                AuthEvent event = new AuthEvent(authed ? AuthEvent.Type.CONNECT : AuthEvent.Type.WRONG_PIN);

                eventBus.emit(event);
            }

            @Override
            protected void failed() {
                eventBus.emit(new AuthEvent(AuthEvent.Type.CONNECTION_ERROR));
            }
        };

        execute(task);
    }

    void cancel() {
        eventBus.emit(new AuthEvent(AuthEvent.Type.CANCEL));
    }

    StringProperty pinProperty() {
        return pin;
    }
}