package gr.smaca.auth;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.database.ConnectionState;
import gr.smaca.database.DatabaseEvent;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;
import gr.smaca.user.UserState;

public class AuthApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {}

    @Override
    public void initComponent(ApplicationContext context) {
        ConnectionState connectionState = context.getStateRegistry().getState(ConnectionState.class);
        UserState userState = context.getStateRegistry().getState(UserState.class);

        AuthService service = new AuthService(connectionState.getConnection());
        AuthViewModel viewModel = new AuthViewModel(userState.getUser(), service, context.getEventBus());
        AuthView view = new AuthView(viewModel);

        EventListener<DatabaseEvent> onDatabaseEvent = event -> viewModel.dispose();
        context.getEventBus().subscribe(DatabaseEvent.class, onDatabaseEvent);

        EventListener<AuthEvent> onAuthEvent = new EventListener<>() {
            @Override
            public void handle(AuthEvent event) {
                switch (event.getType()) {
                    case CONNECTION_ERROR:
                    case WRONG_PIN:
                        view.handle(event);
                        break;
                    case CONNECT:
                        context.getEventBus().unsubscribe(DatabaseEvent.class, onDatabaseEvent);
                        context.getContainer().setCenter(null);
                        viewModel.dispose();

                        context.getEventBus().emit(new NavigationEvent(View.SIDEBAR));
                        break;
                    case DISCONNECT:
                    case CANCEL:
                        context.getEventBus().unsubscribe(AuthEvent.class, this);
                        context.getEventBus().subscribe(DatabaseEvent.class, onDatabaseEvent);
                        context.getStateRegistry().unregister(UserState.class);

                        if (event.getType() == AuthEvent.Type.CANCEL) {
                            viewModel.dispose();
                        }

                        context.getEventBus().emit(new NavigationEvent(View.USER));
                        break;
                }
            }
        };
        context.getEventBus().subscribe(AuthEvent.class, onAuthEvent);

        context.getContainer().setCenter(view.load());
    }
}