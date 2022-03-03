package gr.smaca.profile;

import gr.smaca.common.event.Event;

class ProfileEvent implements Event {
    enum Type {
        CONNECTION_ERROR,
        PIN_CHANGED,
        WRONG_PIN
    }

    private final Type type;

    ProfileEvent(Type type) {
        this.type = type;
    }

    Type getType() {
        return type;
    }
}