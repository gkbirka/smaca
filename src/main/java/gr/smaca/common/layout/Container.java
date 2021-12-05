package gr.smaca.common.layout;

import javafx.scene.control.TabPane;

public class Container extends TabPane {
    public Container() {
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }
}