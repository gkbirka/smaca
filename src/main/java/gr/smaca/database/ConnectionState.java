package gr.smaca.database;

import gr.smaca.common.state.State;

import java.sql.Connection;

public class ConnectionState implements State {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }
}