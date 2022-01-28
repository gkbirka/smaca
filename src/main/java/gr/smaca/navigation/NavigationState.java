package gr.smaca.navigation;

import gr.smaca.common.state.State;

class NavigationState implements State {
    private View view;

    NavigationState() {
    }

    View getView() {
        return view;
    }

    void setView(View view) {
        this.view = view;
    }
}