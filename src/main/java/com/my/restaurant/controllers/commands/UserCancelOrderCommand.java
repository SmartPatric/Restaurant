package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.OrdersDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet for canceling order from user cabinet
 *
 * @author - Mariia Shaiko
 * @version - 1.0
 * @brief cancel order if order status is approving
 */

public class UserCancelOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UserCancelOrderCommand.class);
    private final OrdersDao ordersDao = new OrdersDao();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        Integer userId = Integer.parseInt(session.getAttribute("userId").toString());

        if (ordersDao.findOrderByUserId(userId).getStatus().equals("APPROVING")) {
            ordersDao.cancelOrder(orderId);
        }
        logger.info("order canceled, id: " + orderId);
        return "redirect:/userCabinet";
    }

}
