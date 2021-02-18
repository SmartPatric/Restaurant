package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.models.Orders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AdminCommand implements Command{

    private final OrdersDao ordersDao = new OrdersDao();

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();

        List<Orders> orders = ordersDao.findAllOrders("APPROVING");
        session.setAttribute("orders_app", orders);
        orders = ordersDao.findAllOrders("COOKING");
        session.setAttribute("orders_cook", orders);
        orders = ordersDao.findAllOrders("DELIVERING");
        session.setAttribute("orders_dell", orders);
        orders = ordersDao.findAllOrders("CLOSED");
        session.setAttribute("orders_closed", orders);
        orders = ordersDao.findAllOrders("CANCELED");
        session.setAttribute("orders_can", orders);

        return "/admin/adminPage.jsp";
    }
}
