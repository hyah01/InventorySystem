package org.example;

import java.sql.*;
import java.util.ArrayList;

public class ProductSystem {
    public void addProduct(Product product) {
        String addQuery = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?);";

        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            PreparedStatement preparedStatement = connection.prepareStatement(addQuery);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setDouble(3, product.getPrice());

            int result = preparedStatement.executeUpdate();
            connection.commit(); // Commit transaction

            if (result > 0) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Product addition failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction
                    System.out.println("Transaction rolled back.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // Restore auto-commit mode
                    connection.close(); // Close connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void updateProduct(int id, String name, int quantity, double price){
        String udpateQuery = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?;";
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
                    System.out.println("Transaction rolled back.");
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
    public void deleteProduct(String name) {
        String deleteQuery = "DELETE FROM products WHERE name = ?;";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, name);

            int result = preparedStatement.executeUpdate();
            connection.commit();

            if (result > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("No product found with the given name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                    System.out.println("Transaction rolled back.");
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
        String getQuery = "SELECT * FROM products WHERE name = ?;";
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
                 System.out.println("Product don't exist");
                 return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> getAllProduct(){
        String getAllQuery = "SELECT * FROM products;";
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
