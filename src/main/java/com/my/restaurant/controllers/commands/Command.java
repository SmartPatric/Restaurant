package com.my.restaurant.controllers.commands;

import javax.servlet.http.HttpServletRequest;
/**
 * Command interface
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public interface Command {
    String execute(HttpServletRequest request);
}
