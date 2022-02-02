package gr.smaca.user;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.view.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

class UserViewModel implements ViewModel {
    private final UserState state;
    private final UserService userService;
    private final EventBus eventBus;

    private final StringProperty epc = new SimpleStringProperty("");
    private final Service<UserEvent> taskService = new Service<>() {
        @Override
        protected Task<UserEvent> createTask() {
            return userTask();
        }
    };

    UserViewModel(UserState state, UserService userService, EventBus eventBus) {
        this.state = state;
        this.userService = userService;
        this.eventBus = eventBus;
    }

    private Task<UserEvent> userTask() {
        return new Task<>() {
            @Override
            protected UserEvent call() {
                return userService.getUser(epc.get());
            }

            @Override
            protected void succeeded() {
                UserEvent event = this.getValue();
                state.setUser(event.getUser());
                eventBus.emit(event);
            }
        };
    }

    void getUser() {
        if (!taskService.isRunning()) {
            taskService.reset();
            taskService.start();
        }
    }

    StringProperty epcProperty() {
        return epc;
    }

    @Override
    public void dispose() {
        if (taskService.isRunning()) {
            taskService.cancel();
        }
    }
}