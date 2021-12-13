package gr.smaca.user;

import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.DialogBuilder;
import gr.smaca.dialog.DialogTemplate;
import gr.smaca.reader.TagReportEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class UserView extends AbstractView {
    private final UserViewModel viewModel;
    @FXML
    private ScrollPane root;

    UserView(UserViewModel viewModel) {
        super("/gr/smaca/fxml/user.fxml");
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {

    }

    void handle(TagReportEvent event) {
        if (event.getTags().size() == 1) {
            String epc = event.getTags().get(0).getEpc().toString();
            viewModel.epcProperty().set(epc);
            viewModel.getUser();
        } else {
            new DialogBuilder().build(DialogTemplate.MULTIPLE_TAGS_DETECTED, getStage()).showAndWait();
        }
    }

    void handle(UserEvent event) {
        switch (event.getType()) {
            case CONNECTION_ERROR:
                new DialogBuilder().build(DialogTemplate.CONNECTION_ERROR, getStage()).showAndWait();
                break;
            case USER_FOUND:
                break;
            case USER_NOT_FOUND:
                new DialogBuilder().build(DialogTemplate.USER_NOT_FOUND, getStage()).showAndWait();
                break;
            case CANCEL:
                break;
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }
}