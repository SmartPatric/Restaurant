package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.dao.OrdersDishesDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet for changing dish amount and total price in user page
 * type of change depends on amountChange request param (plus, minus, remove)
 *
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class ChangeDishAmountCommand implements Command {

    private final OrdersDishesDao ordersDishesDao = new OrdersDishesDao();
    private final OrdersDao ordersDao = new OrdersDao();
    private static final Logger logger = LogManager.getLogger(ChangeDishAmountCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String amountChange = request.getParameter("amountChange");
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        Integer dishId = Integer.parseInt(request.getParameter("dishId"));
        Double dishPrice = Double.parseDouble(request.getParameter("dishPrice"));

        if (amountChange.equals("plus")) {
            ordersDishesDao.increaseOrderDishAmount(orderId, dishId);
            ordersDao.changePrice(true, dishPrice, orderId);
            logger.info("increase dish amount, dish_id: " + dishId + " order_id: " + orderId);

        } else if (amountChange.equals("minus") && Integer.parseInt(request.getParameter("dishAmount")) > 1) {
            ordersDishesDao.decreaseOrderDishAmount(orderId, dishId);
            ordersDao.changePrice(false, dishPrice, orderId);
            logger.info("decrease dish amount, dish_id: " + dishId + " order_id: " + orderId);

        } else if (amountChange.equals("remove")) {
            ordersDishesDao.removeOrderDish(orderId, dishId);
            ordersDao.changePrice(false, dishPrice, orderId);
            logger.info("remove dish from order, dish_id: " + dishId + " order_id: " + orderId);
        }
        return "redirect:/userCabinet";
    }
}
