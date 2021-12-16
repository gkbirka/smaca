package gr.smaca.net;
import gr.smaca.common.event.Event;

public class NetworkEvent implements Event {
    public enum Type {
        CONNECT,
        DISCONNECT
    }

    private final Type type;

    public NetworkEvent(Type type) {
        this.type = type;
    }

    Type getType() {
        return type;
    }
}