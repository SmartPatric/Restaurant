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
        if(session.getAttribute("role")!=null) {

            List<Orders> orders = ordersDao.findAllOrders("APPROVING");
            request.setAttribute("orders_app", orders);
            orders = ordersDao.findAllOrders("COOKING");
            request.setAttribute("orders_cook", orders);
            orders = ordersDao.findAllOrders("DELIVERING");
            request.setAttribute("orders_dell", orders);
            orders = ordersDao.findAllOrders("CLOSED");
            request.setAttribute("orders_closed", orders);
            orders = ordersDao.findAllOrders("CANCELED");
            request.setAttribute("orders_can", orders);

            return "/admin/adminPage.jsp";
        }
        return "/login.jsp";
    }
}
