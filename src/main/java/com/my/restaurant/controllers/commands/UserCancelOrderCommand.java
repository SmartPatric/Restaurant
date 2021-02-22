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

        if(ordersDao.findOrderByUserId(userId).getStatus().equals("APPROVING")){
            ordersDao.cancelOrder(orderId);
        }
        return "redirect:/userCabinet";
    }

}
