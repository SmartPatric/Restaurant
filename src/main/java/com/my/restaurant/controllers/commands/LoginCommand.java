package com.my.restaurant.controllers.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 * Command getting login page
 * statusParam can be get after executing LoginPostCommand
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("login page");
        String status = "";
        String statusParam = request.getParameter("status");
        if(statusParam!=null) {
            switch (statusParam) {
                case "logged":
                    status = "User already logged";
                    logger.info("already logged");
                    break;
                case "noData":
                    status = "Enter login and password";
                    logger.info("empty request");
                    break;
                case "blocked":
                    status = "User is blocked";
                    logger.info("blocked");
                    break;
                case "no":
                    status = "Login or password is incorrect";
                    logger.info("incorrect data");
                    break;
            }
        }
        request.setAttribute("loginStatus", status);
        return "/login.jsp";
    }
}
