package gr.smaca.dialog;

import gr.smaca.common.event.Event;

public class DialogEvent implements Event {
    private final DialogBuilder dialogBuilder;
    public DialogEvent(Dialog dialog) {
        this.dialogBuilder = new DialogBuilder().build(dialog);
    }

    DialogBuilder getDialogBuilder() {
        return dialogBuilder;
    }
}