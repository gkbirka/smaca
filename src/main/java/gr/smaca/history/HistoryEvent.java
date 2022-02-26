package gr.smaca.history;

import gr.smaca.common.event.Event;

class HistoryEvent implements Event {
    enum Type {
        CONNECTION_ERROR,
        ORDER_DETAILS_FOUND
    }

    private final HistoryEvent.Type type;

    HistoryEvent(Type type) {
        this.type = type;
    }

    Type getType() {
        return type;
    }
}