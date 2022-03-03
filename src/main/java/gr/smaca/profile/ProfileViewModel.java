package gr.smaca.profile;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.lifecycle.AbstractViewModel;
import gr.smaca.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

class ProfileViewModel extends AbstractViewModel {
    private final User user;
    private final ProfileService service;
    private final EventBus eventBus;

    private final StringProperty currentPin = new SimpleStringProperty("");
    private final StringProperty newPin = new SimpleStringProperty("");
    private final StringProperty newPinConfirmation = new SimpleStringProperty("");

    ProfileViewModel(User user, ProfileService service, EventBus eventBus) {
        super(true);
        this.user = user;
        this.service = service;
        this.eventBus = eventBus;
    }

    void save() {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return service.updatePin(user.getEpc(), currentPin.get(), newPin.get());
            }

            @Override
            protected void succeeded() {
                boolean updated = this.getValue();

                ProfileEvent event = new ProfileEvent(updated ? ProfileEvent.Type.PIN_CHANGED : ProfileEvent.Type.WRONG_PIN);

                eventBus.emit(event);
            }

            @Override
            protected void failed() {
                this.getException().printStackTrace();

                eventBus.emit(new ProfileEvent(ProfileEvent.Type.CONNECTION_ERROR));
            }
        };

        execute(task);
    }

    User getUser() {
        return user;
    }

    StringProperty currentPinProperty() {
        return currentPin;
    }

    StringProperty newPinProperty() {
        return newPin;
    }

    StringProperty newPinConfirmationProperty() {
        return newPinConfirmation;
    }
}