package com.my.restaurant.controllers;

import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Users;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Registration "+email + " " + password);

        if (email.length() < 6 || password.length() < 6) {
            response.sendRedirect(request.getContextPath()+"/registration");
        }
        else {
            Users user = new Users();
            user.setEmail(email);
            user.setPassword(password);

            UsersDao usersDao = new UsersDao();

            if (usersDao.validate(email, password)!=null) {
                getServletContext().getRequestDispatcher("/login").forward(request, response);
            } else {
                if (usersDao.insertUser(user) > 0) {
                    getServletContext().getRequestDispatcher("/login").forward(request, response);
                } else {
                    getServletContext().getRequestDispatcher("/index").forward(request, response);
                }
            }
        }
    }
}
