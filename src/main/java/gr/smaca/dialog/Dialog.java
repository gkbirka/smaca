package gr.smaca.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public enum Dialog {
    CONNECTION_ERROR(new DialogProperties(
            Alert.AlertType.ERROR,
            "dialog.error.connection.content")),

    MULTIPLE_TAGS_DETECTED(new DialogProperties(
            Alert.AlertType.WARNING,
            "dialog.warning.tags.header",
            "dialog.warning.tags.content")),

    USER_NOT_FOUND(new DialogProperties(
            Alert.AlertType.WARNING,
            "dialog.warning.user.content")),

    WRONG_PIN(new DialogProperties(
            Alert.AlertType.WARNING,
            "dialog.warning.pin.content")),

    PURCHASE_COMPLETED(new DialogProperties(
            Alert.AlertType.INFORMATION,
            "dialog.info.purchase.content")),

    PINS_DO_NOT_MATCH(new DialogProperties(
            Alert.AlertType.WARNING,
            "dialog.warning.match.content")),

    PIN_CHANGED(new DialogProperties(
            Alert.AlertType.INFORMATION,
            "dialog.info.pin.content")),

    CONFIRM_CLOSE(new DialogProperties(
            Alert.AlertType.CONFIRMATION,
            "dialog.confirmation.close.content") {
        @Override
        protected ObservableList<ButtonType> getButtons() {
            return FXCollections.observableArrayList(
                    new ButtonType(getBundleString("dialog.button.exit"), ButtonBar.ButtonData.OK_DONE),
                    new ButtonType(getBundleString("dialog.button.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE));
        }
    });

    private final DialogProperties properties;

    Dialog(DialogProperties properties) {
        this.properties = properties;
    }

    DialogProperties getProperties() {
        return properties;
    }
}