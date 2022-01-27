package gr.smaca.auth;

import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.Dialog;
import gr.smaca.dialog.DialogBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AuthView extends AbstractView {
    private static final int PIN_MAX_LENGTH = 4;
    private final AuthViewModel viewModel;
    @FXML
    private ScrollPane root;
    @FXML
    private HBox dotBox;
    @FXML
    private GridPane numpad;
    @FXML
    private Button cancel;
    @FXML
    private Button clear;

    AuthView(AuthViewModel viewModel) {
        super("/gr/smaca/fxml/auth.fxml");
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        numpad.disableProperty().bind(viewModel.pinProperty().length().isEqualTo(PIN_MAX_LENGTH));
        cancel.setOnAction(event -> cancel());
        clear.setOnAction(event -> clear());
    }

    @FXML
    private void handle(Event keyEvent) {
        root.requestFocus();

        SimpleStringProperty pin = viewModel.pinProperty();
        if (pin.get().length() >= PIN_MAX_LENGTH) {
            return;
        }

        Button key = (Button) keyEvent.getSource();
        pin.set(pin.get() + key.getText());

        fillDot(pin.get().length());

        if (pin.get().length() == PIN_MAX_LENGTH) {
            viewModel.auth();
        }
    }

    private void fillDot(int length) {
        dotBox.getChildren().get(length - 1).getStyleClass().add("dot-filled");
    }

    private void cancel() {
        clear();
        viewModel.cancel();
    }

    private void clear() {
        root.requestFocus();

        for (Node dot : dotBox.getChildren()) {
            dot.getStyleClass().remove("dot-filled");
        }

        viewModel.pinProperty().set("");
    }

    void handle(AuthEvent event) {
        clear();
        switch (event.getType()) {
            case CONNECTION_ERROR:
                new DialogBuilder().build(Dialog.CONNECTION_ERROR, getStage()).showAndWait();
                break;
            case WRONG_PIN:
                new DialogBuilder().build(Dialog.WRONG_PIN, getStage()).showAndWait();
                break;
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }
}