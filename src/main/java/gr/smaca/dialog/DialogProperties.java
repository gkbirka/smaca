package gr.smaca.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ResourceBundle;

class DialogProperties {
    private final Alert.AlertType type;
    private final String headerText;
    private final String contentText;
    private static final String BUNDLE_NAME = "gr.smaca.i18n.bundle";

    DialogProperties(Alert.AlertType type, String headerTextKey, String contentTextKey) {
        this.type = type;
        this.headerText = getBundleString(headerTextKey);
        this.contentText = getBundleString(contentTextKey);
    }

    DialogProperties(Alert.AlertType type, String contentTextKey) {
        this.type = type;
        this.headerText = null;
        this.contentText = getBundleString(contentTextKey);
    }

    Alert.AlertType getType() {
        return type;
    }

    String getHeaderText() {
        return headerText;
    }

    String getContentText() {
        return contentText;
    }

    Label getGraphic() {
        Label iconHolder = new Label();
        iconHolder.getStyleClass().add("dialog-icon-holder");

        String styleClass = "";
        switch (type) {
            case INFORMATION:
                styleClass = "icon-information";
                break;
            case WARNING:
                styleClass = "icon-warning";
                break;
            case CONFIRMATION:
                styleClass = "icon-confirmation";
                break;
            case ERROR:
                styleClass = "icon-error";
                break;
        }
        FontIcon icon = new FontIcon();
        icon.getStyleClass().add(styleClass);

        iconHolder.setGraphic(icon);
        return iconHolder;
    }

    ObservableList<ButtonType> getButtons() {
        ObservableList<ButtonType> buttons = FXCollections.observableArrayList();
        buttons.add(new ButtonType(getBundleString("dialog.button.ok"), ButtonBar.ButtonData.OK_DONE));

        if (type == Alert.AlertType.CONFIRMATION) {
            buttons.add(new ButtonType(getBundleString("dialog.button.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE));
        }

        return buttons;
    }

    String getBundleString(String key) {
        return ResourceBundle.getBundle(BUNDLE_NAME).getString(key);
    }
}