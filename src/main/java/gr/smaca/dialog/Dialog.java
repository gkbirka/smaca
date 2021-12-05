package gr.smaca.dialog;

public enum Dialog {
    WRONG_CREDENTIALS(DialogType.WARNING, null, "dialog.warning.credentials.content");

    private final DialogType dialogType;
    private final String headerTextKey;
    private final String contentTextKey;

    Dialog(DialogType dialogType, String headerTextKey, String contentTextKey) {
        this.dialogType = dialogType;
        this.headerTextKey = headerTextKey;
        this.contentTextKey = contentTextKey;
    }

    public DialogType getDialogType() {
        return dialogType;
    }

    public String getHeaderTextKey() {
        return headerTextKey;
    }

    public String getContentTextKey() {
        return contentTextKey;
    }
}