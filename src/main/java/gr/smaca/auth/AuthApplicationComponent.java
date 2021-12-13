package gr.smaca.auth;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.navigation.NavigationEvent;
import gr.smaca.navigation.View;
import gr.smaca.user.UserState;

public class AuthApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
    }

    @Override
    public void initComponent(ApplicationContext context) {
        UserState userState = context.getStateRegistry().getState(UserState.class);

        AuthService service = new AuthService();
        AuthViewModel viewModel = new AuthViewModel(context.getEventBus(), service);
        viewModel.userProperty().set(userState.userProperty().get());
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