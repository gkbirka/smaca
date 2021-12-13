package gr.smaca.dialog;

import javafx.scene.control.Alert;

public enum DialogTemplate {
    CONNECTION_ERROR(Alert.AlertType.ERROR, null, "dialog.error.connection.content"),
    MULTIPLE_TAGS_DETECTED(Alert.AlertType.WARNING, "dialog.warning.tags.header", "dialog.warning.tags.content"),
    USER_NOT_FOUND(Alert.AlertType.WARNING, null, "dialog.warning.user.content"),
    WRONG_PIN(Alert.AlertType.WARNING, null, "dialog.warning.pin.content");

    private final Alert.AlertType type;
    private final String headerTextKey;
    private final String contentTextKey;

    DialogTemplate(Alert.AlertType type, String headerTextKey, String contentTextKey) {
        this.type = type;
        this.headerTextKey = headerTextKey;
        this.contentTextKey = contentTextKey;
    }

    public Alert.AlertType getType() {
        return type;
    }

    public String getHeaderTextKey() {
        return headerTextKey;
    }

    public String getContentTextKey() {
        return contentTextKey;
    }
}