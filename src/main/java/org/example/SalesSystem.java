package org.example;
import java.sql.*;
import java.util.ArrayList;
public class SalesSystem {
    // Add record sale to sales database
    public void RecordSale(int id, int quantity){
        // provide 2 query to INSERT one into sale database and UPDATE one into product
        String productQuery = "UPDATE products SET quantity = quantity - ? WHERE id = ?;";
        String recordQuery = "INSERT INTO sales (product_id, quantity) VALUES (?, ?);";
        Connection connection = null;
        try {
            // Add connection
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Prepare Statement to subtract quantity from product quantity
            PreparedStatement preparedStatement = connection.prepareStatement(productQuery);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id);
            int result = preparedStatement.executeUpdate();

            // Prepare Statement to add to data to sale database
            preparedStatement = connection.prepareStatement(recordQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, quantity);
            result = preparedStatement.executeUpdate();

            // Only commit After everything works
            connection.commit(); // Commit transaction

            if (result > 0) {
                System.out.println("sale added successfully.");
            } else {
                System.out.println("sale addition failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // If something wrong it will roll back
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

    // return sales database
    public ArrayList<Sales> getSalesReport(){
        String getAllQuery = "SELECT * FROM sales;";
        ArrayList<Sales> sales = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getAllQuery);
            while (result.next()){
                int id = result.getInt("id");
                int productId = result.getInt("product_id");
                int quantity = result.getInt("quantity");
                Timestamp saleDate = result.getTimestamp("sale_date");
                sales.add(new Sales(id,productId,quantity,saleDate));
            }
            return sales;

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

    // Join sales and products
    public ArrayList<String> getSaleInventoryReport(){
        String getAllQuery = "SELECT s.id,s.product_id,s.quantity AS sale_quantity,s.sale_date, p.name, " +
                "p.quantity, p.price FROM sales AS s LEFT JOIN products AS p ON s.product_id = p.id;";
        ArrayList<String> salesProduct = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getAllQuery);
            while (result.next()){
                int id = result.getInt(1);
                int productId = result.getInt(2);
                int saleQuantity = result.getInt(3);
                Timestamp saleDate = result.getTimestamp(4);
                String name = result.getString(5);
                int quantity = result.getInt(6);
                double profit = result.getDouble(7) * quantity;

                salesProduct.add("SaleProduct{" +
                        "id=" + id +
                        ", productId=" + productId +
                        ", saleQuantity=" + saleQuantity +
                        ", saleDate=" + saleDate +
                        ", name='" + name + '\'' +
                        ", quantity=" + quantity +
                        ", profit=" + profit +
                        '}');
            }
            return salesProduct;

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
