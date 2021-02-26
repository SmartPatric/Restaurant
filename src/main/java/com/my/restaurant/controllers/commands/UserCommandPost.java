package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.DishesDao;
import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.dao.OrdersDishesDao;
import com.my.restaurant.models.Dishes;
import com.my.restaurant.models.Orders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;

public class UserCommandPost implements Command{

    private final OrdersDao ordersDao = new OrdersDao();
    private final DishesDao dishesDao = new DishesDao();
    private final OrdersDishesDao ordersDishesDao = new OrdersDishesDao();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId")==null || !session.getAttribute("userRole").equals("USER")){
            return "/login.jsp";
        }
        Integer userId = Integer.parseInt(session.getAttribute("userId").toString());
        Orders order = ordersDao.findOrderByUserId(userId);

        if (order == null) {
            order = ordersDao.createNewOrder(userId);
        } else System.out.println("order exist");

        if(request.getParameter("pay").equals("false") && order.getStatus().equals("MAKING")) {
            Object dId = request.getParameter("DishId");
            Integer dishId = Integer.parseInt(dId.toString());

            Dishes dish = dishesDao.findDishByDishId(dishId);

            System.out.println("add dish");
            try {
                ordersDishesDao.createNewOrderDish(order.getId(), dishId);
            } catch (SQLIntegrityConstraintViolationException e) {
                ordersDishesDao.increaseOrderDishAmount(order.getId(), dishId);
            }
            System.out.println("before change price : "+dish.getPrice()+" orderId " + order.getId());
            ordersDao.changePrice(true, dish.getPrice(), order.getId());
        }
        else if(request.getParameter("pay").equals("true")){
            ordersDao.payOrder(order.getId());
        }
        return "redirect:/userCabinet";
    }
}
