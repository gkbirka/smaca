package gr.smaca.navigation;

import gr.smaca.common.observable.Property;
import gr.smaca.common.state.State;

class NavigationState implements State {
    private final Property<View> view = new Property<>();

    Property<View> viewProperty() {
        return view;
    }
}