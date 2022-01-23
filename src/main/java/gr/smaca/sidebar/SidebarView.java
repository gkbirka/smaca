package gr.smaca.sidebar;

import gr.smaca.common.view.AbstractView;
import gr.smaca.navigation.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SidebarView extends AbstractView {
    private final SidebarViewModel viewModel;
    @FXML
    private ScrollPane root;
    @FXML
    private Button basket;
    @FXML
    private Button history;
    @FXML
    private Button profile;
    @FXML
    private Button disconnect;

    public SidebarView(SidebarViewModel viewModel) {
        super("/gr/smaca/fxml/sidebar.fxml");
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        basket.setOnAction(event -> navigate(View.BASKET));
        basket.setOnTouchPressed(event -> navigate(View.BASKET));

        history.setOnAction(event -> navigate(View.HISTORY));
        history.setOnTouchPressed(event -> navigate(View.HISTORY));

        profile.setOnAction(event -> navigate(View.PROFILE));
        profile.setOnTouchPressed(event -> navigate(View.PROFILE));

        disconnect.setOnAction(event -> viewModel.disconnect());
        disconnect.setOnTouchPressed(event -> viewModel.disconnect());
    }

    private void navigate(View view) {
        switch (view) {
            case BASKET:
                select(basket);
                break;
            case HISTORY:
                select(history);
                break;
            case PROFILE:
                select(profile);
                break;
        }

        viewModel.navigate(view);
    }

    private void select(Button pressed) {
        String styleClass = "button-sidebar-selected";

        for (Node button : ((VBox) root.getContent()).getChildren()) {
            button.getStyleClass().remove(styleClass);
        }

        pressed.getStyleClass().add(styleClass);
    }

    @Override
    protected Stage getStage() {
        return null;
    }
}