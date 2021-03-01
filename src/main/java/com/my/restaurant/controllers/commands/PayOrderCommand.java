package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.models.Orders;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PayOrderCommand implements Command {

    private final OrdersDao ordersDao = new OrdersDao();
    private static final Logger logger = LogManager.getLogger(PayOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = Integer.parseInt(session.getAttribute("userId").toString());
        Orders order = ordersDao.findOrderByUserId(userId);
        ordersDao.payOrder(order.getId());
        logger.info("payed order " + order.getId());
        return "redirect:/userCabinet";
    }
}
