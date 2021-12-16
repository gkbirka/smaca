package gr.smaca.user;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.view.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

class UserViewModel implements ViewModel {
    private final EventBus eventBus;
    private final UserState state;
    private final UserService userService;
    private final SimpleStringProperty epc = new SimpleStringProperty("");
    private final Service<UserEvent> taskService = new Service<>() {
        @Override
        protected Task<UserEvent> createTask() {
            return userTask();
        }
    };

    UserViewModel(EventBus eventBus, UserState state, UserService userService) {
        this.eventBus = eventBus;
        this.state = state;
        this.userService = userService;
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
                state.userProperty().set(event.getUser());
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

    SimpleStringProperty epcProperty() {
        return epc;
    }

    @Override
    public void dispose() {
        if (taskService.isRunning()) {
            taskService.cancel();
        }
    }
}