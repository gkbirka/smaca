package gr.smaca.database;

import gr.smaca.common.event.Event;

public class DatabaseEvent implements Event {
    public enum Type {
        CONNECT,
        DISCONNECT
    }

    private final Type type;

    public DatabaseEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}