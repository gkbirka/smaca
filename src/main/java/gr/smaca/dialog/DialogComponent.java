package gr.smaca.dialog;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;

public class DialogComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        EventListener<DialogEvent> dialogEventListener = event -> event.getDialogBuilder().container(context.getContainer()).show();
        context.getEventBus().subscribe(DialogEvent.class, dialogEventListener);
    }
}