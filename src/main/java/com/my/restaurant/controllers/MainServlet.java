package com.my.restaurant.controllers;

import com.my.restaurant.dao.DishesDao;
import com.my.restaurant.models.Cart;
import com.my.restaurant.models.Dishes;
import com.my.restaurant.models.Orders;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "MainServlet", value = "/main")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DishesDao dishesDao = new DishesDao();
        List<Dishes> dishes = dishesDao.findAllDishes();
        /*for (Dishes d : dishes) {
            System.out.println(d.getId() + " " + d.getPrice() + " " + d.getName() + " " + d.getImage() + " " + d.getCategory().toString());
        }*/
        HttpSession session = request.getSession();
        session.setAttribute("dishes", dishes);

        getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
