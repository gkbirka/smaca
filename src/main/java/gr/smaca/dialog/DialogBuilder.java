package gr.smaca.dialog;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;

public class DialogBuilder {
    public Alert build(Dialog dialog, Stage owner) {
        DialogProperties properties = dialog.getProperties();

        Alert alert = new Alert(properties.getType());
        alert.setTitle(owner.getTitle());

        alert.setHeaderText(properties.getHeaderText());
        alert.setContentText(properties.getContentText());

        ObservableList<ButtonType> buttons = properties.getButtons();
        if (buttons != null) {
            alert.getButtonTypes().setAll(buttons);
        }

        alert.setResultConverter(button -> {
            switch (button.getButtonData()) {
                case OK_DONE:
                    return ButtonType.OK;
                case CANCEL_CLOSE:
                default:
                    return ButtonType.CANCEL;
            }
        });

        alert.initOwner(owner);
        alert.setOnShown(event -> owner.getScene().getRoot().setEffect(new GaussianBlur(15)));
        alert.setOnHidden(event -> owner.getScene().getRoot().setEffect(null));

        return alert;
    }
}