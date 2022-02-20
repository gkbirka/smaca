package gr.smaca.basket;

import java.sql.Connection;
import java.sql.ResultSet;
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
        StringBuilder query = new StringBuilder("SELECT * FROM products WHERE product_epc IN (");

        Iterator<String> iterator = epcs.iterator();
        while (iterator.hasNext()) {
            String epc = iterator.next();

            query.append("'").append(epc).append(iterator.hasNext() ? "', " : "');");
        }

        Statement statement;
        ResultSet resultSet;

        statement = connection.createStatement();
        resultSet = statement.executeQuery(query.toString());

        List<Product> products = new ArrayList<>();

        while (resultSet.next()) {
            Product product = new Product(
                    resultSet.getString("product_epc"),
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