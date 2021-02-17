package com.my.restaurant.controllers;

import com.my.restaurant.dao.DishesDao;
import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.dao.OrdersDishesDao;
import com.my.restaurant.models.Dishes;
import com.my.restaurant.models.Orders;
import com.my.restaurant.models.OrdersDishes;
import com.my.restaurant.models.Status;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/userCabinet")
public class UserServlet extends HttpServlet {

    private static OrdersDishesDao ordersDishesDao = new OrdersDishesDao();
    private static OrdersDao ordersDao = new OrdersDao();
    private static DishesDao dishesDao = new DishesDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = Integer.parseInt(session.getAttribute("userId").toString());
        Orders order = ordersDao.findOrderByUserId(userId);

        if (order!=null) {
            session.setAttribute("orderStatus", order.getStatus());
            session.setAttribute("totalPrice", order.getTotal());
            List<Dishes> dishes = dishesDao.findDishesByOrderId(order.getId());
            session.setAttribute("dishes", dishes);
        }
        getServletContext().getRequestDispatcher("/userCabinet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = Integer.parseInt(session.getAttribute("userId").toString());
        Orders order = ordersDao.findOrderByUserId(userId);

        if(request.getParameter("pay").equals("false")) {
            Object dId = request.getParameter("DishId");
            Integer dishId = Integer.parseInt(dId.toString());


            if (order == null) {
                System.out.println("creating new order");
                order = ordersDao.createNewOrder(userId);
            } else System.out.println("order exist");

            try {
                ordersDishesDao.createNewOrderDish(order.getId(), dishId, 1);
            } catch (SQLIntegrityConstraintViolationException e) {
                //System.out.println("im here "+order.getId()+" "+dishId);
                ordersDishesDao.increaseOrderDishAmount(order.getId(), dishId);
            }
        }
        else {
            ordersDao.payOrder(order.getId());
        }

    }
}
