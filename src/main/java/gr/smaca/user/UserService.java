package gr.smaca.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class UserService {
    private final Connection connection;

    UserService(Connection connection) {
        this.connection = connection;
    }

    UserEvent getUser(String epc) {
        String query = "SELECT user_epc, user_first_name, user_last_name FROM users WHERE user_epc = '" + epc + "';";

        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                User user = new User(
                        resultSet.getString("epc"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"));

                resultSet.close();
                statement.close();

                return new UserEvent(UserEvent.Type.USER_FOUND, user);
            } else {
                resultSet.close();
                statement.close();

                return new UserEvent(UserEvent.Type.USER_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();

            return new UserEvent(UserEvent.Type.CONNECTION_ERROR);
        }
    }
}