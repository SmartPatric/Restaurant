package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.DishesDao;
import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.dao.OrdersDishesDao;
import com.my.restaurant.models.Dishes;
import com.my.restaurant.models.Orders;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;
/**
 * Command user page POST
 * @brief if user is logged add dish to order
 *        create new order for user if not
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class UserCommandPost implements Command{
    private static final Logger logger = LogManager.getLogger(UserCommandPost.class);
    private final OrdersDao ordersDao = new OrdersDao();
    private final DishesDao dishesDao = new DishesDao();
    private final OrdersDishesDao ordersDishesDao = new OrdersDishesDao();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Trying to add dish to order");
        HttpSession session = request.getSession();
        if(session.getAttribute("userId")==null || !session.getAttribute("userRole").equals("USER")){
            return "/login.jsp";
        }
        Integer userId = Integer.parseInt(session.getAttribute("userId").toString());
        Orders order = ordersDao.findOrderByUserId(userId);

        if (order == null) {
            order = ordersDao.createNewOrder(userId);
        }

        if(order.getStatus().equals("MAKING")) {
            Object dId = request.getParameter("DishId");
            Integer dishId = Integer.parseInt(dId.toString());

            Dishes dish = dishesDao.findDishByDishId(dishId);

            try {
                ordersDishesDao.createNewOrderDish(order.getId(), dishId);
                logger.info("Dish added");
            } catch (SQLIntegrityConstraintViolationException e) {
                ordersDishesDao.increaseOrderDishAmount(order.getId(), dishId);
                logger.info("Amount increased");
            }
            ordersDao.changePrice(true, dish.getPrice(), order.getId());
        }

        return "redirect:/userCabinet";
    }
}
