package gr.smaca.profile;

import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.Dialog;
import gr.smaca.dialog.DialogBuilder;
import gr.smaca.user.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public class ProfileView extends AbstractView {
    private static final int PIN_LENGTH = 4;
    private final ProfileViewModel viewModel;
    @FXML
    private GridPane root;
    @FXML
    private Label name;
    @FXML
    private Label epc;
    @FXML
    private PasswordField currentPin;
    @FXML
    private PasswordField newPin;
    @FXML
    private PasswordField newPinConfirmation;
    private BooleanBinding pinsMatch;
    @FXML
    private Button save;

    ProfileView(ProfileViewModel viewModel) {
        super("/gr/smaca/fxml/profile.fxml");
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        User user = viewModel.getUser();
        name.setText(user.getFirstName() + " " + user.getLastName());
        epc.setText(user.getEpc());

        currentPin.textProperty().bindBidirectional(viewModel.currentPinProperty());
        newPin.textProperty().bindBidirectional(viewModel.newPinProperty());
        newPinConfirmation.textProperty().bindBidirectional(viewModel.newPinConfirmationProperty());

        currentPin.setTextFormatter(pinFormatter());
        newPin.setTextFormatter(pinFormatter());
        newPinConfirmation.setTextFormatter(pinFormatter());

        pinsMatch = newPin.textProperty().isEqualTo(newPinConfirmation.textProperty());

        save.disableProperty().bind(Bindings.or(viewModel.currentPinProperty().length().isNotEqualTo(PIN_LENGTH), Bindings.or(
                viewModel.newPinProperty().length().isNotEqualTo(PIN_LENGTH),
                viewModel.newPinConfirmationProperty().length().isNotEqualTo(PIN_LENGTH))));
    }

    @FXML
    private void save() {
        if (pinsMatch.get()) {
            viewModel.save();
        } else {
            new DialogBuilder().show(Dialog.PINS_DO_NOT_MATCH, getStage());
        }
    }

    private TextFormatter<?> pinFormatter() {
        UnaryOperator<TextFormatter.Change> pinFilter = change -> {
            String newText = change.getControlNewText();

            return newText.length() <= PIN_LENGTH
                    && newText.matches("^[0-9]*$")
                    ? change : null;
        };

        return new TextFormatter<>(new IntegerStringConverter(), null, pinFilter);
    }

    void handle(ProfileEvent event) {
        switch (event.getType()) {
            case CONNECTION_ERROR:
                new DialogBuilder().show(Dialog.CONNECTION_ERROR, getStage());
                break;
            case PIN_CHANGED:
                new DialogBuilder().show(Dialog.PIN_CHANGED, getStage());
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