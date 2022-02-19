package gr.smaca.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class UserService {
    private final Connection connection;

    UserService(Connection connection) {
        this.connection = connection;
    }

    User getUser(String epc) throws Exception {
        String query = "SELECT user_epc, user_first_name, user_last_name FROM users WHERE user_epc = '" + epc + "';";

        Statement statement;
        ResultSet resultSet;

        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            User user = new User(
                    resultSet.getString("user_epc"),
                    resultSet.getString("user_first_name"),
                    resultSet.getString("user_last_name"));

            resultSet.close();
            statement.close();

            return user;
        } else {
            resultSet.close();
            statement.close();

            return null;
        }
    }
}