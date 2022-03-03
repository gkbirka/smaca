package gr.smaca.profile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ProfileService {
    private final Connection connection;

    ProfileService(Connection connection) {
        this.connection = connection;
    }

    boolean updatePin(String epc, String currentPin, String newPin) throws Exception {
        String selectQuery = "SELECT user_first_name, user_last_name, user_epc FROM users WHERE user_epc = '" + epc + "' AND user_pin = '" + currentPin + "';";

        Statement selectStatement;
        ResultSet resultSet;

        connection.setAutoCommit(true);

        selectStatement = connection.createStatement();
        resultSet = selectStatement.executeQuery(selectQuery);

        if (resultSet.next()) {

            resultSet.close();
            selectStatement.close();

            String updateQuery = "UPDATE users SET user_pin = " + newPin + " WHERE user_epc = '" + epc + "';";

            Statement updateStatement = connection.createStatement();
            int affectedRows = updateStatement.executeUpdate(updateQuery);

            updateStatement.close();

            if (affectedRows == 0) {
                throw new SQLException("PIN update failed, no user rows affected.");
            }

            return true;
        } else {
            resultSet.close();
            selectStatement.close();

            return false;
        }
    }
}