package gr.smaca;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventBus;
import gr.smaca.common.state.StateRegistry;
import gr.smaca.common.view.Container;
import gr.smaca.navigation.NavigationApplicationComponent;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;
import gr.smaca.net.NetworkApplicationComponent;
import gr.smaca.net.NetworkEvent;
import gr.smaca.props.PropsApplicationComponent;
import gr.smaca.reader.ReaderApplicationComponent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Smaca extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();
        StateRegistry stateRegistry = new StateRegistry();

        Container container = new Container();
        ApplicationContext context = new ApplicationContext(eventBus, stateRegistry, container);

        List<ApplicationComponent> components = new LinkedList<>();
        components.add(new PropsApplicationComponent());
        components.add(new NetworkApplicationComponent());
        components.add(new ReaderApplicationComponent());
        components.add(new NavigationApplicationComponent());

        for (ApplicationComponent component : components) {
            component.initState(context);
        }

        for (ApplicationComponent component : components) {
            component.initComponent(context);
        }

        eventBus.emit(new NavigationEvent(View.USER));

        Scene scene = new Scene(container, 1200, 800);
        scene.getRoot().requestFocus();
        URL styleSheet = Smaca.class.getResource("/gr/smaca/css/theme/light.css");
        if (styleSheet != null) {
            scene.getStylesheets().add(styleSheet.toExternalForm());
        }

        stage.setOnCloseRequest(event -> eventBus.emit(new NetworkEvent(NetworkEvent.Type.DISCONNECT)));
        stage.setTitle("Smaca");
        stage.setScene(scene);
        stage.show();
    }
}