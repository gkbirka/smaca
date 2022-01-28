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

            connection = DriverManager.getConnection(
                    connectionString,
                    config.getDatabaseUsername(),
                    config.getDatabasePassword());

            state.setConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            if (connection != null) {

                connection.close();
                connection = null;
                state.setConnection(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}