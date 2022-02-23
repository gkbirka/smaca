package gr.smaca.basket;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.lifecycle.AbstractViewModel;
import gr.smaca.reader.ReaderEvent;
import gr.smaca.reader.Tag;
import gr.smaca.user.User;
import javafx.beans.binding.ListBinding;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.List;
import java.util.stream.Collectors;

class BasketViewModel extends AbstractViewModel {
    private final User user;
    private final BasketService service;
    private final EventBus eventBus;

    private final ObservableListValue<Product> products = new ListBinding<>() {
        @Override
        protected ObservableList<Product> computeValue() {
            return FXCollections.observableArrayList();
        }
    };

    BasketViewModel(User user, BasketService service, EventBus eventBus) {
        super(true);
        this.user = user;
        this.service = service;
        this.eventBus = eventBus;
    }

    void scan() {
        eventBus.emit(new ReaderEvent(ReaderEvent.Type.SCAN));
    }

    void getProducts(List<Tag> tags) {
        Task<List<Product>> task = new Task<>() {
            @Override
            protected List<Product> call() throws Exception {
                return service.getProducts(tags.stream()
                        .map(Tag::getEpc)
                        .collect(Collectors.toList()));
            }

            @Override
            protected void succeeded() {
                products.setAll(this.getValue());
                eventBus.emit(new BasketEvent(BasketEvent.Type.PRODUCTS_FOUND));
            }

            @Override
            protected void failed() {
                eventBus.emit(new BasketEvent(BasketEvent.Type.CONNECTION_ERROR));
            }
        };

        execute(task);
    }

    void purchase() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                service.purchaseProducts(user.getEpc(), products);
                return null;
            }

            @Override
            protected void succeeded() {
                eventBus.emit(new BasketEvent(BasketEvent.Type.PURCHASE_COMPLETED));
            }

            @Override
            protected void failed() {
                eventBus.emit(new BasketEvent(BasketEvent.Type.CONNECTION_ERROR));
            }
        };

        execute(task);
    }

    ObservableListValue<Product> productsProperty() {
        return products;
    }
}