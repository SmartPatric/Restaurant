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
 *
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class DbUtil {
    private static final Logger log = LogManager.getLogger(DbUtil.class);

    private static DbUtil instance;
    private boolean isTest;

    public DbUtil(boolean test) {
        this.isTest = test;
    }

    public DbUtil() {

    }

    public static DbUtil getInstance() {
        if (instance == null) {
            instance = new DbUtil();
        }
        return instance;
    }

    public static DbUtil getInstance(boolean test) {
        if (instance == null) {
            instance = new DbUtil(test);
        }
        return instance;
    }


    public void getTest() {
        System.out.println("test" + isTest);
    }

    public Connection getConnection() {
        Context context;
        Connection connection = null;

        System.out.println("is test " + isTest);
        if (isTest) {
            return getConnectionForTests();
        }
        try {
            context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/restaurant");
            connection = ds.getConnection();
            log.info("Get connection by pool");
        } catch (NamingException | SQLException e) {
            log.error("Error connecting to pool ", e);
        }
        return connection;
    }


    private static Connection getConnectionForTests() {
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