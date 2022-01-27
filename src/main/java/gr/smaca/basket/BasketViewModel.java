package gr.smaca.basket;

import gr.smaca.common.event.EventBus;
import gr.smaca.common.view.ViewModel;
import gr.smaca.reader.ReaderEvent;
import gr.smaca.reader.Tag;
import javafx.beans.binding.ListBinding;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

class BasketViewModel implements ViewModel {
    private final BasketService basketService;
    private final EventBus eventBus;

    private final ObservableListValue<Product> products = new ListBinding<>() {
        @Override
        protected ObservableList<Product> computeValue() {
            return FXCollections.observableArrayList();
        }
    };
    private Service<BasketEvent> taskService = new Service<>() {
        @Override
        protected Task<BasketEvent> createTask() {
            return null;
        }
    };

    BasketViewModel(BasketService basketService, EventBus eventBus) {
        this.basketService = basketService;
        this.eventBus = eventBus;
    }

    private Task<BasketEvent> productsTask(List<Tag> tags) {
        return new Task<>() {
            @Override
            protected BasketEvent call() {
                return basketService.getProducts(tags);
            }

            @Override
            protected void succeeded() {
                BasketEvent event = this.getValue();
                products.setAll(event.getProducts());
                eventBus.emit(event);
            }
        };
    }

    void readTags(){
        eventBus.emit(new ReaderEvent(ReaderEvent.Type.START_READING));
    }

    void getProducts(List<Tag> tags) {
        if (!taskService.isRunning()) {
            taskService = new Service<>() {
                @Override
                protected Task<BasketEvent> createTask() {
                    return productsTask(tags);
                }
            };
            taskService.start();
        }
    }

    ObservableListValue<Product> productsProperty() {
        return products;
    }

    @Override
    public void dispose() {
        if (taskService.isRunning()) {
            taskService.cancel();
        }
    }
}