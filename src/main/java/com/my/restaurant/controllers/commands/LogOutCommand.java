package com.my.restaurant.controllers.commands;

import com.my.restaurant.models.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)
        HttpSession session = request.getSession();
        session.invalidate();
        new MainPageCommand();
        return "";
    }
}
