package gr.smaca.auth;

import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.Dialog;
import gr.smaca.dialog.DialogBuilder;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AuthView extends AbstractView {
    private final AuthViewModel viewModel;
    private int pinMaxLength;
    @FXML
    private ScrollPane root;
    @FXML
    private HBox dots;
    @FXML
    private GridPane numpad;

    AuthView(AuthViewModel viewModel) {
        super("/gr/smaca/fxml/auth.fxml");
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        pinMaxLength = dots.getChildren().size();
        numpad.disableProperty().bind(viewModel.pinProperty().length().isEqualTo(pinMaxLength));
        root.requestFocus();
    }

    @FXML
    private synchronized void handle(Event keyEvent) {
        root.requestFocus();

        StringProperty pin = viewModel.pinProperty();
        if (pin.get().length() == pinMaxLength) {
            return;
        }

        Button key = (Button) keyEvent.getSource();
        pin.set(pin.get() + key.getText());

        fillDot(pin.get().length());

        if (pin.get().length() == pinMaxLength) {
            viewModel.auth();
        }
    }

    private void fillDot(int index) {
        dots.getChildren().get(index - 1).getStyleClass().add("dot-filled");
    }

    @FXML
    private void cancel() {
        clear();
        viewModel.cancel();
    }

    @FXML
    private void clear() {
        root.requestFocus();

        for (Node dot : dots.getChildren()) {
            dot.getStyleClass().remove("dot-filled");
        }

        viewModel.pinProperty().set("");
    }

    void handle(AuthEvent event) {
        clear();
        switch (event.getType()) {
            case CONNECTION_ERROR:
                new DialogBuilder().show(Dialog.CONNECTION_ERROR, getStage());
                break;
            case WRONG_PIN:
                new DialogBuilder().show(Dialog.WRONG_PIN, getStage());
                break;
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }
}