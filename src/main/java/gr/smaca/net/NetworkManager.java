package gr.smaca.net;

import gr.smaca.common.observable.Property;
import gr.smaca.props.Props;
import gr.smaca.props.PropsState;

import java.sql.Connection;
import java.sql.DriverManager;

class NetworkManager {
    private final Property<PropsState> propertiesState = new Property<>();
    private final NetworkState networkState;
    private Connection connection;

    public NetworkManager(NetworkState networkState) {
        this.networkState = networkState;
    }

    void handle(NetworkEvent event) {
        switch (event.getType()) {
            case CONNECT:
                connect();
                break;
            case DISCONNECT:
                disconnect();
                break;
        }
    }

    private void connect() {
        try {
            Props properties = propertiesState.get().propertiesProperty().get();
            String connectionString = "jdbc:mysql://"
                    + properties.getHost() + ":"
                    + properties.getPort() + "/"
                    + properties.getName();

            connection = DriverManager.getConnection(connectionString,
                    properties.getUsername(),
                    properties.getPassword());

            networkState.connectionProperty().set(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            connection.close();
            networkState.connectionProperty().set(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Property<PropsState> propertiesStateProperty() {
        return propertiesState;
    }
}