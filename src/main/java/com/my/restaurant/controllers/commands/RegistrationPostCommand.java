package com.my.restaurant.controllers.commands;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
            return "redirect:/registration?status=noData";
        }

        if (usersDao.validate(name, password) != null) {
            return "redirect:/registration?status=exist";
        } else {
            Users user = new Users();
            user.setEmail(name);
            user.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
            if (usersDao.insertUser(user) > 0) {
                return "redirect:/login";
            }
            return "redirect:/main";
        }
    }
}
