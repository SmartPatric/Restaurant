package com.my.restaurant.controllers.commands;

import javax.servlet.http.HttpServletRequest;
/**
 * Command that redirect to error page
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class ExceptionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/error.jsp";
    }
}
