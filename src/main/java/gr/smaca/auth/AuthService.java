package gr.smaca.auth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class AuthService {
    private final Connection connection;

    public AuthService(Connection connection) {
        this.connection = connection;
    }

    AuthEvent auth(String epc, String pin) {
        String query = "SELECT first_name, last_name, epc FROM users WHERE epc = '" + epc + "' AND pin = '" + pin + "';";

        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {

                resultSet.close();
                statement.close();

                return new AuthEvent(AuthEvent.Type.CONNECT);
            } else {
                resultSet.close();
                statement.close();

                return new AuthEvent(AuthEvent.Type.WRONG_PIN);
            }
        } catch (Exception e) {
            e.printStackTrace();

            return new AuthEvent(AuthEvent.Type.CONNECTION_ERROR);
        }
    }
}