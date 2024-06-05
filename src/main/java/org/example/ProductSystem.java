package org.example;

import java.sql.*;
import java.util.ArrayList;

public class ProductSystem {
    // Add product user input using query
    public void addProduct(Product product) {
        // Query
        String addQuery = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?);";
        Connection connection = null;
        try {
            // Set up Connection
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Load variable into prepareStatement
            PreparedStatement preparedStatement = connection.prepareStatement(addQuery);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setDouble(3, product.getPrice());

            // execute update and get back an integer
            int result = preparedStatement.executeUpdate();
            connection.commit(); // Commit transaction

            // If result is more than 0, then we know it succeed in adding
            if (result > 0) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Product addition failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // If there is an error during sql it will roll back
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

    // Update products based on user's input
    public void updateProduct(int id, String name, int quantity, double price){
        // Query
        String udpateQuery = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?;";
        Connection connection = null;
        try {
            // Set up connection
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            // Load variable into prepareStatement
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
    // Delete a product from database
    public void deleteProduct(String name) {
        // Query
        String deleteQuery = "DELETE FROM products WHERE name = ?;";
        Connection connection = null;
        try {
            // Set up Connection
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            // Delete product by name
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

    // Retrieve a product based name
    public Product getProduct(String name){
        String getQuery = "SELECT * FROM products WHERE name = ?;";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
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
                 // If can't find product
                 System.out.println("Product don't exist");
                 return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    // Return product database
    public ArrayList<Product> getAllProduct(){
        String getAllQuery = "SELECT * FROM products;";
        ArrayList<Product> products = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getAllQuery);
            while (result.next()){
                int id = result.getInt("id");
                String productName = result.getString("name");
                int quantity = result.getInt("quantity");
                double price = result.getDouble("price");
                // Add new products based on what queried
                products.add(new Product(id,productName,quantity,price));
            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}
