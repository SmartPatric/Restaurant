package com.my.restaurant.dao;

import com.my.restaurant.models.Orders;
import com.my.restaurant.models.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrdersDao {

    public List<Orders> findAllOrders(String status) {
        PreparedStatement statement = null;
        Connection connection = null;
        List<Orders> orders = new ArrayList<>();
        try {
            connection = DbUtil.getConnection();
            statement =  connection.prepareStatement("SELECT * FROM orders WHERE status = ?;");
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (resultSet.next()) {
                orders.add(new Orders(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
                        LocalDateTime.parse(resultSet.getString(4), formatter),
                        LocalDateTime.parse(resultSet.getString(5), formatter),
                        resultSet.getDouble(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Orders findOrderByUserId(Integer id){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        Orders order = null;
        try{
            connection = DbUtil.getConnection();
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
            e.printStackTrace();
        }
        return order;
    }

    public Orders createNewOrder(Integer userId){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet idSet = null;
        Orders order = new Orders();
        Integer success = 0;
        order.setUserId(userId);
        try{
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("insert into orders(user_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);
            success = preparedStatement.executeUpdate();
            idSet = preparedStatement.getGeneratedKeys();
            if(idSet.next()){
                order.setId(idSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (success == 1) ? order : null;
    }

    public void payOrder(Integer orderId){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet idSet = null;
        try{
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("update orders set status='APPROVING' where id = ?");
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void nextStatus(Integer userId){
        Orders order = findOrderByUserId(userId);
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet idSet = null;
        try{
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("update orders set status = ? where id = ?");
            Status status =  Status.findStatusById((Status.valueOf(order.getStatus()).getId())+1);
            System.out.println("change status to next" + status);
            preparedStatement.setString(1, status.toString());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
