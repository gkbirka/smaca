package gr.smaca.basket;

import gr.smaca.common.event.Event;

import java.util.List;

class BasketEvent implements Event {
    enum Type {
        CONNECTION_ERROR,
        PRODUCTS_FOUND,
    }

    private final BasketEvent.Type type;
    private final List<Product> products;

    BasketEvent(Type type, List<Product> products) {
        this.type = type;
        this.products = products;
    }

    BasketEvent(Type type) {
        this.type = type;
        this.products = null;
    }

    Type getType() {
        return type;
    }

    List<Product> getProducts() {
        return products;
    }
}