package gr.smaca.user;

import gr.smaca.common.observable.Property;
import gr.smaca.common.state.State;

public class UserState implements State {
    private final Property<User> user = new Property<>();

    public Property<User> userProperty() {
        return user;
    }
}