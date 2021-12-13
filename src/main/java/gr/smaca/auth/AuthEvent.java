package gr.smaca.auth;

import gr.smaca.common.event.Event;

public class AuthEvent implements Event {
    public enum Type {
        CONNECTION_ERROR,
        WRONG_PIN,
        CONNECT,
        DISCONNECT,
        CANCEL
    }

    private final Type type;

    public AuthEvent(Type type) {
        this.type = type;
    }

    Type getType() {
        return type;
    }
}