package gr.smaca.history;

import gr.smaca.basket.Product;
import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.Dialog;
import gr.smaca.dialog.DialogBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryView extends AbstractView {
    private static final String CURRENCY_SYMBOL = "€";
    private final HistoryViewModel viewModel;
    @FXML
    private GridPane root;
    @FXML
    private TableView<Order> orders;
    @FXML
    private TableColumn<Order, LocalDateTime> date;
    @FXML
    private TableColumn<Order, Double> total;
    @FXML
    private TableView<Product> products;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, String> category;
    @FXML
    private TableColumn<Product, Double> price;

    HistoryView(HistoryViewModel viewModel) {
        super("/gr/smaca/fxml/history.fxml");
        this.viewModel = viewModel;
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void initialize() {
        date.setCellValueFactory(data -> data.getValue().dateProperty());
        total.setCellValueFactory(data -> data.getValue().totalProperty().asObject());

        date.setCellFactory(column -> formattedDate());
        total.setCellFactory(column -> (TableCell<Order, Double>) priceWithCurrency());

        orders.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldOrder, newOrder) -> orders.setDisable(true));
        viewModel.selectedOrderProperty().bind(orders.getSelectionModel().selectedItemProperty());

        orders.itemsProperty().bind(viewModel.ordersProperty());
        orders.getColumns().forEach(column -> {
            column.setReorderable(false);
            column.setSortable(false);
        });

        name.setCellValueFactory(data -> data.getValue().nameProperty());
        category.setCellValueFactory(data -> data.getValue().categoryProperty());
        price.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        price.setCellFactory(column -> (TableCell<Product, Double>) priceWithCurrency());

        products.itemsProperty().bind(viewModel.productsProperty());
        products.getColumns().forEach(column -> {
            column.setReorderable(false);
            column.setSortable(false);
        });

        viewModel.loadOrders();
    }

    private TableCell<Order, LocalDateTime> formattedDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

        return new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty ? null : dateFormatter.format(date));
            }
        };
    }

    private TableCell<?, Double> priceWithCurrency() {
        return new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                setText(empty ? null : String.format("%.2f " + CURRENCY_SYMBOL, price));
            }
        };
    }

    void handle(HistoryEvent event) {
        if (event.getType() == HistoryEvent.Type.CONNECTION_ERROR) {
            DialogBuilder.show(Dialog.CONNECTION_ERROR, getStage());
        }

        orders.setDisable(false);
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }
}