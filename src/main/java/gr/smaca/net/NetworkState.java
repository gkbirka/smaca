package gr.smaca.net;

import gr.smaca.common.observable.Property;
import gr.smaca.common.state.State;

import java.sql.Connection;

public class NetworkState implements State {
    private final Property<Connection> connection = new Property<>();

    public Property<Connection> connectionProperty() {
        return connection;
    }
}