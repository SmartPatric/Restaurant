package com.my.restaurant.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Util for getting connection
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class DbUtil {
    static Connection connection = null;

    static {
        try{
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/restaurant");
            connection = dataSource.getConnection();
        }
        catch (NamingException | SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
