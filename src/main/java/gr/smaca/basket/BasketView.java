package gr.smaca.basket;

import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.Dialog;
import gr.smaca.dialog.DialogBuilder;
import gr.smaca.reader.TagReportEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class BasketView extends AbstractView {
    private final BasketViewModel viewModel;
    @FXML
    private TableView<Product> products;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, String> category;
    @FXML
    private TableColumn<Product, Double> price;
    @FXML
    private Button scan;
    @FXML
    private Button purchase;

    BasketView(BasketViewModel viewModel) {
        super("/gr/smaca/fxml/basket.fxml");
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        category.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        products.itemsProperty().bind(viewModel.productsProperty());

        scan.setOnAction(event -> viewModel.scan());
        purchase.setOnAction(event -> {
        });//TODO
    }

    void handle(TagReportEvent event) {
        viewModel.getProducts(event.getTags());
    }

    void handle(BasketEvent event) {
        if (event.getType() == BasketEvent.Type.CONNECTION_ERROR) {
            new DialogBuilder().build(Dialog.CONNECTION_ERROR, getStage()).showAndWait();
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) products.getScene().getWindow();
    }
}