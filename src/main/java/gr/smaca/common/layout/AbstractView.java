package gr.smaca.common.layout;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

import java.util.ResourceBundle;

public abstract class AbstractView {
    private static final String BUNDLE_NAME = "gr.smaca.i18n.bundle";
    private final String viewPath;

    public AbstractView(String viewPath) {
        this.viewPath = viewPath;
    }

    public final Tab load() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(viewPath),
                ResourceBundle.getBundle(BUNDLE_NAME),
                new JavaFXBuilderFactory(),
                controller -> this);

        try {
            return loader.load();
        } catch (Exception ignored) {
        }

        Tab errorTab = new Tab();
        errorTab.setContent(new StackPane(new Label("Error loading view: " + getClass().getSimpleName())));
        return errorTab;
    }
}