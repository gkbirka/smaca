package gr.smaca.auth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class AuthService {
    private final Connection connection;

    AuthService(Connection connection) {
        this.connection = connection;
    }

    boolean auth(String epc, String pin) throws Exception {
        String query = "SELECT user_first_name, user_last_name, user_epc FROM users WHERE user_epc = '" + epc + "' AND user_pin = '" + pin + "';";

        Statement statement;
        ResultSet resultSet;

        connection.setAutoCommit(true);

        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);

        if (resultSet.next()) {

            resultSet.close();
            statement.close();

            return true;
        } else {
            resultSet.close();
            statement.close();

            return false;
        }
    }
}