package com.my.restaurant.controllers;

import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Orders;
import com.my.restaurant.models.Users;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {

    private static OrdersDao ordersDao = new OrdersDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        getServletContext().getRequestDispatcher("/adminPage.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userID = Integer.parseInt(request.getParameter("userId"));
        System.out.println(userID);
        ordersDao.nextStatus(userID);
    }
}
