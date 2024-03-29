package gr.smaca;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventBus;
import gr.smaca.common.state.StateRegistry;
import gr.smaca.common.view.Container;
import gr.smaca.config.ConfigApplicationComponent;
import gr.smaca.database.DatabaseApplicationComponent;
import gr.smaca.database.DatabaseEvent;
import gr.smaca.dialog.Dialog;
import gr.smaca.dialog.DialogBuilder;
import gr.smaca.navigation.NavigationApplicationComponent;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;
import gr.smaca.reader.ReaderApplicationComponent;
import gr.smaca.reader.ReaderEvent;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Smaca extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        Font.loadFont(getClass().getResourceAsStream("/gr/smaca/font/Manrope-Medium.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/gr/smaca/font/Manrope-Bold.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/gr/smaca/font/Manrope-ExtraBold.ttf"), 14);
    }

    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();
        StateRegistry stateRegistry = new StateRegistry();

        Container container = new Container();
        ApplicationContext context = new ApplicationContext(eventBus, stateRegistry, container);

        List<ApplicationComponent> components = new LinkedList<>();
        components.add(new ConfigApplicationComponent());
        components.add(new ReaderApplicationComponent());
        components.add(new DatabaseApplicationComponent());
        components.add(new NavigationApplicationComponent());

        for (ApplicationComponent component : components) {
            component.initState(context);
        }

        for (ApplicationComponent component : components) {
            component.initComponent(context);
        }

        Rectangle2D windowDimensions = computeWindowDimensions();
        Scene scene = new Scene(container, windowDimensions.getMaxX(), windowDimensions.getMaxY());
        URL styleSheet = Smaca.class.getResource("/gr/smaca/css/theme/light.css");
        if (styleSheet != null) {
            scene.getStylesheets().add(styleSheet.toExternalForm());
        }

        eventBus.emit(new NavigationEvent(View.USER));

        stage.setOnCloseRequest(event -> {
            if (confirmClose(stage)) {

                eventBus.emit(new ReaderEvent(ReaderEvent.Type.DISCONNECT));
                eventBus.emit(new DatabaseEvent(DatabaseEvent.Type.DISCONNECT));
            } else {
                event.consume();
            }
        });
        stage.setTitle("Smaca");
        stage.setScene(scene);
        stage.show();
    }

    private static Rectangle2D computeWindowDimensions() {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        boolean lowResolution = visualBounds.getMaxX() < 1024;

        return lowResolution
                ? new Rectangle2D(0, 0, (int) visualBounds.getMaxX() / 1.1, (int) visualBounds.getMaxY() / 1.1)
                : new Rectangle2D(0, 0, (int) visualBounds.getMaxX() / 1.2, (int) visualBounds.getMaxY() / 1.2);
    }

    private static boolean confirmClose(Stage stage) {
        return DialogBuilder.show(Dialog.CONFIRM_CLOSE, stage);
    }
}