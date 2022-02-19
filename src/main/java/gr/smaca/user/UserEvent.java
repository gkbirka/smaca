package gr.smaca.user;

import gr.smaca.common.event.Event;

class UserEvent implements Event {
    enum Type {
        CONNECTION_ERROR,
        USER_FOUND,
        USER_NOT_FOUND
    }

    private final UserEvent.Type type;

    UserEvent(Type type) {
        this.type = type;
    }

    UserEvent.Type getType() {
        return type;
    }
}