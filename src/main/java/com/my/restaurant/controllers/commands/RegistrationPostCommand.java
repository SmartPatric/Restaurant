package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Users;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPostCommand implements Command{

    private final UsersDao usersDao = new UsersDao();

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("login");
        String password = request.getParameter("password");

        if (name == null || name.equals("") || password == null || password.equals("")) {
            request.setAttribute("registrationStatus", "Enter login and password");
            return "redirect:/registration?status=noData";
        }

        Users user = new Users();
        user.setEmail(name);
        user.setPassword(password);

        if (usersDao.validate(name, password) != null) {
            request.setAttribute("registrationStatus", "User exists");
            return "redirect:/registration?status=exist";
        } else {
            if (usersDao.insertUser(user) > 0) {
                return "/login.jsp";
            }
            return "redirect:/main";
        }
    }
}