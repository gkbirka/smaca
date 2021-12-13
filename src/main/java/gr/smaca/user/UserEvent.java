package gr.smaca.user;

import gr.smaca.common.event.Event;

class UserEvent implements Event {
    enum Type {
        CONNECTION_ERROR,
        USER_FOUND,
        USER_NOT_FOUND,
        CANCEL
    }

    private final UserEvent.Type type;
    private final User user;

    UserEvent(Type type, User user) {
        this.type = type;
        this.user = user;
    }

    UserEvent(Type type) {
        this.type = type;
        this.user = null;
    }

    UserEvent.Type getType() {
        return type;
    }

    public User getUser() {
        return user;
    }
}