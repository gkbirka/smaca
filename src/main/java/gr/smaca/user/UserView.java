package gr.smaca.user;

import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.Dialog;
import gr.smaca.dialog.DialogBuilder;
import gr.smaca.reader.Tag;
import gr.smaca.reader.TagReportEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.List;

public class UserView extends AbstractView {
    private final UserViewModel viewModel;
    @FXML
    private ScrollPane root;
    @FXML
    private Button scan;

    UserView(UserViewModel viewModel) {
        super("/gr/smaca/fxml/user.fxml");
        this.viewModel = viewModel;
    }

    @FXML
    private void scan() {
        scan.setDisable(true);
        viewModel.scan();
    }

    void handle(TagReportEvent event) {
        List<Tag> tags = event.getTags();

        if (tags.size() == 1) {
            viewModel.loadUser(tags.get(0));
        } else {
            DialogBuilder.show(Dialog.MULTIPLE_TAGS_DETECTED, getStage());
        }
    }

    void handle(UserEvent event) {
        switch (event.getType()) {
            case CONNECTION_ERROR:
                DialogBuilder.show(Dialog.CONNECTION_ERROR, getStage());
                break;
            case USER_NOT_FOUND:
                DialogBuilder.show(Dialog.USER_NOT_FOUND, getStage());
                break;
        }

        scan.setDisable(false);
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }
}