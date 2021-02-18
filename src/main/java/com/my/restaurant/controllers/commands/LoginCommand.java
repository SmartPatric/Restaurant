package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Role;
import com.my.restaurant.models.Users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private final UsersDao usersDao = new UsersDao();

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("login");
        String password = request.getParameter("password");

        System.out.println("Login " + name + " " + password);
        if (name == null || name.equals("") || password == null || password.equals("")) {
            return "/login.jsp";
        }

        if (CommandUtility.checkUserIsLogged(request, name)) {
            return "/error.jsp";
        }

        Users user = usersDao.validate(name, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userName", user.getEmail());
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());
            if (user.getRole().equals("ADMIN")) {
                CommandUtility.setUserRole(request, Role.ADMIN, name);
                System.out.println("Redirect to admin");
            } else if (user.getRole().equals("USER")) {
                CommandUtility.setUserRole(request, Role.USER, name);
                System.out.println("Redirect to user");
            }
            return "/main.jsp";
        }
        CommandUtility.setUserRole(request, Role.UNKNOWN, name);
        System.out.println("Redirect to login");
        return "/login.jsp";
    }
}
