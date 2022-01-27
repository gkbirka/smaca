package gr.smaca.basket;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private final String epc;
    private final StringProperty name;
    private final StringProperty category;
    private final DoubleProperty price;

    public Product(String epc, String name, String category, Double price) {
        this.epc = epc;
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getEpc() {
        return epc;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public DoubleProperty priceProperty() {
        return price;
    }
}