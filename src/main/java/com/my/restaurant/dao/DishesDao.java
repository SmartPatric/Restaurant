package com.my.restaurant.dao;

import com.my.restaurant.models.Category;
import com.my.restaurant.models.Dishes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishesDao {

    public List<Dishes> findAllDishes() {
        Statement statement = null;
        Connection connection = null;
        List<Dishes> dishes = new ArrayList<>();
        try {
            connection = DbUtil.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dishes;");
            while (resultSet.next()) {
                dishes.add(new Dishes(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4), resultSet.getString(5),
                        Category.valueOf(resultSet.getString(6))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    //select * from dishes d join orders_dishes od on d.id = od.dish_id join orders o on o.id = od.order_id where
    //o.id = 41;

    public List<Dishes> findDishesByOrderId(Integer orderId){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        List<Dishes> dishes = new ArrayList<>();
        try{
            connection = DbUtil.getConnection();
            preparedStatement = connection.prepareStatement("select d.id, d.name, d.price, d.image, d.description, d.category, od.amount " +
                    "from dishes d join orders_dishes od on d.id = od.dish_id " +
                    "join orders o on o.id = od.order_id where " +
                    "o.id = ?");
            preparedStatement.setInt(1, orderId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dishes.add(new Dishes(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getString(4), resultSet.getString(5),
                        Category.valueOf(resultSet.getString(6)), resultSet.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }
}
