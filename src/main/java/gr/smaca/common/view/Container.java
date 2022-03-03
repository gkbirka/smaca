package gr.smaca.common.view;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class Container extends BorderPane {

    public Container() {
        initialize();
    }

    private void initialize() {
        ChangeListener<Node> onNodeChanged = (observable, oldNode, newNode) -> requestFocus();

        centerProperty().addListener(onNodeChanged);
        topProperty().addListener(onNodeChanged);
        bottomProperty().addListener(onNodeChanged);
        leftProperty().addListener(onNodeChanged);
        rightProperty().addListener(onNodeChanged);
    }
}