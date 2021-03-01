package com.my.restaurant.controllers.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Log out command
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        logger.info("invalidate session");
        return "redirect:/main";
    }
}
