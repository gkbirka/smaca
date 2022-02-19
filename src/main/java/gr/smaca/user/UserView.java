package gr.smaca.user;

import gr.smaca.common.view.AbstractView;
import gr.smaca.dialog.Dialog;
import gr.smaca.dialog.DialogBuilder;
import gr.smaca.reader.Tag;
import gr.smaca.reader.TagReportEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.List;

public class UserView extends AbstractView {
    private final UserViewModel viewModel;
    @FXML
    private ScrollPane root;

    UserView(UserViewModel viewModel) {
        super("/gr/smaca/fxml/user.fxml");
        this.viewModel = viewModel;
    }

    void handle(TagReportEvent event) {
        List<Tag> tags = event.getTags();

        if (tags.size() == 1) {
            viewModel.getUser(tags.get(0).getEpc());
        } else {
            new DialogBuilder().build(Dialog.MULTIPLE_TAGS_DETECTED, getStage()).showAndWait();
        }
    }

    void handle(UserEvent event) {
        switch (event.getType()) {
            case CONNECTION_ERROR:
                new DialogBuilder().build(Dialog.CONNECTION_ERROR, getStage()).showAndWait();
                break;
            case USER_NOT_FOUND:
                new DialogBuilder().build(Dialog.USER_NOT_FOUND, getStage()).showAndWait();
                break;
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }
}