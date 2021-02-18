package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.dao.OrdersDishesDao;
import com.my.restaurant.models.Orders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;

public class UserCommandPost implements Command{

    private final OrdersDao ordersDao = new OrdersDao();
    private final OrdersDishesDao ordersDishesDao = new OrdersDishesDao();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId")==null || !session.getAttribute("userRole").equals("USER")){
            return "/login";
        }
        Integer userId = Integer.parseInt(session.getAttribute("userId").toString());
        Orders order = ordersDao.findOrderByUserId(userId);

        if (order == null) {
            System.out.println("creating new order");
            order = ordersDao.createNewOrder(userId);
        } else System.out.println("order exist");

        if(request.getParameter("pay").equals("false") && order.getStatus().equals("MAKING")) {
            Object dId = request.getParameter("DishId");
            Integer dishId = Integer.parseInt(dId.toString());
            System.out.println("add dish");
            try {
                ordersDishesDao.createNewOrderDish(order.getId(), dishId, 1);
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("im here "+order.getId()+" "+dishId);
                ordersDishesDao.increaseOrderDishAmount(order.getId(), dishId);
            }
        }
        else if(request.getParameter("pay").equals("true")){
            ordersDao.payOrder(order.getId());
        }
        new UserCommand();
        return "";
    }
}
