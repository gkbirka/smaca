package gr.smaca.navigation;

import gr.smaca.auth.AuthApplicationComponent;
import gr.smaca.basket.BasketApplicationComponent;
import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.history.HistoryApplicationComponent;
import gr.smaca.profile.ProfileApplicationComponent;
import gr.smaca.sidebar.SidebarApplicationComponent;
import gr.smaca.user.UserApplicationComponent;

class NavigationManager {
    private final ApplicationContext context;

    NavigationManager(ApplicationContext context) {
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
                component = new BasketApplicationComponent();
                break;
            case HISTORY:
                component = new HistoryApplicationComponent();
                break;
            case PROFILE:
                component = new ProfileApplicationComponent();
                break;
        }

        if (component != null) {
            component.initState(context);
            component.initComponent(context);
        }
    }
}