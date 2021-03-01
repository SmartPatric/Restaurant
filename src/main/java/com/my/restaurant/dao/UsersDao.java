package com.my.restaurant.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.my.restaurant.models.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Users DAO - table users
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class UsersDao {

    /** insert new user to DB
     * @return number of affected rows (1)*/
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

    /** validate user
     *  looking for user by email and then verify entered passport using encryptor
     *  @return user object or null if user doesn't exist or password is incorrect*/
    public Users validate(String email, String password) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        Users user = null;
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
