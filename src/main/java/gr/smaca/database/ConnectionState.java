package gr.smaca.database;

import gr.smaca.common.state.State;

import java.sql.Connection;

public class ConnectionState implements State {
    private Connection connection;

    ConnectionState() {
    }

    public Connection getConnection() {
        return connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }
}