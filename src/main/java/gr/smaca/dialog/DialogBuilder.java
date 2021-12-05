package gr.smaca.dialog;

import gr.smaca.common.layout.Container;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.ResourceBundle;

class DialogBuilder {
    private Container container;
    private DialogType dialogType;
    private String headerText;
    private String contentText;
    private static final String BUNDLE_NAME = "gr.smaca.i18n.bundle";

    DialogBuilder container(Container container) {
        if (container == null) {
            throw new IllegalStateException("Container required.");
        }
        this.container = container;
        return this;
    }

    private void setDialogType(DialogType dialogType) {
        if (dialogType == null) {
            throw new IllegalStateException("Dialog type required.");
        }
        this.dialogType = dialogType;
    }

    private void setHeaderText(String headerTextKey) {
        String headerText = null;
        if (headerTextKey != null) {
            headerText = ResourceBundle.getBundle(BUNDLE_NAME).getString(headerTextKey);
        }
        this.headerText = headerText;
    }

    private void setContentText(String contentTextKey) {
        String contentText = null;
        if (contentTextKey != null) {
            contentText = ResourceBundle.getBundle(BUNDLE_NAME).getString(contentTextKey);
        }
        this.contentText = contentText;
    }

    DialogBuilder build(Dialog dialog) {
        setDialogType(dialog.getDialogType());
        setHeaderText(dialog.getHeaderTextKey());
        setContentText(dialog.getContentTextKey());
        return this;
    }

    Alert build() {
        if (dialogType == null) {
            throw new IllegalStateException("Dialog type required.");
        }

        final Alert alert = new Alert(getType());
        alert.setTitle(((Stage) container.getScene().getWindow()).getTitle());
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initOwner(container.getScene().getWindow());
        return alert;
    }

    private Alert.AlertType getType() {
        switch (dialogType) {
            case INFORMATION:
                return Alert.AlertType.INFORMATION;
            case WARNING:
                return Alert.AlertType.WARNING;
            case CONFIRMATION:
                return Alert.AlertType.CONFIRMATION;
            case ERROR:
                return Alert.AlertType.ERROR;
            default:
                throw new IllegalStateException("Dialog type required.");
        }
    }

    void show() {
        build().showAndWait();
    }
}