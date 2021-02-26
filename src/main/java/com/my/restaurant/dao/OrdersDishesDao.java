package com.my.restaurant.dao;

import com.my.restaurant.models.OrdersDishes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class OrdersDishesDao {

    public OrdersDishes createNewOrderDish(Integer orderId, Integer dishId) throws SQLIntegrityConstraintViolationException{
        PreparedStatement preparedStatement;
        Connection connection;
        OrdersDishes orderDish = new OrdersDishes(orderId, dishId, 1);
        int success = 0;
        try{
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("insert into orders_dishes (order_id, dish_id, amount) VALUES (?, ?, 1)");
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, dishId);
            success = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getClass().equals(SQLIntegrityConstraintViolationException.class)){
                throw new SQLIntegrityConstraintViolationException();
            }
            e.printStackTrace();
        }
        return (success == 1) ? orderDish : null;
    }

    public void increaseOrderDishAmount(Integer orderId, Integer dishId){
        PreparedStatement preparedStatement;
        Connection connection;
        try{
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("update orders_dishes set amount = amount+1 where order_id = ? AND dish_id = ?");
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, dishId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void decreaseOrderDishAmount(Integer orderId, Integer dishId){
        PreparedStatement preparedStatement;
        Connection connection;
        try{
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("update orders_dishes set amount = amount-1 where order_id = ? AND dish_id = ?");
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, dishId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeOrderDish(Integer orderId, Integer dishId){
        PreparedStatement preparedStatement ;
        Connection connection;
        try{
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("delete from orders_dishes where dish_id = ? and order_id = ?");
            preparedStatement.setInt(1, dishId);
            preparedStatement.setInt(2, orderId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
