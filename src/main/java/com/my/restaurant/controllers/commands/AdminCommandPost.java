package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.OrdersDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet for posting from admin page
 * set order status to next
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class AdminCommandPost implements Command{

    private final OrdersDao ordersDao = new OrdersDao();
    private static final Logger logger = LogManager.getLogger(AdminCommandPost.class);

    @Override
    public String execute(HttpServletRequest request) {
        Integer userID = Integer.parseInt(request.getParameter("userId"));
        ordersDao.nextStatus(userID);
        logger.info("admin change order status");
        return "redirect:/admin";
    }
}
