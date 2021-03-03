package com.my.restaurant.dao;

import com.my.restaurant.models.OrdersDishes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * OrdersDishes DAO - table orders_dishes
 *
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class OrdersDishesDao {
    private static final Logger logger = LogManager.getLogger(OrdersDishesDao.class);

    /** insert new data in order_dishes table
     * @return inserted OrdersDishes */
    public OrdersDishes createNewOrderDish(Integer orderId, Integer dishId) throws SQLIntegrityConstraintViolationException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        OrdersDishes orderDish = new OrdersDishes(orderId, dishId, 1);
        int success = 0;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("insert into orders_dishes (order_id, dish_id, amount) VALUES (?, ?, 1)");
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, dishId);
            success = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getClass().equals(SQLIntegrityConstraintViolationException.class)) {
                throw new SQLIntegrityConstraintViolationException();
            }
            logger.error("SQL exception occurred" + e);
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                logger.error("Error closing connection" + e);
            }
        }
        return (success == 1) ? orderDish : null;
    }

    /** increase column amount by 1 */
    public void increaseOrderDishAmount(Integer orderId, Integer dishId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("update orders_dishes set amount = amount+1 where order_id = ? AND dish_id = ?");
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, dishId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL exception occurred" + e);
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                logger.error("Error closing connection" + e);
            }
        }
    }

    /** decrease column amount by 1 */
    public void decreaseOrderDishAmount(Integer orderId, Integer dishId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("update orders_dishes set amount = amount-1 where order_id = ? AND dish_id = ?");
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, dishId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQL exception occurred" + e);
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                logger.error("Error closing connection" + e);
            }
        }
    }

    /** delete from order_dishes */
    public void removeOrderDish(Integer orderId, Integer dishId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("delete from orders_dishes where dish_id = ? and order_id = ?");
            preparedStatement.setInt(1, dishId);
            preparedStatement.setInt(2, orderId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("SQL exception occurred" + e);
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                logger.error("Error closing connection" + e);
            }
        }
    }

}
