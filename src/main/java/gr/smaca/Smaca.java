package gr.smaca;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventBus;
import gr.smaca.common.layout.Container;
import gr.smaca.common.state.StateRegistry;
import gr.smaca.dialog.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        components.add(new DialogComponent());

        for (ApplicationComponent component : components) {
            component.initState(context);
        }

        for (ApplicationComponent component : components) {
            component.initComponent(context);
        }

        Scene scene = new Scene(container, 1200, 800);

        stage.setTitle("Smaca");
        stage.setScene(scene);
        stage.show();
    }
}