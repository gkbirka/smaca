package gr.smaca.basket;

import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.Dialog;
import gr.smaca.dialog.DialogBuilder;
import gr.smaca.reader.TagReportEvent;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BasketView extends AbstractView implements Initializable {
    private final BasketViewModel viewModel;
    @FXML
    private GridPane root;
    @FXML
    private TableView<Product> products;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, String> category;
    @FXML
    private TableColumn<Product, Double> price;
    @FXML
    private Label totalProducts;
    @FXML
    private Label totalCost;
    @FXML
    private Button scan;
    @FXML
    private Button purchase;

    BasketView(BasketViewModel viewModel) {
        super("/gr/smaca/fxml/basket.fxml");
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL path, ResourceBundle bundle) {
        name.setCellValueFactory(data -> data.getValue().nameProperty());
        category.setCellValueFactory(data -> data.getValue().categoryProperty());
        price.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        products.setPlaceholder(new Label(bundle.getString("basket.table.placeholder")));
        products.itemsProperty().bind(viewModel.productsProperty());
        products.getColumns().forEach(column -> {
            column.setReorderable(false);
            column.setSortable(false);
        });

        totalProducts.textProperty().bind(Bindings.size(viewModel.productsProperty()).asString());
        totalCost.textProperty().bind(Bindings.createDoubleBinding(() -> products.getItems()
                .stream()
                .mapToDouble(Product::getPrice).sum(), products.getItems()).asString().concat(" €"));

        purchase.setDisable(true);
    }

    @FXML
    private void scan() {
        scan.setDisable(true);
        viewModel.scan();
    }

    @FXML
    private void purchase() {
        purchase.setDisable(true);
        viewModel.purchase();
    }

    void handle(TagReportEvent event) {
        viewModel.getProducts(event.getTags());
    }

    void handle(BasketEvent event) {
        switch (event.getType()) {
            case CONNECTION_ERROR:
                new DialogBuilder().build(Dialog.CONNECTION_ERROR, getStage()).showAndWait();
                scan.setDisable(false);
                if (viewModel.productsProperty().size() > 0) {
                    purchase.setDisable(false);
                }
                break;
            case PRODUCTS_FOUND:
                scan.setDisable(false);
                purchase.setDisable(false);
                break;
            case PURCHASE_COMPLETED:
                viewModel.productsProperty().clear();
                new DialogBuilder().build(Dialog.PURCHASE_COMPLETED, getStage()).showAndWait();
                break;

        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }
}