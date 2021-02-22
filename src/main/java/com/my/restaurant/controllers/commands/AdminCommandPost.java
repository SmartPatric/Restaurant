package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.OrdersDao;

import javax.servlet.http.HttpServletRequest;

public class AdminCommandPost implements Command{

    private final OrdersDao ordersDao = new OrdersDao();

    @Override
    public String execute(HttpServletRequest request) {
        Integer userID = Integer.parseInt(request.getParameter("userId"));
        //System.out.println("admin post "+ userID);
        ordersDao.nextStatus(userID);
        return "redirect:/admin";
    }
}
