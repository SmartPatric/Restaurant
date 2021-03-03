package com.my.restaurant.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Util for getting connection
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class DbUtil {

    private static DbUtil instance;
    private boolean isTest;
    private static final Logger log = LogManager.getLogger(DbUtil.class);

    private DbUtil() {
    }

    private DbUtil(boolean isTest) {
        this.isTest = isTest;
    }

    /**
     * Return the instance of DBManager
     */
    public static DbUtil getInstance() {
        if (instance == null) {
            instance = new DbUtil();
        }
        return instance;
    }

    public void getTest(){
        System.out.println("is test "+isTest);
    }

    /**
     * Return the instance of DBManager
     * @param isTest is used for test.
     */
    public static DbUtil getInstance(boolean isTest) {
        instance = new DbUtil(isTest);
        return instance;
    }

    /**
     * Return connection for database according to the isTest value;
     */
    public Connection getConnection() {
        Context context;
        Connection connection = null;
        if (isTest) {
            return getConnectionForTests();
        }
        try {
            context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/restaurant");
            connection = ds.getConnection();
            log.info("Get connection by pool");
        } catch (NamingException | SQLException e) {
            log.error("Error while connection pooling ", e);
        }
        return connection;
    }

    /**
     * Return connection for test database.
     */
    private Connection getConnectionForTests() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            log.error("Error while connection for test. ", e);
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restauran?serverTimezone=UTC", "root", "rest");
        } catch (SQLException throwables) {
            log.error("Error while getting connection for test. ", throwables);
        }
        return connection;

    }


}