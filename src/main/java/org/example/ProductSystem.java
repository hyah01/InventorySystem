package org.example;

import java.sql.*;
import java.util.ArrayList;

public class ProductSystem {
    private final String addQuery = "INSERT INTO products (name, quantity, price) VALUES(?, ?, ?);";
    private final String getQuery = "SELECT * FROM products WHERE name = ?;";
    private final String getAllQuery = "SELECT * FROM products;";

    private final String udpateQuery = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?;";
    public void addProduct(Product product){
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(addQuery);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setInt(2,product.getQuantity());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateProduct(int id, String name, int quantity, double price){
            Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(udpateQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, id);
            int result = preparedStatement.executeUpdate();
            connection.commit();

            if (result > 0){
                System.out.println("Product updated successfully");
            }
            else {
                System.out.println("No product found with the given id.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try{
                if (connection != null){
                    connection.rollback();
                    System.out.println("Rolled Backed");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Product getProduct(String name){
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
            preparedStatement.setString(1,name);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()){
                int id = result.getInt("id");
                String productName = result.getString("name");
                int quantity = result.getInt("quantity");
                double price = result.getDouble("price");
                return new Product(id,productName,quantity,price);

            }
             else {
                 return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> getAllProduct(){
        ArrayList<Product> products = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getAllQuery);
            while (result.next()){
                int id = result.getInt("id");
                String productName = result.getString("name");
                int quantity = result.getInt("quantity");
                double price = result.getDouble("price");
                products.add(new Product(id,productName,quantity,price));
            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
