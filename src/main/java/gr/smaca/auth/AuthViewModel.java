package gr.smaca.auth;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.view.ViewModel;
import gr.smaca.user.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

class AuthViewModel implements ViewModel {
    private final EventBus eventBus;
    private final AuthService authService;
    private final SimpleObjectProperty<User> user = new SimpleObjectProperty<>();
    private final SimpleStringProperty pin = new SimpleStringProperty("");
    private final Service<AuthEvent> taskService = new Service<>() {
        @Override
        protected Task<AuthEvent> createTask() {
            return authTask();
        }
    };

    AuthViewModel(EventBus eventBus, AuthService authService) {
        this.eventBus = eventBus;
        this.authService = authService;
    }

    private Task<AuthEvent> authTask() {
        return new Task<>() {
            @Override
            protected AuthEvent call() {
                return authService.auth(user.get().getEpc(), pin.get());
            }

            @Override
            protected void succeeded() {
                eventBus.emit(this.getValue());
            }

            @Override
            protected void failed() {
                eventBus.emit(new AuthEvent(AuthEvent.Type.CONNECTION_ERROR));
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

    SimpleObjectProperty<User> userProperty() {
        return user;
    }

    SimpleStringProperty pinProperty() {
        return pin;
    }

    @Override
    public void dispose() {
        if (taskService.isRunning()) {
            taskService.cancel();
        }
    }
}