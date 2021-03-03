package com.my.restaurant.dao;

import com.my.restaurant.models.Orders;
import com.my.restaurant.models.Status;
import com.my.restaurant.models.Users;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 * Orders DAO - table orders
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class OrdersDao {
    private static final Logger logger = LogManager.getLogger(OrdersDao.class);

    /** find order list by status
     * @return list or orders */
    public List<Orders> findAllOrders(String status) {
        PreparedStatement preparedStatement = null;
        Connection connection= null;
        List<Orders> orders = new ArrayList<>();
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM orders WHERE status = ?;");
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (resultSet.next()) {
                orders.add(new Orders(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
                        LocalDateTime.parse(resultSet.getString(4), formatter),
                        LocalDateTime.parse(resultSet.getString(5), formatter),
                        resultSet.getDouble(6)));
            }
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
        return orders;
    }

    /** find order by user id
     * @return order object or null if it doesn't exist*/
    public Orders findOrderByUserId(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        Orders order = null;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("select * from orders where user_id = ? AND status!='CLOSED' AND status != 'CANCELED'");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (resultSet.next()) {
                order = new Orders(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
                        LocalDateTime.parse(resultSet.getString(4), formatter),
                        LocalDateTime.parse(resultSet.getString(5), formatter),
                        resultSet.getDouble(6));
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
        return order;
    }

    /** create new order
     * default status is MAKING
     * @return created order object*/
    public Orders createNewOrder(Integer userId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet idSet = null;
        Orders order = new Orders();
        int success = 0;
        order.setUserId(userId);
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("insert into orders(user_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);
            success = preparedStatement.executeUpdate();
            idSet = preparedStatement.getGeneratedKeys();
            if (idSet.next()) {
                order.setId(idSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.error("SQL exception occurred" + e);
        } finally {
            try {
                preparedStatement.close();
                connection.close();
                idSet.close();
            } catch (SQLException e) {
                logger.error("Error closing connection" + e);
            }
        }
        return (success == 1) ? order : null;
    }

    /** pay order
     * change order status to APPROVING
     * */
    public void payOrder(Integer orderId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("update orders set status='APPROVING' where id = ?");
            preparedStatement.setInt(1, orderId);
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

    /** cancel order
     * change order status to CANCELED
     * */
    public void cancelOrder(Integer orderId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("update orders set status='CANCELED' where id = ?");
            preparedStatement.setInt(1, orderId);
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

    /** set order status to next
     *     MAKING(0),
     *     APPROVING(1),
     *     COOKING(2),
     *     DELIVERING(3),
     *     CLOSED(4),
     *     CANCELED(5);
     * */
    public void nextStatus(Integer userId) {
        Orders order = findOrderByUserId(userId);
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("update orders set status = ? where id = ?");
            Status status = Status.findStatusById((Status.valueOf(order.getStatus()).getId()) + 1);
            preparedStatement.setString(1, status.toString());
            preparedStatement.setInt(2, order.getId());
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

    /** change order price
     * add or subtract price from total order price
     * */
    public void changePrice(boolean add, Double price, Integer orderId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DbUtil.getInstance().getConnection();
            if (add) {
                preparedStatement = connection.prepareStatement("update orders set total = total + ? where id = ?");
            } else preparedStatement = connection.prepareStatement("update orders set total = total - ? where id = ?");

            preparedStatement.setDouble(1, price);
            preparedStatement.setInt(2, orderId);
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


    /** delete user from DB
     **/
    public Integer deleteOrder(Integer userId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int result = 0;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("delete from orders where user_id = ?");
            preparedStatement.setInt(1, userId);
            result = preparedStatement.executeUpdate();
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
        return result;
    }
}
