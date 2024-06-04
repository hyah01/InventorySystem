package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSystem {
    public static User authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?;";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()){
            int uid = result.getInt("id");
            String uname = result.getString("username");
            String upass = result.getString("password");
            String urole = result.getString("role");
            return new User(uid,uname,upass,urole);
        }

        return null;
    }
}
