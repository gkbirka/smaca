package gr.smaca.sidebar;

import gr.smaca.auth.AuthEvent;
import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;

public class SidebarApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        SidebarViewModel viewModel = new SidebarViewModel(context.getEventBus());
        SidebarView view = new SidebarView(viewModel);

        EventListener<AuthEvent> eventListener = new EventListener<>() {
            @Override
            public void handle(AuthEvent event) {
                if (event.getType() == AuthEvent.Type.DISCONNECT) {
                    context.getContainer().setLeft(null);
                    context.getEventBus().unsubscribe(AuthEvent.class, this);
                }
            }
        };
        context.getEventBus().subscribe(AuthEvent.class, eventListener);

        context.getContainer().setLeft(view.load());
    }
}