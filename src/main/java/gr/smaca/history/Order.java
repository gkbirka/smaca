package gr.smaca.history;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;

class Order {
    private final int id;
    private final ObjectProperty<LocalDateTime> date;
    private final DoubleProperty total;

    Order(int id, LocalDateTime date, double total) {
        this.id = id;
        this.date = new SimpleObjectProperty<>(date);
        this.total = new SimpleDoubleProperty(total);
    }

    int getId() {
        return id;
    }

    ObjectProperty<LocalDateTime> dateProperty() {
        return date;
    }

    DoubleProperty totalProperty() {
        return total;
    }
}