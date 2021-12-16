package gr.smaca.user;

import gr.smaca.net.NetworkState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class UserService {
    private final NetworkState networkState;

    public UserService(NetworkState networkState) {
        this.networkState = networkState;
    }

    UserEvent getUser(String epc) {
        Connection connection = networkState.connectionProperty().get();
        String query = "SELECT * FROM users WHERE epc = '" + epc + "';";

        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                User user = new User(resultSet.getString("epc"),
                        resultSet.getString("username"),
                        resultSet.getString("surname"));

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