package com.my.restaurant.dao;

import com.my.restaurant.models.Category;
import com.my.restaurant.models.Dishes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dishes DAO - table dishes
 *
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class DishesDao {
    private static final Logger logger = LogManager.getLogger(DishesDao.class);

    /**
     * return list of all dishes from BD
     *
     * @return list of dishes
     */
    public List<Dishes> findAllDishes() {
        Statement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<Dishes> dishes = new ArrayList<>();
        try {
            connection = DbUtil.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM dishes;");
            while (resultSet.next()) {
                dishes.add(new Dishes(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4), resultSet.getString(5),
                        Category.valueOf(resultSet.getString(6))));
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred" + e);
        } finally {
            try {
                statement.close();
                connection.close();
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Error closing connection" + e);
            }
        }
        return dishes;
    }

    /**
     * return list of all dishes of order
     *
     * @return list of dishes
     */
    public List<Dishes> findDishesByOrderId(Integer orderId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        List<Dishes> dishes = new ArrayList<>();
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("select d.id, d.name, d.price, d.image, d.description, d.category, " +
                    "od.amount, od.total_dish " +
                    "from dishes d join orders_dishes od on d.id = od.dish_id " +
                    "join orders o on o.id = od.order_id where " +
                    "o.id = ?");
            preparedStatement.setInt(1, orderId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dishes.add(new Dishes(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getString(4), resultSet.getString(5),
                        Category.valueOf(resultSet.getString(6)),
                        resultSet.getInt(7), resultSet.getDouble(8)));
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred" + e);
        } finally {
            try {
                preparedStatement.close();
                connection.close();
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Error closing connection" + e);
            }
        }
        return dishes;
    }

    /**
     * return dish by id
     *
     * @return dish object
     */
    public Dishes findDishByDishId(Integer dishId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        Dishes dish = null;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM dishes where id = ?;");
            preparedStatement.setInt(1, dishId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                dish = new Dishes(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4), resultSet.getString(5),
                        Category.valueOf(resultSet.getString(6)));
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred" + e);
        } finally {
            try {
                preparedStatement.close();
                connection.close();
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Error closing connection" + e);
            }
        }
        return dish;
    }
}
