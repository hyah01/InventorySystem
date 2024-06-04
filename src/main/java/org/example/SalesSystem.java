package org.example;
import java.sql.*;
import java.util.ArrayList;
public class SalesSystem {
    public void RecordSale(int id, int quantity){
        String productQuery = "UPDATE products SET quantity = quantity - ? WHERE id = ?;";
        String recordQuery = "INSERT INTO sales (product_id, quantity) VALUES (?, ?);";
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            PreparedStatement preparedStatement = connection.prepareStatement(productQuery);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id);
            int result = preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(recordQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, quantity);
            result = preparedStatement.executeUpdate();

            connection.commit(); // Commit transaction

            if (result > 0) {
                System.out.println("sale added successfully.");
            } else {
                System.out.println("sale addition failed.");
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

    public ArrayList<Sales> getSalesReport(){
        String getAllQuery = "SELECT * FROM sales;";
        ArrayList<Sales> sales = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
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
        }
    }

}
