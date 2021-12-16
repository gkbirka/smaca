package gr.smaca.net;

import gr.smaca.common.component.ApplicationComponent;
import gr.smaca.common.component.ApplicationContext;
import gr.smaca.common.event.EventListener;
import gr.smaca.props.PropsState;

public class NetworkApplicationComponent implements ApplicationComponent {
    @Override
    public void initState(ApplicationContext context) {
        NetworkState state = new NetworkState();
        context.getStateRegistry().register(NetworkState.class, state);
    }

    @Override
    public void initComponent(ApplicationContext context) {
        PropsState propertiesState = context.getStateRegistry().getState(PropsState.class);
        NetworkState networkState = context.getStateRegistry().getState(NetworkState.class);

        NetworkManager manager = new NetworkManager(networkState);
        manager.propertiesStateProperty().set(propertiesState);

        EventListener<NetworkEvent> eventListener = new EventListener<>() {
            @Override
            public void handle(NetworkEvent event) {
                manager.handle(event);
                if (event.getType() == NetworkEvent.Type.DISCONNECT) {
                    context.getEventBus().unsubscribe(NetworkEvent.class, this);
                    context.getStateRegistry().unregister(NetworkState.class);
                }
            }
        };
        context.getEventBus().subscribe(NetworkEvent.class, eventListener);

        context.getEventBus().emit(new NetworkEvent(NetworkEvent.Type.CONNECT));
    }
}