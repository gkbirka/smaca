package gr.smaca.basket;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class Product {
    private final String epc;
    private final StringProperty name;
    private final StringProperty category;
    private final DoubleProperty price;

    Product(String epc, String name, String category, Double price) {
        this.epc = epc;
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.price = new SimpleDoubleProperty(price);
    }

    String getEpc() {
        return epc;
    }

    StringProperty nameProperty() {
        return name;
    }

    StringProperty categoryProperty() {
        return category;
    }

    DoubleProperty priceProperty() {
        return price;
    }
}