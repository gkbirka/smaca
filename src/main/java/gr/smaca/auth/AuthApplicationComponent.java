package gr.smaca.auth;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.database.ConnectionState;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;
import gr.smaca.user.User;
import gr.smaca.user.UserState;

import java.sql.Connection;

public class AuthApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        ConnectionState connectionState = context.getStateRegistry().getState(ConnectionState.class);
        Connection connection = connectionState.connectionProperty().get();

        UserState userState = context.getStateRegistry().getState(UserState.class);
        User user = userState.userProperty().get();

        AuthService service = new AuthService(connection);
        AuthViewModel viewModel = new AuthViewModel(user, service, context.getEventBus());
        AuthView view = new AuthView(viewModel);

        EventListener<AuthEvent> eventListener = new EventListener<>() {
            @Override
            public void handle(AuthEvent event) {
                switch (event.getType()) {
                    case CONNECTION_ERROR:
                    case WRONG_PIN:
                        view.handle(event);
                        break;
                    case CONNECT:
                        context.getContainer().setCenter(null);
                        viewModel.dispose();

                        context.getEventBus().emit(new NavigationEvent(View.SIDEBAR));
                        break;
                    case DISCONNECT:
                    case CANCEL:
                        context.getEventBus().unsubscribe(AuthEvent.class, this);
                        context.getStateRegistry().unregister(UserState.class);

                        if (event.getType() == AuthEvent.Type.CANCEL) {
                            viewModel.dispose();
                        }

                        context.getEventBus().emit(new NavigationEvent(View.USER));
                        break;
                }
            }
        };
        context.getEventBus().subscribe(AuthEvent.class, eventListener);

        context.getContainer().setCenter(view.load());
    }
}