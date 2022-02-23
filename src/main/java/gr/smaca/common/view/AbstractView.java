package gr.smaca.common.view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public abstract class AbstractView {
    private static final String BUNDLE_NAME = "gr.smaca.i18n.bundle";
    private final String location;

    public AbstractView(String location) {
        this.location = location;
    }

    public final Parent load() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(location),
                ResourceBundle.getBundle(BUNDLE_NAME),
                new JavaFXBuilderFactory(),
                controller -> this);

        try {
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();

            StackPane error = new StackPane(new Label("Error loading view: " + getClass().getSimpleName()));
            error.getStyleClass().add("container-primary");
            return error;
        }
    }

    protected abstract Stage getStage();
}