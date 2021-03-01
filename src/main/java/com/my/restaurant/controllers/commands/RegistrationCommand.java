package com.my.restaurant.controllers.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 * Command getting registration page
 * statusParam can be get after executing RegistrationPostCommand
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String status = "";
        String statusParam = request.getParameter("status");
        logger.info("registration");

        if(statusParam!=null) {
            switch (statusParam) {
                case "exist":
                    status = "User already exists";
                    logger.info("user exist");
                    break;
                case "noData":
                    status = "Enter login and password";
                    logger.info("empty request");
                    break;
            }
        }
        request.setAttribute("registrationStatus", status);
        return "/registration.jsp";
    }
}

