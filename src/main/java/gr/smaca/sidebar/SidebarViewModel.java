package gr.smaca.sidebar;

import gr.smaca.auth.AuthEvent;
import gr.smaca.common.event.EventBus;
import gr.smaca.common.lifecycle.AbstractViewModel;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;

class SidebarViewModel extends AbstractViewModel {
    private final EventBus eventBus;

    SidebarViewModel(EventBus eventBus) {
        super(false);
        this.eventBus = eventBus;
    }

    void navigate(View view) {
        eventBus.emit(new NavigationEvent(View.NONE));
        eventBus.emit(new NavigationEvent(view));
    }

    void disconnect() {
        eventBus.emit(new NavigationEvent(View.NONE));
        eventBus.emit(new AuthEvent(AuthEvent.Type.DISCONNECT));
    }
}