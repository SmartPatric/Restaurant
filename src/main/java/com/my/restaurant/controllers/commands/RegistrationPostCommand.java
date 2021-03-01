package com.my.restaurant.controllers.commands;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Users;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command registration page POST
 * @brief if user doesn't exist, hash password and save user to DB
 * status is used by RegistrationCommand to show exceptions messages
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class RegistrationPostCommand implements Command{
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private final UsersDao usersDao = new UsersDao();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("checking registration request");

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
                logger.info("new user registered");
                return "redirect:/login";
            }
            logger.info("user not registered");
            return "redirect:/main";
        }
    }
}
