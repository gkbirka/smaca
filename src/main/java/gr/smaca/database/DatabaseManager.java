package gr.smaca.database;

import gr.smaca.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;

class DatabaseManager {
    private final ConnectionState state;
    private final Config config;
    private Connection connection;

    DatabaseManager(ConnectionState state, Config config) {
        this.state = state;
        this.config = config;
    }

    void handle(DatabaseEvent event) {
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
            String connectionString = "jdbc:mysql://"
                    + config.getDatabaseHost() + ":"
                    + config.getDatabasePort() + "/"
                    + config.getDatabaseName();

            connection = DriverManager.getConnection(connectionString,
                    config.getDatabaseUsername(),
                    config.getDatabasePassword());

            state.connectionProperty().set(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            connection.close();
            state.connectionProperty().set(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}