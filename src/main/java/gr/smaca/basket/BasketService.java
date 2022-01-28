package gr.smaca.basket;

import gr.smaca.reader.Tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class BasketService {
    private final Connection connection;

    BasketService(Connection connection) {
        this.connection = connection;
    }

    BasketEvent getProducts(List<Tag> tags) {
        StringBuilder query = new StringBuilder("SELECT * FROM products WHERE product_epc IN (");
        for (Tag tag : tags) {
            query.append("'").append(tag.getEpc()).append("'");
        }
        query.append(");");

        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.toString());

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getString("product_epc"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_category"),
                        resultSet.getDouble("product_price"));

                products.add(product);
            }

            resultSet.close();
            statement.close();

            return new BasketEvent(BasketEvent.Type.PRODUCTS_FOUND, products);
        } catch (Exception e) {
            e.printStackTrace();

            return new BasketEvent(BasketEvent.Type.CONNECTION_ERROR);
        }
    }
}