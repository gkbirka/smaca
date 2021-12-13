package gr.smaca.navigation;

import gr.smaca.auth.AuthApplicationComponent;
import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.sidebar.SidebarApplicationComponent;
import gr.smaca.user.UserApplicationComponent;

class Navigation {
    private final ApplicationContext context;

    Navigation(ApplicationContext context) {
        this.context = context;
    }

    void handle(NavigationEvent event) {
        ApplicationComponent component = null;
        switch (event.getView()) {
            case USER:
                component = new UserApplicationComponent();
                break;
            case AUTH:
                component = new AuthApplicationComponent();
                break;
            case SIDEBAR:
                component = new SidebarApplicationComponent();
                break;
            case BASKET:
                //TODO
                break;
            case HISTORY:
                //TODO
                break;
            case PROFILE:
                //TODO
                break;
        }
        component.initState(context);
        component.initComponent(context);
    }
}