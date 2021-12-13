package gr.smaca.auth;

import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.DialogBuilder;
import gr.smaca.dialog.DialogTemplate;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AuthView extends AbstractView {
    private final AuthViewModel viewModel;
    @FXML
    private ScrollPane root;
    @FXML
    private GridPane numpad;
    @FXML
    private Button firstDot;
    @FXML
    private Button secondDot;
    @FXML
    private Button thirdDot;
    @FXML
    private Button fourthDot;
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
        numpad.disableProperty().bind(viewModel.pinProperty().length().isEqualTo(4));
        cancel.setOnAction(event -> cancel());
        clear.setOnAction(event -> clear());
    }

    @FXML
    private void handle(Event keyEvent) {
        root.requestFocus();

        SimpleStringProperty pin = viewModel.pinProperty();
        if (pin.get().length() >= 4) {
            return;
        }

        Button key = (Button) keyEvent.getSource();
        pin.set(pin.get() + key.getText());

        fillDot(pin.get().length());

        if (pin.get().length() == 4) {
            viewModel.auth();
        }
    }

    private void fillDot(int length) {
        String styleClass = "dot-filled";
        switch (length) {
            case 1:
                firstDot.getStyleClass().add(styleClass);
                break;
            case 2:
                secondDot.getStyleClass().add(styleClass);
                break;
            case 3:
                thirdDot.getStyleClass().add(styleClass);
                break;
            case 4:
                fourthDot.getStyleClass().add(styleClass);
                break;
        }
    }

    private void cancel() {
        clear();
        viewModel.cancel();
    }

    private void clear() {
        root.requestFocus();

        String styleClass = "dot-filled";
        firstDot.getStyleClass().remove(styleClass);
        secondDot.getStyleClass().remove(styleClass);
        thirdDot.getStyleClass().remove(styleClass);
        fourthDot.getStyleClass().remove(styleClass);

        viewModel.pinProperty().set("");
    }

    void handle(AuthEvent event) {
        switch (event.getType()) {
            case CONNECTION_ERROR:
                clear();
                new DialogBuilder().build(DialogTemplate.CONNECTION_ERROR, getStage()).showAndWait();
                break;
            case WRONG_PIN:
                clear();
                new DialogBuilder().build(DialogTemplate.WRONG_PIN, getStage()).showAndWait();
                break;
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }
}