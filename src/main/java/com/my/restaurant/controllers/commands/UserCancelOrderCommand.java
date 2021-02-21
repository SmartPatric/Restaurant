package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.OrdersDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserCancelOrderCommand implements Command{

    private final OrdersDao ordersDao = new OrdersDao();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        Integer userId = Integer.parseInt(session.getAttribute("userId").toString());
        System.out.println("User id "+userId);
        if(ordersDao.findOrderByUserId(userId).getStatus()=="APPROVING"){
            ordersDao.cancelOrder(orderId);
        }
        //System.out.println("cancel order "+orderId);
        return "changeUser";
    }

}
