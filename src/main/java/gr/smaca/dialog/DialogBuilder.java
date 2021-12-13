package gr.smaca.dialog;

import javafx.scene.control.Alert;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class DialogBuilder {
    private static final String BUNDLE_NAME = "gr.smaca.i18n.bundle";

    public DialogBuilder() {
    }

    public Alert build(DialogTemplate template, Stage owner) {
        Alert alert = new Alert(template.getType());
        alert.setTitle(owner.getTitle());

        String headerText = null;
        if (template.getHeaderTextKey() != null) {
            headerText = ResourceBundle.getBundle(BUNDLE_NAME).getString(template.getHeaderTextKey());
        }
        alert.setHeaderText(headerText);

        String contentText = null;
        if (template.getContentTextKey() != null) {
            contentText = ResourceBundle.getBundle(BUNDLE_NAME).getString(template.getContentTextKey());
        }
        alert.setContentText(contentText);

        alert.initOwner(owner);
        alert.setOnShown(event -> owner.getScene().getRoot().setEffect(new GaussianBlur(15)));
        alert.setOnHidden(event -> owner.getScene().getRoot().setEffect(null));

        return alert;
    }
}