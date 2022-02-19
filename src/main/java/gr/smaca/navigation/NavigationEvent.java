package gr.smaca.navigation;

import gr.smaca.common.event.Event;

public class NavigationEvent implements Event {
    private final View view;

    public NavigationEvent(View view) {
        this.view = view;
    }

    View getView() {
        return view;
    }
}