package com.my.restaurant.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.my.restaurant.models.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {

    public Integer insertUser(Users user) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int result = 0;
        try {
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("insert into users (email, password) values " +
                    "(?, ?);");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());

            System.out.println(preparedStatement);

            result = preparedStatement.executeUpdate();
            System.out.println(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteUser(Integer id) {
        return false;
    }

    public Users findUser(Integer id) {
        return new Users();
    }

    public List<Users> findAllUsers() {
        Statement statement;
        Connection connection = null;
        List<Users> users = new ArrayList<>();
        try {
            connection = DbUtil.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");
            while (resultSet.next()) {
                users.add(new Users(resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Users validate(String email, String password) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        Users user = null;
        // System.out.println("validate before"+email+" "+password);
        try {
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("select * from users where email = ?");
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new Users(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getBoolean(4), resultSet.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user!= null && BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified){
            return user;
        }
        return null;
    }
}
