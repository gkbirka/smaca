package gr.smaca.reader;

import gr.smaca.common.event.Event;

public class ReaderEvent implements Event {
    public enum Type {
        START_READING,
        STOP_READING,
        DISCONNECT
    }

    private final Type type;

    public ReaderEvent(Type type) {
        this.type = type;
    }

    Type getType() {
        return type;
    }
}