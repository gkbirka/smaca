package gr.smaca.history;

import gr.smaca.basket.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class HistoryService {
    private final Connection connection;

    HistoryService(Connection connection) {
        this.connection = connection;
    }

    List<Order> getOrders(String epc) throws Exception {
        String query = "SELECT * FROM orders WHERE user_epc = '" + epc + "';";

        Statement statement;
        ResultSet resultSet;

        connection.setAutoCommit(true);

        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);

        List<Order> orders = new ArrayList<>();

        while (resultSet.next()) {
            Order order = new Order(
                    resultSet.getInt("order_id"),
                    resultSet.getTimestamp("order_date"),
                    resultSet.getDouble("order_total"));

            orders.add(order);
        }

        resultSet.close();
        statement.close();

        return orders;
    }

    List<Product> getProducts(int id) throws Exception {
        String query = "SELECT products.product_id, products.product_name, products.category_name, order_details.product_price " +
                "FROM products, order_details " +
                "WHERE order_details.order_id = " + id + " " +
                "AND products.product_id = order_details.product_id;";

        Statement statement;
        ResultSet resultSet;

        connection.setAutoCommit(true);

        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);

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
}