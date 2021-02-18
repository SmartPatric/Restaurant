package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Users;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {

    private final UsersDao usersDao = new UsersDao();

    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Registration " + name + " " + password);

        if (name == null || name.equals("") || password == null || password.equals("")) {
            return "/registration.jsp";
        }

        Users user = new Users();
        user.setEmail(name);
        user.setPassword(password);

        if (usersDao.validate(name, password) != null) {
            return "/login.jsp";
        } else {
            if (usersDao.insertUser(user) > 0) {
                return "/login.jsp";
            } else {
                return "/main.jsp";
            }
        }

    }
}

