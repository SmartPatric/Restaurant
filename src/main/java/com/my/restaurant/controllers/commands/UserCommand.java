package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.DishesDao;
import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.models.Dishes;
import com.my.restaurant.models.Orders;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * Command getting user page
 * @brief if user is logged and have order, put dish list from order to view
 *              return user cabinet view
 *        if user is not logged redirect to login page
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class UserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UserCommand.class);
    private final DishesDao dishesDao = new DishesDao();
    private final OrdersDao ordersDao = new OrdersDao();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("Opening user page");

        HttpSession session = request.getSession();
        Object user = session.getAttribute("userId");

        ServletContext context = request.getServletContext();
        request.setAttribute("name", context.getAttribute("userName"));

        if (user != null) {
            Integer userId = Integer.parseInt(user.toString());
            Orders order = ordersDao.findOrderByUserId(userId);

            if (order != null) {
                request.setAttribute("orderStatus", order.getStatus());
                request.setAttribute("orderId", order.getId());
                request.setAttribute("totalPrice", order.getTotal());
                List<Dishes> dishes = dishesDao.findDishesByOrderId(order.getId());
                request.setAttribute("dishes", dishes);
            }
            return "/user/userCabinet.jsp";
        }
        return "/login.jsp";
    }
}
