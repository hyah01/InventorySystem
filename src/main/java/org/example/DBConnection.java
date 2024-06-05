package org.example;
import java.sql.*;
public class DBConnection {
    // Variables for connection
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
    private static final String USER = "root";
    private static final String PASSWORD = "gensparksql";

    // return a connection for other classes to utilize
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
