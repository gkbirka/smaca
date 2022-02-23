package gr.smaca.basket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class BasketService {
    private final Connection connection;

    BasketService(Connection connection) {
        this.connection = connection;
    }

    List<Product> getProducts(List<String> epcs) throws Exception {
        StringBuilder query = new StringBuilder("SELECT * FROM products, product_epcs " +
                "WHERE products.product_id = product_epcs.product_id " +
                "AND product_epcs.product_epc IN (");

        Iterator<String> iterator = epcs.iterator();
        while (iterator.hasNext()) {
            String epc = iterator.next();

            query.append("'").append(epc).append(iterator.hasNext() ? "', " : "');");
        }

        Statement statement;
        ResultSet resultSet;

        connection.setAutoCommit(true);

        statement = connection.createStatement();
        resultSet = statement.executeQuery(query.toString());

        List<Product> products = new ArrayList<>();

        while (resultSet.next()) {
            Product product = new Product(
                    resultSet.getInt("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("category_name"),
                    resultSet.getDouble("product_price"));

            products.add(product);
        }

        resultSet.close();
        statement.close();

        return products;
    }

    void purchaseProducts(String epc, List<Product> products) throws Exception {
        String orderQuery = "INSERT INTO orders (order_id, user_epc, order_date, order_total) " +
                "VALUES (default, '" + epc + "', NOW(), 0);";

        try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false);

            int affectedRows = orderStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Purchasing failed, no order rows affected.");
            }

            int orderId;
            try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Purchasing failed, no ID obtained.");
                }
            }

            StringBuilder detailsQuery = new StringBuilder("INSERT INTO order_details (order_id, product_id, product_price) VALUES");

            Iterator<Product> iterator = products.iterator();
            while (iterator.hasNext()) {
                Product product = iterator.next();

                detailsQuery.append("(")
                        .append(orderId).append(", ")
                        .append(product.getId()).append(", ")
                        .append(product.getPrice()).append(")")
                        .append(iterator.hasNext() ? ", " : ";");
            }

            PreparedStatement detailsStatement = connection.prepareStatement(detailsQuery.toString());
            affectedRows = detailsStatement.executeUpdate();

            if (affectedRows != products.size()) {
                throw new SQLException("Purchasing failed, no order details rows affected.");
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();

            throw new Exception(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}