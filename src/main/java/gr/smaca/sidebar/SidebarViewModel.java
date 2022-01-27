package gr.smaca.sidebar;

import gr.smaca.auth.AuthEvent;
import gr.smaca.common.event.EventBus;
import gr.smaca.common.view.ViewModel;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;

class SidebarViewModel implements ViewModel {
    private final EventBus eventBus;

    SidebarViewModel(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    void navigate(View view) {
        eventBus.emit(new NavigationEvent(view));
    }

    void disconnect() {
        eventBus.emit(new AuthEvent(AuthEvent.Type.DISCONNECT));
    }

    @Override
    public void dispose() {
    }
}