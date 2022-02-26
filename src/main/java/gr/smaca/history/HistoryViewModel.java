package gr.smaca.history;

import gr.smaca.basket.Product;
import gr.smaca.common.event.EventBus;
import gr.smaca.common.lifecycle.AbstractViewModel;
import gr.smaca.user.User;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.List;

class HistoryViewModel extends AbstractViewModel {
    private final User user;
    private final HistoryService service;
    private final EventBus eventBus;

    private final ObjectProperty<Order> selectedOrder = new SimpleObjectProperty<>();
    private final ObservableListValue<Order> orders = new ListBinding<>() {
        @Override
        protected ObservableList<Order> computeValue() {
            return FXCollections.observableArrayList();
        }
    };
    private final ObservableListValue<Product> products = new ListBinding<>() {
        @Override
        protected ObservableList<Product> computeValue() {
            return FXCollections.observableArrayList();
        }
    };

    HistoryViewModel(User user, HistoryService service, EventBus eventBus) {
        super(true);
        this.user = user;
        this.service = service;
        this.eventBus = eventBus;
        this.selectedOrder.addListener((observable, oldOrder, newOrder) -> {
            products.clear();
            if (newOrder != null) {
                loadOrderDetails();
            }
        });
    }

    void loadOrders() {
        Task<List<Order>> task = new Task<>() {
            @Override
            protected List<Order> call() throws Exception {
                return service.getOrders(user.getEpc());
            }

            @Override
            protected void succeeded() {
                orders.setAll(this.getValue());
            }

            @Override
            protected void failed() {
                this.getException().printStackTrace();

                eventBus.emit(new HistoryEvent(HistoryEvent.Type.CONNECTION_ERROR));
            }
        };

        execute(task);
    }

    private void loadOrderDetails() {
        Task<List<Product>> task = new Task<>() {
            @Override
            protected List<Product> call() throws Exception {
                return service.getProducts(selectedOrder.get().getId());
            }

            @Override
            protected void succeeded() {
                products.setAll(this.getValue());
                eventBus.emit(new HistoryEvent(HistoryEvent.Type.ORDER_DETAILS_FOUND));
            }

            @Override
            protected void failed() {
                this.getException().printStackTrace();

                eventBus.emit(new HistoryEvent(HistoryEvent.Type.CONNECTION_ERROR));
            }
        };

        execute(task);
    }

    ObjectProperty<Order> selectedOrderProperty() {
        return selectedOrder;
    }

    ObservableListValue<Order> ordersProperty() {
        return orders;
    }

    ObservableListValue<Product> productsProperty() {
        return products;
    }
}