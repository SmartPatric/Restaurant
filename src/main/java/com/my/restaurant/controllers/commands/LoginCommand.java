package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.UsersDao;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

    private final UsersDao usersDao = new UsersDao();

    @Override
    public String execute(HttpServletRequest request) {
        String status = "";
        String statusParam = request.getParameter("status");
        if(statusParam!=null) {
            switch (statusParam) {
                case "logged":
                    status = "User already logged";
                    break;
                case "noData":
                    status = "Enter login and password";
                    break;
                case "blocked":
                    status = "User is blocked";
                    break;
                case "no":
                    status = "Login or password is incorrect";
                    break;
            }
        }
        request.setAttribute("loginStatus", status);
        return "/login.jsp";
    }
}
