package com.my.restaurant.controllers.commands;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String status = "";
        String statusParam = request.getParameter("status");
        if(statusParam!=null) {
            switch (statusParam) {
                case "exist":
                    status = "User already exists";
                    break;
                case "noData":
                    status = "Enter login and password";
                    break;
            }
        }
        request.setAttribute("registrationStatus", status);
        return "/registration.jsp";
    }
}

