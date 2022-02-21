package gr.smaca.sidebar;

import gr.smaca.auth.AuthEvent;
import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.navigation.View;

public class SidebarApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {}

    @Override
    public void initComponent(ApplicationContext context) {
        SidebarViewModel viewModel = new SidebarViewModel(context.getEventBus());
        SidebarView view = new SidebarView(viewModel);

        EventListener<AuthEvent> onAuthEvent = new EventListener<>() {
            @Override
            public void handle(AuthEvent event) {
                if (event.getType() == AuthEvent.Type.DISCONNECT) {
                    context.getEventBus().unsubscribe(AuthEvent.class, this);
                    context.getContainer().setLeft(null);
                }
            }
        };
        context.getEventBus().subscribe(AuthEvent.class, onAuthEvent);

        context.getContainer().setLeft(view.load());
        view.navigate(View.BASKET);
    }
}