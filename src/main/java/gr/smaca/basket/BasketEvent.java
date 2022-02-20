package gr.smaca.basket;

import gr.smaca.common.event.Event;

class BasketEvent implements Event {
    enum Type {
        CONNECTION_ERROR
    }

    private final BasketEvent.Type type;

    BasketEvent(Type type) {
        this.type = type;
    }

    Type getType() {
        return type;
    }
}