package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.DishesDao;
import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.models.Dishes;
import com.my.restaurant.models.Orders;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserCommand implements Command {

    private final DishesDao dishesDao = new DishesDao();
    private final OrdersDao ordersDao = new OrdersDao();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("userId");

        ServletContext context = request.getServletContext();
        request.setAttribute("name", context.getAttribute("userName"));

        if (user != null) {
            Integer userId = Integer.parseInt(user.toString());
            System.out.println("user cabinet user id " + userId);
            Orders order = ordersDao.findOrderByUserId(userId);

            if (order != null) {
                System.out.println("Order id " + order.getId());
                request.setAttribute("orderStatus", order.getStatus());
                request.setAttribute("orderId", order.getId());
                request.setAttribute("totalPrice", order.getTotal());
                List<Dishes> dishes = dishesDao.findDishesByOrderId(order.getId());
                request.setAttribute("dishes", dishes);
            }
            return "/user/userCabinet.jsp";
        }
        return "/login.jsp";
    }
}
