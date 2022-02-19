package gr.smaca.auth;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.lifecycle.ViewModel;
import gr.smaca.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

class AuthViewModel implements ViewModel {
    private final User user;
    private final AuthService authService;
    private final EventBus eventBus;

    private final StringProperty pin = new SimpleStringProperty("");
    private final Service<AuthEvent> taskService = new Service<>() {
        @Override
        protected Task<AuthEvent> createTask() {
            return authTask();
        }
    };

    AuthViewModel(User user, AuthService authService, EventBus eventBus) {
        this.user = user;
        this.authService = authService;
        this.eventBus = eventBus;
    }

    private Task<AuthEvent> authTask() {
        return new Task<>() {
            @Override
            protected AuthEvent call() {
                return authService.auth(user.getEpc(), pin.get());
            }

            @Override
            protected void succeeded() {
                eventBus.emit(this.getValue());
            }
        };
    }

    void auth() {
        if (!taskService.isRunning()) {
            taskService.reset();
            taskService.start();
        }
    }

    void cancel() {
        eventBus.emit(new AuthEvent(AuthEvent.Type.CANCEL));
    }

    StringProperty pinProperty() {
        return pin;
    }

    @Override
    public void dispose() {
        if (taskService.isRunning()) {
            taskService.cancel();
        }
    }
}