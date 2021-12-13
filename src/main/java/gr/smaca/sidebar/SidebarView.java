package gr.smaca.sidebar;

import gr.smaca.common.view.AbstractView;
import gr.smaca.navigation.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class SidebarView extends AbstractView {
    private final SidebarViewModel viewModel;
    @FXML
    private Button basket;
    @FXML
    private FontIcon basketIcon;
    @FXML
    private Button history;
    @FXML
    private FontIcon historyIcon;
    @FXML
    private Button profile;
    @FXML
    private FontIcon profileIcon;
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
        String buttonStyleClass = "button-sidebar-selected";
        String iconStyleClass = "icon-sidebar-selected";
        switch (view) {
            case BASKET:
                basket.getStyleClass().add(buttonStyleClass);
                basketIcon.getStyleClass().add(iconStyleClass);

                history.getStyleClass().remove(buttonStyleClass);
                historyIcon.getStyleClass().remove(iconStyleClass);

                profile.getStyleClass().remove(buttonStyleClass);
                profileIcon.getStyleClass().remove(iconStyleClass);

                viewModel.navigate(View.BASKET);
                break;
            case HISTORY:
                history.getStyleClass().add(buttonStyleClass);
                historyIcon.getStyleClass().add(iconStyleClass);

                basket.getStyleClass().remove(buttonStyleClass);
                basketIcon.getStyleClass().remove(iconStyleClass);

                profile.getStyleClass().remove(buttonStyleClass);
                profileIcon.getStyleClass().remove(iconStyleClass);

                viewModel.navigate(View.HISTORY);
                break;
            case PROFILE:
                profile.getStyleClass().add(buttonStyleClass);
                profileIcon.getStyleClass().add(iconStyleClass);

                basket.getStyleClass().remove(buttonStyleClass);
                basketIcon.getStyleClass().remove(iconStyleClass);

                history.getStyleClass().remove(buttonStyleClass);
                historyIcon.getStyleClass().remove(iconStyleClass);

                viewModel.navigate(View.PROFILE);
                break;
        }
    }

    @Override
    protected Stage getStage() {
        return null;
    }
}